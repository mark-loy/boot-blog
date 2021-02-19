package com.mark.blog.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/28 20:13
 */
@ApiModel(value = "前台文章对象")
@Data
@Accessors(chain = true)
public class ArticleResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文章id")
    private String id;

    @ApiModelProperty("发表人id")
    private String userId;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty(value = "文章概要")
    private String summary;

    @ApiModelProperty(value = "文章是否为热度文章；浏览数 > 100")
    private Boolean isHot;

    @ApiModelProperty(value = "文章置顶状态;0.表示未置顶，1.表示已置顶")
    private Boolean isTop;

    @ApiModelProperty("文章内容")
    private String content;

    @ApiModelProperty("分类id")
    private String cateId;

    @ApiModelProperty("分类名称")
    private String cateName;

    @ApiModelProperty(value = "图片标题")
    private String imageTitle;

    @ApiModelProperty(value = "背景图地址")
    private String imageUrl;

    @ApiModelProperty(value = "图片链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "阅读数")
    private Integer viewCount;

    @ApiModelProperty(value = "评论数")
    private Integer commentCount;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
