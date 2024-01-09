package cc.mikaka.ddd.service.event;

public abstract class AbstractAsyncMqEventHandle implements EventMessageHandle {

    @Override
    public int retryTimes() {
        return 3;
    }
}