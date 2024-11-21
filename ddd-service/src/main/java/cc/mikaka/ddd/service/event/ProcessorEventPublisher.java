package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.error.BizErrorCode;
import cc.mikaka.ddd.common.exception.BizServiceException;
import cc.mikaka.ddd.common.lock.DistributeLockEnum;
import cc.mikaka.ddd.common.lock.DistributeLockService;
import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import cc.mikaka.ddd.common.sequence.SequenceService;
import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

/**
 * 事件发布器
 */
@Log4j2
@Component
public class ProcessorEventPublisher {
    /**
     * 异步事件执行的现成池
     * <p>
     * TODO 提到common里面去
     */
    private final static ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(5);
    @Autowired
    BizEventHandleComponent bizEventHandleComponent;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    DistributeLockService distributeLockService;

    public void publishEvent(EventMessageModel eventData) {
        List<EventMessageHandle> handles = bizEventHandleComponent.getHandle(eventData.getBizType(), eventData.getActionType());
        if (CollectionUtils.isEmpty(handles)) {
            return;
        }

        //TODO 异步逻辑处理
        List<EventMessageHandle> asyncHandle = Lists.newArrayList();
        for (EventMessageHandle handle : handles) {
            log.info("-->调用业务事件，当前handle={}", handle.getClass().getSimpleName());
            if (handle instanceof AbstractSyncEventHandle) {
                //同步事件
                dispatcherSyncEvent(eventData, (AbstractSyncEventHandle) handle);
            } else {
                asyncHandle.add(handle);
            }
        }
        for (EventMessageHandle handle : asyncHandle) {
            if (handle instanceof AbstractAsyncEventHandle) {
                //异步事件
                buildEventTracking(eventData);
                dispatcherAsyncEvent(eventData, (AbstractAsyncEventHandle) handle);
            }
        }
    }

    /**
     * TODO 异步事件处理
     *
     * @param eventData
     * @param handle
     */
    private void dispatcherAsyncEvent(EventMessageModel eventData, AbstractAsyncEventHandle handle) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            // 判断是否有事物
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    // 有事务，事务提交后执行
                    doAsyncEvent(eventData, handle);
                }
            });
        } else {
            // 没事务直接执行
            doAsyncEvent(eventData, handle);
        }
    }

    private void doAsyncEvent(EventMessageModel eventData, AbstractAsyncEventHandle handle) {
        try {
            EXECUTOR_SERVICE.execute(() -> {
                boolean locked = false;
                String messageId = eventData.getMessageId();
                String requestId = sequenceService.buildSequenceId(SequenceIdEnum.DEFAULT);
                try {
                    //messageId加锁
                    locked = distributeLockService.tryLock(DistributeLockEnum.EVENT_MESSAGE, messageId, requestId);
                    if (!locked) {
                        log.error("获取锁失败:{}", messageId);
                        //TODO 兜底
                        return;
                    }
                    EventResult eventResult = handle.doHandle(eventData);
                    boolean isSuccess = eventResult != null && Lists.newArrayList(MessageStateEnum.SUCCESS, MessageStateEnum.SKIP).contains(eventResult.getState());
                    Boolean jobRetry = isSuccess ? null : (eventResult != null && eventResult.isNeedRetry());
                    // TODO 不成功，重试
                } finally {
                    if (locked) {
                        distributeLockService.unlock(DistributeLockEnum.EVENT_MESSAGE, messageId, requestId);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            //TODO 兜底
        }
    }

    /**
     * @param eventData
     * @param handle
     */
    private void dispatcherSyncEvent(EventMessageModel eventData, AbstractSyncEventHandle handle) {
        try {
            EventResult eventResult = handle.doHandle(eventData);
            if (eventResult == null) {
                throw new BizServiceException(BizErrorCode.EVENT_ERROR);
            }
            if (eventResult.getState() == MessageStateEnum.FAIL) {
                throw new BizServiceException(eventResult.getErrorCode(), eventResult.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("同步处理器处理异常:{}", eventData);
            throw e;
        }
    }


    private void buildEventTracking(EventMessageModel fissionModel) {
        String msgId = sequenceService.buildSequenceId(SequenceIdEnum.USER);
        fissionModel.setMessageId(msgId);
        fissionModel.setState(MessageStateEnum.INIT.getCode());
        //TODO 落库
    }

}
