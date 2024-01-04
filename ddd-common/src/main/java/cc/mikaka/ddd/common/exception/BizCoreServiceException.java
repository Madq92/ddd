package cc.mikaka.ddd.common.exception;


import cc.mikaka.ddd.common.error.ErrorCode;
import lombok.Data;

/**
 * 领域异常
 */
@Data
public class BizCoreServiceException extends RuntimeException {
    /**
     * 业务逻辑内部错误码
     */
    private ErrorCode errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    public BizCoreServiceException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public BizCoreServiceException(ErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BizCoreServiceException(Throwable t) {
        super(t);
    }
}
