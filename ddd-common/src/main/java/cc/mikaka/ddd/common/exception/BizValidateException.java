package cc.mikaka.ddd.common.exception;


import cc.mikaka.ddd.common.error.ErrorCode;

/**
 * 功能描述: 业务校验异常
 *
 */
public class BizValidateException extends RuntimeException {

    private static final long serialVersionUID = -1532585207732638732L;
    /**
     * 业务逻辑内部错误码
     */
    private ErrorCode errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    public BizValidateException() {
        super();
    }

    public BizValidateException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public BizValidateException(ErrorCode errorCode, Throwable t) {
        super(errorCode.getCode(), t);
        this.errorCode = errorCode;
    }

    public BizValidateException(ErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BizValidateException(ErrorCode errorCode, String errorDesc, Throwable t) {
        super(errorCode.getCode() + ":" + errorDesc, t);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BizValidateException(Throwable t) {
        super(t);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
