package cc.mikaka.ddd.bean.enums;

import lombok.Getter;

/**
 * 排序属性字段枚举类（不在此类中的排序，统统驳回服务请求）
 */
@Getter
public enum OrderRuleEnum {

    /**
     * ID 升序
     */
    ID_ASC("id", OrderTypeEnum.ASC),

    /**
     * iD 降序
     */
    ID_DESC("id", OrderTypeEnum.DESC),

    /**
     * 自定义排序字段 升序
     */
    SORT_ASC("sort", OrderTypeEnum.ASC),

    /**
     * 自定义排序字段 降序
     */
    SORT_DESC("sort", OrderTypeEnum.DESC),


    /**
     * 创建时间 升序
     */
    SERVER_CREATE_TIME_ASC("server_create_time", OrderTypeEnum.ASC),

    /**
     * 创建时间 降序
     */
    SERVER_CREATE_TIME_DESC("server_create_time", OrderTypeEnum.DESC),

    /**
     * 更新时间倒序
     */
    SERVER_UPDATE_TIME_DESC("server_update_time", OrderTypeEnum.DESC),

    /**
     * 更新时间正排序
     */
    SERVER_UPDATE_TIME_ASC("server_update_time", OrderTypeEnum.ASC),


    // add others column
    ;

    /**
     * 字段属性名称
     */
    private final String columnName;
    /**
     * 排序规则
     */
    private final OrderTypeEnum orderType;

    OrderRuleEnum(String columnName, OrderTypeEnum orderType) {
        this.columnName = columnName;
        this.orderType = orderType;
    }

    public String getOrderCase() {
        return this.columnName + " " + orderType.name();
    }
}
