package cc.mikaka.ddd.service.event;

public enum EventMessageType {
    /**
     * 引用计数
     */
    DISH_REFERENCE("DISH_REFERENCE", "引用计数"),

    /**
     * 菜品同步菜单
     */
    DISH_2_COOKBOOK_DISH("DISH_2_COOKBOOK_DISH", "菜品同步菜单"),

    /**
     * PUSH_ES
     */
    PUSH_ES("PUSH_ES", "PUSH_ES"),

    /**
     * 关联推送ES
     */
    ASSOCIATION_PUSH_ES("ASSOCIATION_PUSH_ES", "关联推送ES"),

    /**
     * 门店菜品列表同步ic
     */
    FISSION_STORE_DISH_IC("FISSION_STORE_DISH_IC", "门店菜品列表同步ic"),

    /**
     * 修改菜单发布状态
     */
    CHANGE_COOKBOOK_PUB_STATE("CHANGE_COOKBOOK_PUB_STATE", "修改菜单发布状态"),

    COOKBOOK_DISH_CHANGE("COOKBOOK_DISH_CHANGE", "菜单菜数据变更"),

    COOKBOOK_DISH_PUB("COOKBOOK_DISH_PUB", "菜单菜轻量双写"),

    COOKBOOK_PUB("COOKBOOK_PUB", "菜单轻量双写"),

    SYNC_DISH_CATEGORY_TO_COOKBOOK("SYNC_DISH_CATEGORY_TO_COOKBOOK", "菜品分类同步到菜单分类"),

    SYNC_DISH_CATEGORY_2_COOKBOOK("SYNC_DISH_CATEGORY_2_COOKBOOK", "菜品分类同步到菜单分类"),

    SYNC_DISH_TO_DISH_SHOP("SYNC_DISH_TO_DISH_SHOP", "同步菜品到门店菜品库"),

    SYNC_DISH_TO_SHOP_COOKBOOK("SYNC_DISH_TO_SHOP_COOKBOOK", "连锁门店/单店同步菜品到门店菜单"),

    SYNC_DISH_TO_COOKBOOK_TEMPLATE("SYNC_DISH_TO_COOKBOOK_TEMPLATE", "同步菜品到菜谱模版"),

    SYNC_DISH_SORT_TO_SHOP("SYNC_DISH_SORT_TO_SHOP", "同步菜品排序到门店菜品库"),

    SYNC_DISH_SORT_TO_SHOP_COOKBOOK("SYNC_DISH_SORT_TO_SHOP_COOKBOOK", "同步菜品排序到门店菜单"),

    SYNC_PROP_BIND_TO_SHOP_COOKBOOK("SYNC_PROP_BIND_TO_SHOP_COOKBOOK", "同步菜品属性关系到门店菜单"),

    SYNC_UPLOAD_IMG_TO_SHOP_COOKBOOK("SYNC_UPLOAD_IMG_TO_SHOP_COOKBOOK", "同步上传图片到门店菜单"),

    SYNC_CENTER_SEND_MESSAGE("SYNC_CENTER_SEND_MESSAGE", "同步下行统一服务发送"),

    AUTO_PUB_MESSAGE("AUTO_PUB_MESSAGE", "单店版本自动发布菜单"),

    DOMAIN_EVENT_BURY_MESSAGE("DOMAIN_EVENT_BURY_MESSAGE", "领域事件埋点"),

    SHOP_PROP_AUTO_PUB_MESSAGE("SHOP_PROP_AUTO_PUB_MESSAGE", "门店属性变更同步菜单"),

    SYNC_CONFIG("SYNC_CONFIG", "配置变更同步到生产域菜单"),

    DISH_STOCK_EVENT_MESSAGE("DISH_STOCK_EVENT_MESSAGE", "售卖量状态变化事件"),

    PRACTICE_SELL_OUT_MESSAGE("PRACTICE_SELL_OUT_MESSAGE", "做法沽清通知"),

    DISH_CATEGORY_DOMAIN_MESSAGE("DISH_CATEGORY_DOMAIN_MESSAGE", "菜品分类领域事件"),
    COOK_CATEGORY_DOMAIN_MESSAGE("COOK_CATEGORY_DOMAIN_MESSAGE", "菜单分类领域事件"),
    DISH_DOMAIN_MESSAGE("DISH_DOMAIN_MESSAGE", "菜品领域事件"),
    DISH_STOCK_DOMAIN_MESSAGE("DISH_STOCK_DOMAIN_MESSAGE", "菜品售卖量领域事件"),
    DISH_STOCK_CHANGE_DOMAIN_MESSAGE("DISH_STOCK_CHANGE_DOMAIN_MESSAGE", "菜品售卖量变更领域事件"),
    COOK_DISH_DOMAIN_MESSAGE("COOK_DISH_DOMAIN_MESSAGE", "菜单菜领域事件"),
    SINGLE_AUTO_DIST_MESSAGE("SINGLE_AUTO_DIST_MESSAGE", "单店自动下发事件"),
    CHAIN_MANUAL_DIST_MESSAGE("CHAIN_MANUAL_DIST_MESSAGE", "连锁下发事件"),

    SHOP_DICT_GROUP_MESSAGE("SHOP_DICT_DETAIL_MESSAGE", "门店字典分组事件"),

    SHOP_LABEL_CHANGE_MESSAGE("SHOP_LABEL_CHANGE_MESSAGE", "门店标签变更事件"),

    SHOP_UNIT_CHANGE_MESSAGE("SHOP_UNIT_CHANGE_MESSAGE", "门店单位变更事件"),

    SHOP_DICT_DETAIL_MESSAGE("SHOP_DICT_DETAIL_MESSAGE", "门店字典明细事件"),

    SHOP_DICT_DETAIL_SALE_STATE_MESSAGE("SHOP_DICT_DETAIL_SALE_STATE_MESSAGE", "门店字典售卖状态事件"),

    DISH_ALTER_NOTIFY_MESSAGE("DISH_ALTER_NOTIFY_MESSAGE", "菜品变更消息触达事件"),

    DICT_DETAIL_MESSAGE("DICT_DETAIL_MESSAGE", "字典明细事件"),

    COOKBOOK_TEMPLATE("COOKBOOK_TEMPLATE", "菜谱模版的事件消息"),

    WEIGHT_DISH_DOUBLE_UNIT_ENABLED_MESSAGE("WEIGHT_DISH_DOUBLE_UNIT_ENABLED_MESSAGE", "称重菜双单位开启事件"),
    ;

    /**
     * code
     */
    private String code;
    /**
     * desc
     */
    private String desc;

    /**
     * 基础表结构
     */

    EventMessageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据标识码获取对应枚举
     *
     * @param code 标识码
     * @return 对应枚举
     */
    public static EventMessageType getByCode(String code) {
        if (null == code) {
            return null;
        }
        EventMessageType[] values = EventMessageType.values();
        for (EventMessageType value : values) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
