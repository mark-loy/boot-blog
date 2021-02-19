package com.mark.blog.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/29 14:03
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "焦点图对象")
public class FocusMapVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章id")
    private String id;

    @ApiModelProperty(value = "焦点图标题")
    private String imageTitle;

    @ApiModelProperty(value = "焦点图url")
    private String imageUrl;
}
