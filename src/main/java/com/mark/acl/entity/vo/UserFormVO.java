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
 * @date 2021/2/9 19:41
 */
@Data
@ApiModel(value = "用户表单实体")
public class UserFormVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空", groups = {Group.Update.class})
    private String id;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空", groups = {Group.Add.class})
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空", groups = {Group.Add.class})
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户签名")
    private String sign;
}
