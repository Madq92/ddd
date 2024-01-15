package cc.mikaka.ddd.common.exception;

import cc.mikaka.ddd.common.error.ErrorCode;
import lombok.Data;

/**
 * 内部服务异常
 */
@Data
public class BizServiceException extends RuntimeException {

    /**
     * 业务逻辑内部错误码
     */
    private final ErrorCode errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    public BizServiceException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public BizServiceException(ErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public BizServiceException(Throwable t, ErrorCode errorCode) {
        super(errorCode.getCode(), t);
        this.errorCode = errorCode;
    }

    public BizServiceException(Throwable t, ErrorCode errorCode, String errorDesc) {
        super(errorCode.getCode() + ":" + errorDesc, t);
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }
}
