package cc.mikaka.ddd.common.lock;

/**
 * 分布式锁通用静态变量
 */
public final class LockConstant {
    /**
     * 默认锁时间 10分钟
     */
    public final static int DEFAULT_LOCK_TIME = 5 * 60 * 1000;

    /**
     * 默认获取某个锁时常  3分钟
     */
    public final static int DEFAULT_GET_LOCK_TIME = 3 * 60 * 1000;
}
