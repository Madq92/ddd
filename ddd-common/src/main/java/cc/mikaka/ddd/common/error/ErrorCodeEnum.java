package cc.mikaka.ddd.common.error;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum implements ErrorCode {

    /**
     * 业务处理成功
     */
    SUCCESS("SUCCESS", "处理成功", null),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常，请稍后重试", ErrorType.SYS),

    /**
     * 入参异常
     */
    PARAM_ERROR("PARAM_ERROR", "入参异常", ErrorType.BIZ),

    /**
     * 业务参数异常
     */
    BIZ_PARAM_ERROR("BIZ_PARAM_ERROR", "业务参数异常", ErrorType.BIZ),

    /**
     * 创建业务标示号失败
     */
    BIZ_KEY_IS_NULL("BIZ_KEY_IS_NULL", "创建业务标示号失败", ErrorType.BIZ),

    /**
     * 找不到业务校验器
     */
    BIZ_VALIDATE_CANNOT_FOUND("BIZ_VALIDATE_CANNOT_FOUND", "找不到业务校验器", ErrorType.BIZ),

    /**
     * 找不到领域业务执行器
     */
    BIZ_PROCESSOR_CANNOT_FOUND("BIZ_PROCESSOR_CANNOT_FOUND", "找不到领域业务执行器", ErrorType.BIZ),

    /**
     * request不能为空
     */
    REQUEST_IS_NULL("REQUEST_IS_NULL", "request不能为空", ErrorType.BIZ),

    /**
     * requestId不能为空
     */
    REQUEST_ID_IS_NULL("REQUEST_ID_IS_NULL", "requestId不能为空", ErrorType.BIZ),

    ;

    ErrorCodeEnum(String code, String desc, ErrorType errorType) {
        this.code = code;
        this.desc = desc;
        this.errorType = errorType;
    }

    /**
     * code
     */
    private String code;

    /**
     * desc
     */
    private String desc;

    /**
     * 错误类型
     */
    private ErrorType errorType;
}
