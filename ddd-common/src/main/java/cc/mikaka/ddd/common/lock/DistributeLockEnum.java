package cc.mikaka.ddd.common.lock;

/***
 * 分布式锁类型
 * */
public enum DistributeLockEnum {
    /**
     * 业务锁
     */
    BIZ("BIZ", "BIZ", "业务锁"),

    /**
     * 事件锁
     */
    EVENT_MESSAGE("EVENT_MESSAGE", "EVENT_MESSAGE", "事件锁"),


    /**
     * 任务重试锁
     */
    JOB_RETRY("JOB_RETRY", "JOB_RETRY", "任务重试锁"),

    ;

    /**
     * 业务类型
     */
    private final String id;

    /**
     * code
     */
    private final String code;
    /**
     * 描述
     */
    private final String desc;

    DistributeLockEnum(String biz, String code, String desc) {
        this.id = biz;
        this.desc = desc;
        this.code = code;
    }
}
