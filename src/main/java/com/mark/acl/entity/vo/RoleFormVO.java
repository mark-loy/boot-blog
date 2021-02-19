package com.mark.acl.entity.vo;

import com.mark.base.valid.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/9 19:18
 */
@Data
@ApiModel(value = "角色表单实体")
public class RoleFormVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @NotBlank(message = "角色id不能为空", groups = {Group.Update.class})
    private String id;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空", groups = {Group.Add.class})
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    private String roleCode;

    @ApiModelProperty(value = "备注")
    private String remark;
}
