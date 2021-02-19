package com.mark.base.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/5 12:25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "评论邮件实体")
public class CommentMailRpcVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论人邮箱")
    @NotBlank(message = "评论人邮箱不能为空")
    @Email(message = "邮箱不合法")
    private String email;

    @ApiModelProperty("回复人昵称")
    @NotBlank(message = "回复人昵称不能为空")
    private String replayName;

    @ApiModelProperty("回复内容")
    @NotBlank(message = "回复内容不能为空")
    private String content;

    @ApiModelProperty("文章名称")
    @NotBlank(message = "文章名称不能为空")
    private String articleName;

    @ApiModelProperty("评论类型，0：一级评论")
    @NotBlank(message = "评论类型不能为空")
    private String parentId;
}
