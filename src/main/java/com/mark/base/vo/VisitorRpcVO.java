package com.mark.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/5 11:11
 */
@Data
@ApiModel("访客RPC实体")
public class VisitorRpcVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("访客id")
    private String id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;
}
