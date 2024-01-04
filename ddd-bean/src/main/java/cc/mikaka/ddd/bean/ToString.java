package cc.mikaka.ddd.bean;

import java.io.Serializable;

public class ToString implements Serializable {
    @Override
    public String toString() {
        //TODO  序列化
        return null;
    }

    /**
     * 不需要某些字段
     *
     * @param fields 字段名称
     * @return
     */
    public String toStringWithExcludeFileds(String... fields) {
        //TODO  序列化
        return null;
    }
}
