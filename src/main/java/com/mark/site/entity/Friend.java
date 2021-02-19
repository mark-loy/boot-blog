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
 * 友站信息表
 * </p>
 *
 * @author mark
 * @since 2021-02-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("site_friend")
@ApiModel(value="Friend对象", description="友站信息表")
public class Friend implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "友站id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "友站名称")
    private String name;

    @ApiModelProperty(value = "友站链接")
    private String friendUrl;

    @ApiModelProperty(value = "友站头像")
    private String imageUrl;

    @ApiModelProperty(value = "友站简介")
    private String friendSummary;

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
