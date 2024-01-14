package cc.mikaka.ddd.common.enums;

import lombok.Getter;

@Getter
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
    private final String code;

    /**
     * desc
     */
    private final String desc;

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
}
