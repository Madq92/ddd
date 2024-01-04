package cc.mikaka.ddd.bean.enums;

/**
 * 排序属性字段枚举类（不在此类中的排序，统统驳回服务请求）
 */
public enum OrderRuleEnum {

    /**
     * ID 升序 (默认id倒叙，创建时间倒叙)
     */
    ID_ASC("id", "", false, OrderTypeEnum.ASC),

    /**
     * iD 降序
     */
    ID_DESC("id", "", false, OrderTypeEnum.DESC),

    /**
     * 自定义排序字段 升序
     */
    SORT_ASC("sort", "sort", false, "sort", OrderTypeEnum.ASC),

    /**
     * 自定义排序字段 降序
     */
    SORT_DESC("sort", "sort", false, "sort", OrderTypeEnum.DESC),


    /**
     * 更新时间 升序
     */
    GMT_MODIFIED_ASC("gmt_modified", "gmtModified", false, "gmt_modified", OrderTypeEnum.ASC),

    /**
     * 更新时间 降序
     */
    GMT_MODIFIED_DESC("gmt_modified", "gmtModified", false, "gmt_modified", OrderTypeEnum.DESC),

    /**
     * 创建时间倒序
     */
    GMT_CREATE_DESC("gmt_create", "gmtCreate", false, "gmt_create", OrderTypeEnum.DESC),

    /**
     * 创建时间正排序
     */
    GMT_CREATE_ASC("gmt_create", "gmtCreate", false, "gmt_create", OrderTypeEnum.ASC),


    // add others column
    ;
    /**
     * 字段属性名称
     */
    private String columnName;

    /**
     * 字段属性名称
     */
    private String esOrderColumnName;

    /**
     * OpenSearch字段名
     */
    private String openSearchColumnName;

    /**
     * es keyword 属性字段
     */
    private Boolean isEsKeywordColumn;

    /**
     * 排序规则
     */
    private OrderTypeEnum orderType;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public OrderTypeEnum getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderTypeEnum orderType) {
        this.orderType = orderType;
    }

    public String getEsOrderColumnName() {
        return esOrderColumnName;
    }

    public void setEsOrderColumnName(String esOrderColumnName) {
        this.esOrderColumnName = esOrderColumnName;
    }

    public String getOpenSearchColumnName() {
        return openSearchColumnName;
    }

    public void setOpenSearchColumnName(String openSearchColumnName) {
        this.openSearchColumnName = openSearchColumnName;
    }

    OrderRuleEnum(String columnName, String esOrderColumnName, Boolean isEsKeywordColumn, OrderTypeEnum orderType) {
        this.columnName = columnName;
        this.orderType = orderType;
        this.esOrderColumnName = esOrderColumnName;
        this.isEsKeywordColumn = isEsKeywordColumn;
    }

    OrderRuleEnum(String columnName, String esOrderColumnName, Boolean isEsKeywordColumn, String openSearchColumnName,
                  OrderTypeEnum orderType) {
        this.columnName = columnName;
        this.orderType = orderType;
        this.esOrderColumnName = esOrderColumnName;
        this.isEsKeywordColumn = isEsKeywordColumn;
        this.openSearchColumnName = openSearchColumnName;
    }

    public Boolean getIsEsKeywordColumn() {
        return isEsKeywordColumn;
    }

    public void setIsEsKeywordColumn(Boolean isEsKeywordColumn) {
        this.isEsKeywordColumn = isEsKeywordColumn;
    }
}
