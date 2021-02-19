package com.mark.base.handler;

import com.mark.base.exception.CustomException;
import com.mark.common.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;

/**
 * spring boot统一异常处理
 * @author 木可
 * @version 1.0
 * @date 2020/12/19 21:13
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局异常处理
     * @param e Exception
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    public Result exceptionHandler(Exception e) {
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }

    /**
     * 自定义异常
     * @param e CustomException
     * @return Result
     */
    @ExceptionHandler(CustomException.class)
    public Result customExceptionHandler(CustomException e) {
        log.error(e.getCode() + "====" +e.getMsg());
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

    /**
     * 特定异常 ==》算术异常
     * @param e ArithmeticException
     * @return Result
     */
    @ExceptionHandler(ArithmeticException.class)
    public Result arithmeticExceptionHandler(ArithmeticException e){
        log.error("断言异常------{}", e.getMessage());
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }

    /**
     * 特定异常 ==》断言异常
     * @param ex ArithmeticException
     * @return Result
     */
    @ExceptionHandler(AssertionError.class)
    public Result exceptionHandler(AssertionError ex) {
        log.info("断言异常------{}", ex.getMessage());
        ex.printStackTrace();
        return Result.error().message(ex.getMessage());
    }

    /**
     * 特定异常 ==》实体参数校验异常
     * @param ex ArithmeticException
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result exceptionHandler(MethodArgumentNotValidException ex) {
        ObjectError objectError = ex.getBindingResult().getAllErrors().stream().findFirst().get();
        log.info("实体参数校验异常------{}", objectError.getDefaultMessage());
        ex.printStackTrace();
        return Result.error().message(objectError.getDefaultMessage());
    }

    /**
     * 特定异常 ==》实体参数类型校验异常
     * @param ex ArithmeticException
     * @return Result
     */
    @ExceptionHandler(UnexpectedTypeException.class)
    public Result exceptionHandler(UnexpectedTypeException ex) {
        log.info("实体参数类型校验异常------{}", ex.getMessage());
        ex.printStackTrace();
        return Result.error().message(ex.getMessage());
    }


}
