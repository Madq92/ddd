package cc.mikaka.ddd.event;

public enum MessageStateEnum {
    /**
     * 生效
     */
    INIT("INIT", "初始化"),

    /**
     * 处理成功
     */
    SUCCESS("SUCCESS", "处理成功"),

    /**
     * 跳过处理
     */
    SKIP("SKIP", "跳过处理"),

    /**
     * 处理失败
     */
    FAIL("FAIL", "处理失败");

    /**
     * 类型值
     */
    private String code;

    /**
     * 类型名称
     */
    private String desc;

    MessageStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
