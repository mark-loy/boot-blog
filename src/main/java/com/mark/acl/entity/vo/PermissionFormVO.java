package com.mark.acl.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.mark.base.valid.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/9 15:28
 */
@Data
@ApiModel(value = "菜单表单实体")
public class PermissionFormVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @NotBlank(message = "菜单编号不能为空", groups = {Group.Update.class})
    private String id;

    @ApiModelProperty(value = "所属上级")
    @NotBlank(message = "上级菜单不能为空", groups = {Group.Add.class})
    private String pid;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "菜单名称不能为空", groups = {Group.Add.class})
    private String name;

    @ApiModelProperty(value = "类型(1:菜单,2:按钮)")
    private Integer type;

    @ApiModelProperty(value = "权限值")
    private String permissionValue;

    @ApiModelProperty(value = "访问路径")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;
}
