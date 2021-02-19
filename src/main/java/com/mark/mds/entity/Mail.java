package com.mark.mds.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 邮件管理表
 * </p>
 *
 * @author mark
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mds_mail")
@ApiModel(value="Mail对象", description="邮件管理表")
public class Mail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮件id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "邮件接收者")
    private String toMail;

    @ApiModelProperty(value = "邮件发送者")
    private String fromMail;

    @ApiModelProperty(value = "邮件主题")
    private String subject;

    @ApiModelProperty(value = "内容主体")
    private String text;

    @ApiModelProperty(value = "逻辑删除;0.表示未删除，1.表示已删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
