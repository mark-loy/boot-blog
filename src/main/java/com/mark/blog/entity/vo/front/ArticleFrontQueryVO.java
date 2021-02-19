package com.mark.blog.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/30 14:07
 */
@Data
@ApiModel("前台文章查询对象")
public class ArticleFrontQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "分类id")
    private String cateId;

    @ApiModelProperty(value = "标签id")
    private String tagId;
}
