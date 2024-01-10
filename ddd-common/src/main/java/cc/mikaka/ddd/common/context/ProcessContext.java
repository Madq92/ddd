package cc.mikaka.ddd.common.context;


import org.springframework.stereotype.Component;

@Component
public class ProcessContext {
    /**
     * 通用请求基本信息
     */
    private static final ThreadLocal<BaseContext> COMMON_REQUEST = new ThreadLocal<>();


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
     * 清理
     */
    public static void cleanThreadLocal() {
        COMMON_REQUEST.remove();
    }
}
