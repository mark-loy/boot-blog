package com.mark.site.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.aspectj.lang.annotation.DeclareAnnotation;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/6 19:55
 */
@Data
@Accessors(chain = true)
@ApiModel("留言表单实体")
public class GuestbookFormVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("邮箱地址")
    @NotBlank(message = "邮箱地址不能为空")
    @Email(message = "邮箱不合法")
    private String mail;

    @ApiModelProperty("留言内容")
    @NotBlank(message = "留言内容不能为空")
    private String content;
}
