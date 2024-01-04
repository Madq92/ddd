package cc.mikaka.ddd.common.lock;

import cc.mikaka.ddd.common.lock.DistributeLockEnum;

/**
 * 分布式锁实现服务
 * <p>
 * 为了节约资源，每次获取锁后，最多锁定5分钟会自动释放锁
 *
 */
public interface DistributeLockService {

    /**
     * 最多尝试 3分钟
     *
     * @param distributeLock
     * @param key            业务锁的key
     * @param requestId
     * @return true 获取锁成功  false 获取锁失败
     */
    public boolean lock(DistributeLockEnum distributeLock, String key, String requestId);

    /**
     * 最多尝试 3分钟
     *
     * @param distributeLock
     * @param key            业务锁的key
     * @param requestId
     * @return true 获取锁成功  false 获取锁失败
     */
    public boolean lock(DistributeLockEnum distributeLock, String key, String requestId, int getLockTime);

    /**
     * @param distributeLock
     * @param key            业务锁的key
     * @param getLockTime    超时时间，单位毫秒，如：1秒  传1000
     * @param lockTime       锁定多长时间
     * @param requestId
     * @return true 获取锁成功  false 获取锁失败
     * @see DistributeLockEnum
     */
    public boolean lock(DistributeLockEnum distributeLock, String key, String requestId, int getLockTime, int lockTime);

    /**
     * @param distributeLock
     * @param key            业务锁的key
     * @param requestId
     * @return true 释放锁成功  false 释放锁失败
     * @see DistributeLockEnum
     */
    public boolean tryLock(DistributeLockEnum distributeLock, String key, String requestId);

    /**
     * @param distributeLock
     * @param key            业务锁的key
     * @param requestId
     * @return true 释放锁成功  false 释放锁失败
     * @see DistributeLockEnum
     */
    public boolean tryLock(DistributeLockEnum distributeLock, String key, String requestId, int lockTime);

    /**
     * @param distributeLock
     * @param key            业务锁的key
     * @param requestId      requestId必须和锁定的时候传入相同则可以删除
     * @return true 释放锁成功  false 释放锁失败
     * @see DistributeLockEnum
     */
    public boolean unlock(DistributeLockEnum distributeLock, String key, String requestId);

    /**
     * 查看是否存在锁
     * @param distributeLock
     * @param key 业务锁的 key
     * @return
     */
    public Boolean findHasLock(DistributeLockEnum distributeLock, String key);

}
