package cc.mikaka.ddd.common.exception;


import cc.mikaka.ddd.common.error.ErrorCode;
import lombok.Data;

/**
 * 功能描述: 业务校验异常
 */
@Data
public class BizValidateException extends RuntimeException {
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
}
