package cc.mikaka.ddd.bean.result;

import cc.mikaka.ddd.bean.ToString;
import cc.mikaka.ddd.bean.enums.BaseResultExtEnum;


import java.util.HashMap;
import java.util.Map;

abstract public class BaseResult extends ToString {

    /**
     * 业务处理是否成功 true：成功、false：请求异常
     */
    private boolean success = false;

    /**
     * 业务处理结果（错误码）
     *
     */
    private String errorCode;

    /**
     * 错误描述
     */
    private String errorDesc;

    private Map<String, String> extMap;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public Map<String, String> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
    }

    public void putExt(String key, String value) {
        if (extMap == null) {
            extMap = new HashMap<String, String>();
        }
        BaseResultExtEnum extEnum = BaseResultExtEnum.getTypeByKey(key);
        if (extEnum == null) {
            return;
        }
        extMap.put(key, value);
    }

    public String getExtByKey(String key) {
        if (extMap == null) {
            return null;
        }
        return extMap.get(key);
    }
}
