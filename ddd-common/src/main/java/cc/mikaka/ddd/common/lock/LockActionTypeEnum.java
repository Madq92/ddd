package cc.mikaka.ddd.common.lock;

/***
 * 分布式锁实现类型
 * */
public enum LockActionTypeEnum {
    EDIT("EDIT", "编辑"),

    DELETE("DELETE", "删除");

    private String code;
    private String name;

    LockActionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
