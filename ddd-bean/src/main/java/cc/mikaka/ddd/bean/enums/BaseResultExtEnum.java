package cc.mikaka.ddd.bean.enums;

import org.apache.commons.lang3.StringUtils;

public enum BaseResultExtEnum {

    /**
     * 业务类型
     */
    BIZ_TYPE("BIZ_TYPE","业务类型"),

    /**
     * 操作类型
     */
    ACTION_TYPE("ACTION_TYPE","操作类型"),

    /**
     *  批量修改的类型
     */
    BATCH_EDIT_TYPE("BATCH_EDIT_TYPE","批量修改的类型"),
    ;
    private String key;

    private String desc;

    BaseResultExtEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据key找枚举
     * @param key
     * @return
     */
    public static BaseResultExtEnum getTypeByKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        for (BaseResultExtEnum e : BaseResultExtEnum.values()) {
            if (e.key.equalsIgnoreCase(key)) {
                return e;
            }
        }
        return null;
    }
}
