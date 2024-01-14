package cc.mikaka.ddd.service.processor;

import cc.mikaka.ddd.bean.request.BaseRequest;
import cc.mikaka.ddd.common.context.ParamContext;
import cc.mikaka.ddd.common.error.BizErrorCode;
import cc.mikaka.ddd.common.lock.BaseLockInfo;
import cc.mikaka.ddd.common.lock.DistributeLockEnum;
import cc.mikaka.ddd.common.lock.DistributeLockService;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.service.event.EventMessageModel;
import cc.mikaka.ddd.service.event.ProcessorEventPublisher;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 核心业务逻辑抽象
 **/
@Log4j2
public abstract class AbstractProcessor<Model, Request extends BaseRequest, BizResult> extends BaseProcessor<Model, Request, BizResult> {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private DistributeLockService distributeLockService;
    @Autowired
    private ProcessorEventPublisher processorEventPublisher;

    /**
     * 执行业务
     *
     * @param request 请求参数
     */
    @Override
    public final BizResult doProcessor(Request request) {
        /**
         * 流程上下文
         */
        ParamContext paramContext = new ParamContext();

        /**
         * 前置校验
         */
        preTransValidate(request, paramContext);

        /**
         * 数据模型转换(创建领域模型)
         */
        Model model = trans(request, paramContext);

        /**
         * 获取需要加锁的列表
         */
        List<BaseLockInfo> lockList = getLockKey(model, paramContext);


        try {
            /**
             * 加锁
             */
            addLock(request, lockList);

            /**
             * 业务校验
             */
            afterTransValidate(model, paramContext);

            /**
             * 最小事务处理范围
             */
            BizResult bizResult = transactionTemplate.execute(state -> {
                /** 核心业务处理 */
                BizResult result = doBusiness(model, paramContext);

                List<EventMessageModel> eventMessageModelList = buryEventPoints(model, paramContext);

                if (CollectionUtils.isNotEmpty(eventMessageModelList)) {
                    for (EventMessageModel eventMessageModel : eventMessageModelList) {
                        if (null == eventMessageModel.getBizType()) {
                            eventMessageModel.setBizType(this.getBizType());
                        }
                        if (null == eventMessageModel.getActionType()) {
                            eventMessageModel.setActionType(this.getActionType());
                        }

                        processorEventPublisher.publishEvent(eventMessageModel);
                    }
                }
                return result;
            });
            return bizResult;
        } finally {
            /**
             * 释放锁
             */
            releaseLock(request, lockList);

        }
    }

    /**
     * 核心业务处理
     *
     * @param model        数据模型
     * @param paramContext
     * @return DishCommonResult
     */
    protected abstract BizResult doBusiness(Model model, ParamContext paramContext);

    /**
     * 本地事件埋点
     *
     * @param model
     * @param paramContext
     * @return
     */
    protected List<EventMessageModel> buryEventPoints(Model model, ParamContext paramContext) {
        return Collections.emptyList();
    }

    /**
     * 数据模型转换(创建领域模型)
     *
     * @param request      入参
     * @param paramContext
     * @return Model 领域数据模型
     */
    protected abstract Model trans(Request request, ParamContext paramContext);

    /**
     * 前置处理业务校验
     *
     * @param request      入参
     * @param paramContext
     */
    protected abstract void preTransValidate(Request request, ParamContext paramContext);

    /**
     * 业务校验
     *
     * @param model        领域数据模型
     * @param paramContext
     */
    protected abstract void afterTransValidate(Model model, ParamContext paramContext);

    /**
     * 获取lock Key
     *
     * @param model
     * @param paramContext
     * @return
     */
    protected abstract List<BaseLockInfo> getLockKey(Model model, ParamContext paramContext);


    /**
     * add lock
     *
     * @param request  请求
     * @param lockList 需要加锁的list
     * @return 需要释放的锁
     */
    private void addLock(Request request, List<BaseLockInfo> lockList) {
        if (CollectionUtils.isEmpty(lockList)) {
            return;
        }
        lockList.stream().filter(Objects::nonNull).forEach(lock -> {
            String key = lock.toLockKey();
            log.info("请求加锁key: {}", key);
            boolean success = distributeLockService.lock(DistributeLockEnum.BIZ, key,
                    request.getRequestId(), lock.getFetchLockMillTime(), lock.getLockedMillTime());
            if (!success) {
                log.error("请求加锁lock失败:{}", lock);
            }
            lock.setLocked(success);
            AssertUtil.isTrue(success, BizErrorCode.BIZ_DATA_LOCKED, lock.getErrorMessage());
        });
    }

    /**
     * release lock
     *
     * @param request  请求
     * @param lockList 需要加锁的list
     */
    private void releaseLock(Request request, List<BaseLockInfo> lockList) {
        if (CollectionUtils.isEmpty(lockList)) {
            return;
        }
        // 被锁定的才需要解锁
        lockList.stream().filter(Objects::nonNull).filter(BaseLockInfo::isLocked).forEach(lock -> {
            String key = lock.toLockKey();
            // distributeLockService.unlock(DistributeLockEnum.PAAS, key, request.getRequestId());
        });
    }
}
