package com.mark.mds.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/7 11:00
 */
@Data
@ApiModel(value = "站点通知邮件实体")
public class SiteNoticeVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接收人邮件地址")
    @NotBlank(message = "邮件地址不能为空")
    @Email(message = "请填写合法邮件")
    private String toMail;

    @ApiModelProperty("邮件主题")
    private String subject;

    @ApiModelProperty("邮件内容")
    @NotBlank(message = "邮件内容不能为空")
    private String text;
}
