package cc.mikaka.ddd.common.enums;

public enum BizType {
    /**
     * 业务配置
     */
    BIZ_CONFIG("BIZ_CONFIG", "业务配置"),

    /**
     * USER
     */
    USER("USER", "用户");

    /**
     * code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    BizType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据标识码获取对应枚举
     *
     * @param code 标识码
     * @return 对应枚举
     */
    public static BizType getByCode(String code) {
        if (null == code) {
            return null;
        }
        BizType[] values = BizType.values();
        for (BizType value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
