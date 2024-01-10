package cc.mikaka.ddd.service.event;

public interface EventMessageHandle {
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