package cc.mikaka.ddd.bean.result;

public class CommonResult<T> extends BaseResult {
    /**
     * 返回的业务数据
     */
    private T data;

    /**
     * @return Result<T>
     */
    public static <T> CommonResult<T> create() {
        return new CommonResult<T>();
    }

    /**
     * 新增快速创建Result方法
     */
    public static <T> CommonResult<T> createSucc(T data) {
        CommonResult<T> result = CommonResult.create();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }

    /**
     * 新增快速创建Result方法
     */
    public static <T> CommonResult<T> createError(String errorCode, String errorDesc) {
        CommonResult<T> result = CommonResult.create();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorDesc(errorDesc);
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
