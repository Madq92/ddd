package cc.mikaka.ddd.common.lock;

/**
 * 分布式锁
 */
public interface DistributeLockService {

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @param requestId      锁的value
     * @return true 获取锁成功  false 获取锁失败
     */
    boolean lock(DistributeLockEnum distributeLock, String key, String requestId);

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @param requestId      锁的value
     * @param getLockTime    获取锁的超时时间，单位毫秒，如：1秒  传1000
     * @return true 获取锁成功  false 获取锁失败
     */
    boolean lock(DistributeLockEnum distributeLock, String key, String requestId, int getLockTime);

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @param requestId      锁的value
     * @param getLockTime    获取锁的超时时间，单位毫秒，如：1秒  传1000
     * @param lockTime       锁的时长
     * @return true 获取锁成功  false 获取锁失败
     */
    boolean lock(DistributeLockEnum distributeLock, String key, String requestId, int getLockTime, int lockTime);

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @param requestId      锁的value
     * @return true 获取锁成功  false 获取锁失败
     */
    boolean tryLock(DistributeLockEnum distributeLock, String key, String requestId);

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @param requestId      锁的value
     * @param lockTime       锁的时长
     * @return true 获取锁成功  false 获取锁失败
     */
    boolean tryLock(DistributeLockEnum distributeLock, String key, String requestId, int lockTime);

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @param requestId      锁的value
     * @return true 获取锁成功  false 获取锁失败
     */
    boolean unlock(DistributeLockEnum distributeLock, String key, String requestId);

    /**
     * @param distributeLock 锁类型
     * @param key            锁的key
     * @return true 获取锁成功  false 获取锁失败
     */
    Boolean findHasLock(DistributeLockEnum distributeLock, String key);

}
