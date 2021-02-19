package com.mark.ucenter.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/31 16:01
 */
@Data
@ApiModel(value = "访客查询对象")
public class VisitorQueryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("访客状态；0.未禁用，1.已禁用")
    private Boolean isDisabled;

}
