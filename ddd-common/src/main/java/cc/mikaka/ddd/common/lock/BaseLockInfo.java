package cc.mikaka.ddd.common.lock;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class BaseLockInfo {
    /**
     * 获取锁的时间(毫秒)-子类可自定义
     */
    protected int fetchLockMillTime = 1000;

    /**
     * 数据锁定时间(毫秒)-子类可自定义
     */
    protected int lockedMillTime = 5000;

    /**
     * 标：数据是否被锁定
     */
    protected boolean locked = false;

    /**
     * 操作类型
     */
    protected LockActionTypeEnum lockActionTypeEnum;

    /**
     * 机构
     */
    protected String merchantId;

    /**
     * 品牌id
     */
    protected String brandId;

    /**
     * 机构id
     */
    protected String orgId;

    /**
     * 返回锁key
     *
     * @return
     */
    public abstract String toLockKey();

    /**
     * 错误信息
     *
     * @return
     */
    public abstract String getErrorMessage();
}
    
    