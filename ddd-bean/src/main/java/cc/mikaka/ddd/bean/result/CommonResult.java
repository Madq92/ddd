package cc.mikaka.ddd.bean.result;

public class CommonResult<T> extends BaseResult {

    private static final long serialVersionUID = 8297885192337681663L;

    /**
     * 业务主键
     */
    private String bizId;

    /**
     * 返回的业务数据
     */
    private T bizData;

    public T getBizData() {
        return bizData;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public void setBizData(T bizData) {
        this.bizData = bizData;
    }

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
        result.setBizData(data);
        result.setSuccess(true);
        return result;
    }
}
