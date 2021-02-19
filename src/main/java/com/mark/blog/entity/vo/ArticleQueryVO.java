package com.mark.blog.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/22 16:42
 */
@Data
@ApiModel(value = "文章查询条件对象")
public class ArticleQueryVO implements Serializable {
    private static  final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章发布状态;0.表示未发布，1.表示已发布")
    private Boolean isReleased;

    @ApiModelProperty(value = "文章发布起始时间", example = "2021-01-22 16:46:21")
    private String begin;

    @ApiModelProperty(value = "文章发布结束时间", example = "2021-01-22 16:46:21")
    private String end;
}
