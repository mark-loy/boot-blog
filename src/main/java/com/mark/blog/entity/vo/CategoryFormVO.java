package com.mark.blog.entity.vo;

import com.mark.base.valid.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/23 15:43
 */
@Data
@ApiModel(value = "分类表单对象")
public class CategoryFormVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类id")
    @NotBlank(message = "分类id不能为空", groups = {Group.Update.class})
    private String id;

    @ApiModelProperty(value = "分类名")
    @NotBlank(message = "分类名不能为空", groups = {Group.Add.class, Group.Update.class})
    private String name;

}
