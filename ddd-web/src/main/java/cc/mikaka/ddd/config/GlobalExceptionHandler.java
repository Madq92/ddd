package cc.mikaka.ddd.config;

import cc.mikaka.ddd.bean.result.CommonResult;
import cc.mikaka.ddd.common.error.BizErrorCode;
import cc.mikaka.ddd.common.error.ErrorCode;
import cc.mikaka.ddd.common.exception.BizServiceException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order
@Log4j2
public class GlobalExceptionHandler {
    @ExceptionHandler({BizServiceException.class, BizServiceException.class})
    @ResponseBody
    public ResponseEntity<CommonResult<Void>> baseException(BizServiceException e) {
        ErrorCode error = e.getErrorCode();
        CommonResult<Void> result = CommonResult.createError(error.getCode(), error.getDesc());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CommonResult<Void>> seriousHandler(Exception e) {
        log.error("system error: {}", e.getMessage(), e);
        ErrorCode error = BizErrorCode.SYSTEM_ERROR;
        CommonResult<Void> result = CommonResult.createError(error.getCode(), error.getDesc());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }
}
