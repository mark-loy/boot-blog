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
 * 站点信息表
 * </p>
 *
 * @author mark
 * @since 2021-01-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("site_info")
@ApiModel(value="SiteInfo对象", description="站点信息表")
public class SiteInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "站点id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "站点名称")
    private String name;

    @ApiModelProperty(value = "站点背景图")
    private String banner;

    @ApiModelProperty(value = "域名")
    private String domain;

    @ApiModelProperty(value = "标语")
    private String slogan;

    @ApiModelProperty(value = "通知")
    private String notice;

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
