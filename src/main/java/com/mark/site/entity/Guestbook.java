package com.mark.site.entity;

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
 * 站点留言表
 * </p>
 *
 * @author mark
 * @since 2021-02-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("site_guestbook")
@ApiModel(value="Guestbook对象", description="站点留言表")
public class Guestbook implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "留言id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "留言邮箱")
    private String mail;

    @ApiModelProperty(value = "留言内容")
    private String content;

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
