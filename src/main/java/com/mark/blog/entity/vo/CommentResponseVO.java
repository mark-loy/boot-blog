package com.mark.blog.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/23 20:55
 */
@Data
@ApiModel(value = "后台评论结果对象")
public class CommentResponseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    private String id;

    @ApiModelProperty(value = "评论人id")
    private String visitorId;

    @ApiModelProperty(value = "评论人昵称")
    private String visitorName;

    @ApiModelProperty(value = "评论人头像")
    private String visitorAvatar;

    @ApiModelProperty(value = "接收人id")
    private String acceptId;

    @ApiModelProperty(value = "接收人昵称")
    private String acceptName;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论状态；0.表示已禁止，1.表示未禁止")
    private Boolean isDisabled;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "二级评论集合")
    private List<CommentResponseVO> children;

}
