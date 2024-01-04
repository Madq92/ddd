package cc.mikaka.ddd.service.base;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import cc.mikaka.ddd.bean.request.BaseRequest;
import cc.mikaka.ddd.common.constants.PaaSConstants;
import cc.mikaka.ddd.common.context.BaseContext;
import cc.mikaka.ddd.common.context.ProcessContext;
import cc.mikaka.ddd.common.enums.ActionType;
import cc.mikaka.ddd.common.enums.BizType;
import cc.mikaka.ddd.common.error.BizErrorCode;
import cc.mikaka.ddd.common.exception.BizServiceException;
import cc.mikaka.ddd.common.exception.BizValidateException;
import cc.mikaka.ddd.common.util.AssertUtil;
import cc.mikaka.ddd.processor.BaseProcessor;
import cc.mikaka.ddd.processor.BizProcessorComponent;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 通用业务逻辑处理
 */
@Log4j2
public class BaseBizService {

    /**
     * 核心业务处理器
     */
    @Resource
    private BizProcessorComponent bizProcessorComponent;

    /**
     * 执行业务调用
     */
    protected final <BizData> BizData execute(BaseRequest request, ActionType actionType,
                                              BizType bizType) {
        log.info("==>执行processor业务调用.");
        try {
            if (StringUtils.isBlank(request.getRequestId())) {
                request.setRequestId(UUID.randomUUID().toString());
            }
            // 1.通用参数校验
            baseVerify(request, actionType, bizType);

            // 2.获得业务标示号(贯穿整个请求链路的业务身份)
            String bizKey = getBizKey(actionType, bizType);
            AssertUtil.notBlank(bizKey);

            buildBaseContext(request, bizType, actionType);

            // 3、业务数据注解校验
            // annotateValidate.validate(request);

            // 4.创建流水(幂等校验)
            // try {
            //     flowDomain = createFlow(request, actionType, bizType);
            // } catch (Exception e) {
            //     LoggerUtil.error(e, logger, "插入流水状态失败。业务类型：{0}，操作类型：{1}", bizType,
            //         actionType);
            // }

            // 5.执行业务
            BaseProcessor processor = bizProcessorComponent.getProcessor(bizKey);
            AssertUtil.notNull(processor, BizErrorCode.PROCESSOR_NOT_EXIST);
            processor.setActionType(actionType);
            processor.setBizType(bizType);
            log.info("当前processor={}", processor.getClass().getSimpleName());
            return doProcess(processor, request);

        } catch (BizValidateException e) {
            log.error("业务校验异常。业务类型：{}，操作类型：{}", bizType, actionType, e);
            throw new BizValidateException(e.getErrorCode(), e.getErrorDesc());
        } catch (Throwable e) {
            log.error("系统异常。业务类型：{}，操作类型：{}", bizType, actionType, e);
            throw new BizServiceException(BizErrorCode.SYSTEM_ERROR, BizErrorCode.SYSTEM_ERROR.getDesc());
        } finally {
            // 9.线程结束，清理ThreadLocal里的属性
            ProcessContext.cleanThreadLocal();
        }
    }

    private void buildBaseContext(BaseRequest request, BizType bizType, ActionType actionType) {
        BaseContext context = new BaseContext();
        context.setBizType(bizType);
        context.setActionType(actionType);
        context.setInitTimestamp(System.currentTimeMillis());
        Map<String, Object> extMap = request.getExtMap();
        if (MapUtils.isNotEmpty(extMap)) {
            extMap.forEach((k, v) -> {
                if (Objects.nonNull(k) && Objects.nonNull(v)) {
                    context.getExtMap().put(k, v);
                }
            });
        }
        ProcessContext.setCommonRequest(context);
    }

    /**
     * 获得业务key
     */
    private String getBizKey(ActionType actionType, BizType bizType) {
        return actionType.getCode() + PaaSConstants.SEP_UNDERLINE + bizType.getCode();
    }


    private <BizData> BizData doProcess(BaseProcessor processor, BaseRequest request) {
        return (BizData) processor.doProcessor(request);
    }

    /**
     * 基础校验
     */
    private void baseVerify(BaseRequest baseRequest, ActionType actionType, BizType bizType) {

        AssertUtil.notNull(baseRequest, "request不能为空");
        AssertUtil.notNull(actionType, "操作类型不能为空");
        AssertUtil.notNull(bizType, "业务类型不能为空");

        AssertUtil.notBlank(baseRequest.getRequestId(), "requestId不能为空");
        // TODO 用户ID,session,鉴权相关
    }
}
