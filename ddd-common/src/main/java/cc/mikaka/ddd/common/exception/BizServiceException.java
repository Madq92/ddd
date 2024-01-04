package cc.mikaka.ddd.common.exception;

import cc.mikaka.ddd.common.error.BizErrorCode;
import cc.mikaka.ddd.common.error.ErrorCode;

public class BizServiceException extends RuntimeException {

    /**
     * 业务逻辑内部错误码
     */
    private ErrorCode errorCode;

    /**
     * 子错误码
     */
    private String subErrorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    public BizServiceException(BizErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public BizServiceException(BizErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BizServiceException(BizErrorCode errorCode, String errorDesc, String subErrorCode) {
        super(errorCode.getCode() + ":" + errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.subErrorCode = subErrorCode;
    }

    public BizServiceException(Throwable t, BizErrorCode errorCode) {
        super(errorCode.getCode(), t);
        this.errorCode = errorCode;
    }

    public BizServiceException(Throwable t, BizErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc, t);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public String getSubErrorCode() {
        return subErrorCode;
    }

    public void setSubErrorCode(String subErrorCode) {
        this.subErrorCode = subErrorCode;
    }
}
