package com.mark.base.exception;

import com.mark.base.enums.CustomExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 * @author 木可
 * @version 1.0
 * @date 2020/12/20 12:04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException{

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 异常信息
     */
    private String msg;

    public CustomException(CustomExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }
}
