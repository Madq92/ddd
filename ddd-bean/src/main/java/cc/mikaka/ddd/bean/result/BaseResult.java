package cc.mikaka.ddd.bean.result;

import cc.mikaka.ddd.bean.ToString;

abstract public class BaseResult extends ToString {

    /**
     * 业务处理是否成功 true：成功、false：请求异常
     */
    private boolean success = false;

    /**
     * 业务处理结果（错误码）
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
