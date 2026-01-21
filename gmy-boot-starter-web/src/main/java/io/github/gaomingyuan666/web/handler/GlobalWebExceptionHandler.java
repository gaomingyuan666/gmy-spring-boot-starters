package io.github.gaomingyuan666.web.handler;

import com.google.common.collect.Maps;
import io.github.gaomingyuan666.base.exception.BizException;
import io.github.gaomingyuan666.base.exception.SystemException;
import io.github.gaomingyuan666.base.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

import static io.github.gaomingyuan666.base.response.ResponseCode.SYSTEM_ERROR;

/**
 * @author gaomingyuan
 */
@ControllerAdvice
@Slf4j
public class GlobalWebExceptionHandler {

    /**
     * 自定义方法参数校验异常处理器
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException occurred.", ex);
        Map<String, String> errors = Maps.newHashMapWithExpectedSize(1);
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * 自定义业务异常处理器
     *
     * @param bizException
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse exceptionHandler(BizException bizException) {
        log.error("bizException occurred.", bizException);
        BaseResponse result = new BaseResponse();
        result.setCode(bizException.getErrorCode().getCode());
        result.setMessage(bizException.getErrorCode().getMessage());
        result.setSucceed(false);
        return result;
    }

    /**
     * 自定义系统异常处理器
     *
     * @param systemException
     * @return
     */
    @ExceptionHandler(SystemException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse systemExceptionHandler(SystemException systemException) {
        log.error("systemException occurred.", systemException);
        BaseResponse result = new BaseResponse();
        result.setCode(systemException.getErrorCode().getCode());
        result.setMessage(systemException.getErrorCode().getMessage());
        result.setSucceed(false);
        return result;
    }

    /**
     * 自定义系统异常处理器
     *
     * @param throwable
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse throwableHandler(Throwable throwable) {
        log.error("throwable occurred.", throwable);
        BaseResponse result = new BaseResponse();
        result.setCode(SYSTEM_ERROR.name());
        result.setMessage("哎呀，当前网络比较拥挤，请您稍后再试~");
        result.setSucceed(false);
        return result;
    }
}
