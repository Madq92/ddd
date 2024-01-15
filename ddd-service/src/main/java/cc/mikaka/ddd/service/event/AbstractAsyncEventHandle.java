package cc.mikaka.ddd.service.event;

public abstract class AbstractAsyncEventHandle implements EventMessageHandle {
    @Override
    public int retryTimes() {
        return 3;
    }
}