package cc.mikaka.ddd.common.lock;

/***
 * 分布式锁类型
 * */
public enum DistributeLockEnum {
    /**
     * PAAS
     */
    PAAS("PAAS", "PAAS", "paas层"),

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
    private String id;

    /**
     * code
     */
    private String code;
    /**
     * 描述
     */
    private String desc;

    DistributeLockEnum(String biz, String code, String desc) {
        this.id = biz;
        this.desc = desc;
        this.code = code;
    }
}
