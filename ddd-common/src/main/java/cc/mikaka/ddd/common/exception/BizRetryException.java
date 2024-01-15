package cc.mikaka.ddd.common.exception;


import cc.mikaka.ddd.common.error.ErrorCode;
import lombok.Data;

/**
 * 业务重试的异常
 */
@Data
public class BizRetryException extends RuntimeException {
    /**
     * 业务逻辑内部错误码
     */
    private ErrorCode errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    public BizRetryException() {
        super();
    }

    public BizRetryException(ErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
