package cc.mikaka.ddd.common.enums;


public enum ActionType {
    /**
     * 查询(单个)
     */
    QUERY("QUERY_SINGLE", "查询"),

    /**
     * 查询批量
     */
    QUERY_BATCH("QUERY_BATCH", "查询批量"),

    /**
     * 查询分页
     */
    QUERY_PAGE("QUERY_PAGE", "查询分页"),

    /**
     * 创建
     */
    CREATE("CREATE", "创建"),

    /**
     * 批量创建
     */
    BATCH_CREATE("BATCH_CREATE", "创建"),

    /**
     * 编辑
     */
    EDIT("EDIT", "编辑"),

    /**
     * 删除
     */
    DELETE("DELETE", "删除"),

    /**
     * 启用
     */
    ENABLE("ENABLE", "启用"),

    /**
     * 停用
     */
    DISABLE("DISABLE", "停用"),

    /**
     * 重试
     */
    RETRY("RETRY", "重试")
    // add other enum code
    ;

    private String code;


    private String desc;

    ActionType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据标识码获取对应枚举
     *
     * @param code 标识码
     * @return 对应枚举
     */
    public static ActionType getByCode(String code) {
        if (null == code) {
            return null;
        }
        ActionType[] values = ActionType.values();
        for (ActionType value : values) {
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
