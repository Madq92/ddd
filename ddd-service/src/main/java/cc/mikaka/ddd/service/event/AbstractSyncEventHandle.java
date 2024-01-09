package cc.mikaka.ddd.service.event;

public abstract class AbstractSyncEventHandle implements EventMessageHandle {
    @Override
    public int retryTimes() {
        return 0;
    }
}
