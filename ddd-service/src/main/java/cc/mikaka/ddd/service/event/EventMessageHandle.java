package cc.mikaka.ddd.service.event;

public interface EventMessageHandle {
    /**
     * 消息事件类型
     *
     * @return
     */
    EventMessageType getEventMessageType();

    /**
     * 处理
     *
     * @param eventMessageModel
     */
    EventResult doHandle(EventMessageModel eventMessageModel);

    /**
     * 重试次数
     *
     * @return
     */
    int retryTimes();
}