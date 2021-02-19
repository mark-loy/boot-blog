package com.mark.sta.entity;

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
 * 文章统计表
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sta_article")
@ApiModel(value="StaArticle对象", description="文章统计表")
public class StaArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "统计id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "统计日期")
    private String staDate;

    @ApiModelProperty(value = "每日文章总浏览数")
    private Integer viewNum;

    @ApiModelProperty(value = "每日文章总评论数")
    private Integer commentNum;

    @ApiModelProperty(value = "每日文章总点赞数")
    private Integer likeNum;

    @ApiModelProperty(value = "每日文章发表数")
    private Integer publishNum;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
