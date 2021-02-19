package com.mark.blog.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/8 10:17
 */
@Data
@ApiModel(value = "前台分类实体")
public class CategoryResultVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分类id")
    private String id;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("文章数")
    private Integer articleCount;
}
