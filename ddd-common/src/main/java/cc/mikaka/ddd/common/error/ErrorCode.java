package cc.mikaka.ddd.common.error;


public interface ErrorCode {
    String getCode();

    String getDesc();

    ErrorType getErrorType();
}