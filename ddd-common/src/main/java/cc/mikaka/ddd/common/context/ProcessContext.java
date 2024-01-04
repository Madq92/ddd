package cc.mikaka.ddd.common.context;


import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

@Component
public class ProcessContext {
    /**
     * 业务识别号
     */
    private static final ThreadLocal<String> BIZ_KEY = new ThreadLocal<>();

    /**
     * 操作表列表
     */
    private static final ThreadLocal<ArrayList<String>> DAO_LIST = new ThreadLocal<>();

    /**
     * 通用请求基本信息
     */
    private static final ThreadLocal<BaseContext> COMMON_REQUEST = new ThreadLocal<>();

    static {
        DAO_LIST.set(new ArrayList<>(16));
    }

    /**
     * 获得通用请求基本信息
     */
    public static BaseContext getBaseContext() {
        return COMMON_REQUEST.get();
    }


    /**
     * 设置通用请求基本信息
     */
    public static void setCommonRequest(BaseContext request) {
        COMMON_REQUEST.set(request);
    }

    /**
     * 读取业务识别号
     */
    public static String getBizProcessKey() {
        return BIZ_KEY.get();
    }

    /**
     * 设置业务识别号
     *
     * @param bizKey 业务识别号
     */
    public static void setBizProcessKey(String bizKey) {
        BIZ_KEY.set(bizKey);
    }

    /**
     * 设置操作列表
     *
     * @param
     */
    public static ArrayList<String> getDaoList() {
        ArrayList<String> list = DAO_LIST.get();
        if (!CollectionUtils.isEmpty(list)) {
            return list;
        }
        DAO_LIST.set(new ArrayList<>(16));
        return DAO_LIST.get();
    }


    /**
     * 清理
     */
    public static void cleanThreadLocal() {
        BIZ_KEY.remove();
        COMMON_REQUEST.remove();
        DAO_LIST.remove();
    }
}
