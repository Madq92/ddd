package cc.mikaka.ddd.common.lock.impl;


import cc.mikaka.ddd.common.lock.DistributeLockEnum;
import cc.mikaka.ddd.common.lock.DistributeLockService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component("distributeLockService")
public class DefaultDistributeLockServiceImpl implements DistributeLockService {


    @Override
    public boolean lock(DistributeLockEnum distributeLock, String key, String requestId) {
        return true;
    }

    @Override
    public boolean lock(DistributeLockEnum distributeLock, String key, String requestId, int getLockTime) {
        return true;
    }

    @Override
    public boolean lock(DistributeLockEnum distributeLock, String key, String requestId, int getLockTime, int lockTime) {
        return true;
    }

    @Override
    public boolean tryLock(DistributeLockEnum distributeLock, String key, String requestId) {
        return true;
    }

    @Override
    public boolean tryLock(DistributeLockEnum distributeLock, String key, String requestId, int lockTime) {
        return true;
    }

    @Override
    public boolean unlock(DistributeLockEnum distributeLock, String key, String requestId) {
        return true;
    }

    @Override
    public Boolean findHasLock(DistributeLockEnum distributeLock, String key) {
        return true;
    }
}
