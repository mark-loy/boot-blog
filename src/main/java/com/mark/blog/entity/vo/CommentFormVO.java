package com.mark.blog.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/24 13:44
 */
@Data
@ApiModel(value = "评论表单对象")
public class CommentFormVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    private String id;

    @ApiModelProperty(value = "文章id")
    private String articleId;

    @ApiModelProperty(value = "评论父级id；0：一级评论，其它值(一级评论id)：二级评论")
    private String parentId;

    @ApiModelProperty(value = "评论人id")
    private String visitorId;

    @ApiModelProperty(value = "评论人昵称")
    private String visitorName;

    @ApiModelProperty(value = "评论人头像")
    private String visitorAvatar;

    @ApiModelProperty(value = "评论内容")
    private String comment;
}
