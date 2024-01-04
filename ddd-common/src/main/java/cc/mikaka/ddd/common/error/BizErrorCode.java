package cc.mikaka.ddd.common.error;

/**
 * 业务逻辑内部错误码
 **/
public enum BizErrorCode implements ErrorCode {
    /**
     * 业务处理成功
     */
    SUCCESS("SUCCESS", "处理成功", null),

    /**
     * 入参为空
     */
    PARAM_ILLEGAL("PARAM_ILLEGAL", "参数不合法", ErrorType.BIZ),

    /**
     * 业务员数据校验异常
     */
    BIZ_VALIDATE_ILLEGAL("BIZ_VALIDATE_ILLEGAL", "业务数据校验异常", ErrorType.BIZ),

    /**
     * 入参为空
     */
    PARAM_IS_NULL("PARAM_IS_NULL", "入参为空", ErrorType.BIZ),

    /**
     * 业务数据不存在
     */
    BIZ_DATA_NOT_EXIST("BIZ_DATA_NOT_EXIST", "业务数据不存在", ErrorType.BIZ),

    /**
     * 业务数据重复
     */
    BIZ_DATA_DUPLICATE("BIZ_DATA_DUPLICATE", "业务数据已经存在", ErrorType.BIZ),

    /**
     * 名称已存在
     */
    DATA_NAME_DUPLICATE("DATA_NAME_DUPLICATE", "名称已存在", ErrorType.BIZ),


    /**
     * 数据不允许操作
     */
    DATA_OPERATION_NOT_ALLOWED("DATA_OPERATION_NOT_ALLOWED", "数据不允许操作", ErrorType.BIZ),


    /**
     * spring上下文加载Bean失败
     */
    LOAD_BEAN_FAILURE("LOAD_BEAN_FAILURE", "spring上下文加载Bean失败", ErrorType.SYS),

    /**
     * 缓存业务key非法
     */
    CACHE_KEY_ILLEGAL("CACHE_KEY_ILLEGAL", "缓存业务key非法", ErrorType.SYS),

    /**
     * 业务处理器不存在
     */
    PROCESSOR_NOT_EXIST("PROCESSOR_NOT_EXIST", "业务处理器不存在", ErrorType.SYS),

    /**
     * 数据被锁定
     */
    BIZ_DATA_LOCKED("DATA_LOCKED", "重复数据请求", ErrorType.BIZ),

    /**
     * 系统异常
     */
    SYSTEM_ERROR("SYSTEM_ERROR", "系统异常", ErrorType.SYS),

    /**
     * 业务异常
     */
    BIZ_ERROR("BIZ_ERROR", "业务异常", ErrorType.BIZ),


    /**
     * 网络超时异常
     */
    TIMEOUT_ERROR("TIMEOUT_ERROR", "网络超时异常", ErrorType.SYS),


    ;

    /**
     * code
     */
    private String code;
    /**
     * desc
     */
    private String desc;
    /**
     * type
     */
    private ErrorType errorType;


    BizErrorCode(String code, String desc, ErrorType type) {
        this.code = code;
        this.desc = desc;
        this.errorType = type;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public ErrorType getErrorType() {
        return errorType;
    }
}
