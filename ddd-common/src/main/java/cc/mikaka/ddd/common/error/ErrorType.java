package cc.mikaka.ddd.common.error;

import org.apache.commons.lang3.StringUtils;

/**
 * 错误类型
 * <PRE>
 * 错误类型：
 * 0 系统错误
 * 1 业务错误
 * 2 第三方错误
 * </PRE>
 *
 **/
public enum ErrorType {

    /**
     * SYS("0") 0 系统错误
     **/
    SYS("0"),
    /**
     * BIZ("1") 1 业务错误
     **/
    BIZ("1"),
    /**
     * REF("2") 2 第三方错误
     **/
    REF("2");

    private final String type;

    /**
     * Constructor for ErrorType.
     *
     * @param type String
     */
    ErrorType(String type) {
        this.type = type;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */
    public String getType() {
        return type;
    }

    /**
     * Method getByType.
     *
     * @param type String
     * @return ErrorType
     */
    public static ErrorType getByType(String type) {
        for (ErrorType errorType : values()) {
            if (StringUtils.equals(type, errorType.getType())) {
                return errorType;
            }
        }
        return null;
    }
}
