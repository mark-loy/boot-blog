package com.mark.blog.entity.vo.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/4 21:17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "保存评论实体")
public class CommentFrontFormVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章id")
    @NotBlank(message = "文章id不能为空")
    private String articleId;

    @ApiModelProperty(value = "评论父级id；0：一级评论，其它值(一级评论id)：二级评论")
    @NotBlank(message = "评论父级id不能为空")
    private String parentId;

    @ApiModelProperty(value = "接收人id")
    @NotBlank(message = "接收人id不能为空")
    private String acceptId;

    @ApiModelProperty(value = "评论内容")
    @NotBlank(message = "评论内容不能为空")
    private String content;
}
