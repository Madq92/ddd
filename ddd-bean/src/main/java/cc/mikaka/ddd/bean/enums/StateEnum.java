package cc.mikaka.ddd.bean.enums;

import lombok.Getter;

@Getter
public enum StateEnum {
    /**
     * 生效
     * */
    INIT("INIT", "初始化"),

    /**
     * 生效
     * */
    ONLINE("ONLINE", "生效"),

    /**
     * 下架(暂停/停用)
     */
    PAUSE("PAUSE", "下架"),

    /**
     * 删除
     * */
    INVALID("INVALID", "删除");

    /**
     * 类型值
     */
    private String code;

    /**
     * 类型名称
     */
    private String desc;

    StateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
