package cc.mikaka.ddd.common.lock;

import lombok.Getter;

/***
 * 分布式锁实现类型
 * */
@Getter
public enum LockActionTypeEnum {
    EDIT("EDIT", "编辑"),

    DELETE("DELETE", "删除");

    private final String code;
    private final String name;

    LockActionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
