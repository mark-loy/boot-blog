package com.mark.blog.entity;

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
 * 评论表
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("blog_comment")
@ApiModel(value="Comment对象", description="评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
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

    @ApiModelProperty(value = "接收人id")
    private String acceptId;

    @ApiModelProperty(value = "接收人昵称")
    private String acceptName;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论状态；0.表示未禁用，1.表示已禁用")
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
