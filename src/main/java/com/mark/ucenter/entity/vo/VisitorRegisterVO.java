package com.mark.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/31 18:13
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "访客注册对象")
public class VisitorRegisterVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("昵称")
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @ApiModelProperty("邮箱")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "请输入合法邮箱")
    private String email;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    @Length(max = 15, min = 6, message = "密码长度在6-15位")
    private String password;

    @ApiModelProperty("验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
