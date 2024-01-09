package cc.mikaka.ddd.service.event;

import cc.mikaka.ddd.common.sequence.SequenceIdEnum;
import cc.mikaka.ddd.common.sequence.SequenceService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 事件发布器
 */
@Component
public class ProcessorEventPublisher {
    @Autowired
    BizEventHandleComponent bizEventHandleComponent;
    @Autowired
    SequenceService sequenceService;


    public void publishEvent(EventMessageModel eventData) {
        List<EventMessageHandle> handles = bizEventHandleComponent.getHandle(eventData.getBizType(), eventData.getActionType());
        if (CollectionUtils.isEmpty(handles)) {
            return;
        }

        //TODO 异步逻辑处理
        List<EventMessageHandle> asyncHandle = Lists.newArrayList();
        for (EventMessageHandle handle : handles) {
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
            } else if (handle instanceof AbstractAsyncMqEventHandle) {
                // MQ事件
                buildEventTracking(eventData);
                dispatcherMqEvent(eventData);
            }
        }
    }

    /**
     * TODO　MQ 事件处理
     *
     * @param eventData
     */
    private void dispatcherMqEvent(EventMessageModel eventData) {

    }

    /**
     * TODO 异步事件处理
     *
     * @param eventData
     * @param handle
     */
    private void dispatcherAsyncEvent(EventMessageModel eventData, AbstractAsyncEventHandle handle) {
        handle.doHandle(eventData);
    }

    /**
     * TODO　同步事件处理
     *
     * @param eventData
     * @param handle
     */
    private void dispatcherSyncEvent(EventMessageModel eventData, AbstractSyncEventHandle handle) {
        handle.doHandle(eventData);
    }


    private void buildEventTracking(EventMessageModel fissionModel) {
        String msgId = sequenceService.buildSequenceId(SequenceIdEnum.USER);
        fissionModel.setMessageId(msgId);
        fissionModel.setState(MessageStateEnum.INIT.getCode());
        //TODO 落库
    }

}
