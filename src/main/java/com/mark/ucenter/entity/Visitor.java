package com.mark.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 访客表
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("ucenter_visitor")
@ApiModel(value="Visitor对象", description="访客表")
public class Visitor implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "访客id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "账户id，第三方登录的id")
    private String accountId;

    @ApiModelProperty(value = "邮箱地址")
    private String email;

    @ApiModelProperty(value = "密码，使用MD5加密")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "访客状态；0.表示未禁止，1.表示已禁止")
    private Boolean isDisabled;

    @ApiModelProperty(value = "逻辑删除;0.表示未删除，1.表示已删除")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
