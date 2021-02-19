package com.mark.blog.entity.vo;

import com.mark.base.valid.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/22 18:32
 */
@Data
@ApiModel(value = "文章结果对象")
public class ArticleResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章概要")
    private String summary;

    @ApiModelProperty(value = "文章置顶状态;0.表示未置顶，1.表示已置顶")
    private Boolean isTop;

    @ApiModelProperty(value = "发表人id")
    private String userId;

    @ApiModelProperty(value = "发表人名称")
    private String userName;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

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

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "标签id")
    private List<String> tagIds;

    @ApiModelProperty(value = "标签名称集合")
    private List<String> tagsName;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
