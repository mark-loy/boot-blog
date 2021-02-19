package com.mark.blog.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/8 13:34
 */
@Data
@ApiModel("前台标签实体")
public class TagResultVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签id")
    private String id;

    @ApiModelProperty("标签名称")
    private String name;

    @ApiModelProperty("文章数")
    private Integer articleCount;
}
