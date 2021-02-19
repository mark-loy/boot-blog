package com.mark.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/31 18:26
 */
@Data
@ApiModel(value = "访客登录对象")
public class VisitorLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入合法邮箱")
    private String email;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Length(max = 15, min = 6, message = "密码长度在6-15之间")
    private String password;
}
