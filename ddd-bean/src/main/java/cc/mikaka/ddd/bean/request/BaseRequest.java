package cc.mikaka.ddd.bean.request;


import lombok.Data;

/**
 * 基础请求参数
 */
@Data
public class BaseRequest {
    //TODO 用户ID,session,鉴权相关

    private String requestId;
}
