package com.mark.blog.entity;

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
 * 文章表
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_article")
@ApiModel(value="Article对象", description="文章表")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章概要")
    private String summary;

    @ApiModelProperty(value = "发表人id")
    private String userId;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "阅读数")
    private Integer viewCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "图片标题")
    private String imageTitle;

    @ApiModelProperty(value = "背景图地址")
    private String imageUrl;

    @ApiModelProperty(value = "图片链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "文章发布状态;0.表示未发布，1.表示已发布")
    private Boolean isReleased;

    @ApiModelProperty(value = "文章置顶状态;0.表示未置顶，1.表示已置顶")
    private Boolean isTop;

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
