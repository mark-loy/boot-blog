package com.mark.blog.entity.vo;

import com.mark.base.valid.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/22 17:16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "文章对象", description = "文章表单对象")
public class ArticleFormVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文章ID")
    @NotBlank(message = "文章ID不能为空", groups = {Group.Update.class})
    private String id;

    @ApiModelProperty(value = "文章标题")
    @NotBlank(message = "文章标题不能为空", groups = {Group.Add.class})
    private String title;

    @ApiModelProperty(value = "文章概要")
    private String summary;

    @ApiModelProperty(value = "发表人id")
    @NotBlank(message = "发表人不能为空", groups = {Group.Add.class})
    private String userId;

    @ApiModelProperty(value = "分类id")
    @NotBlank(message = "分类不能为空", groups = {Group.Add.class})
    private String categoryId;

    @ApiModelProperty(value = "图片标题")
    private String imageTitle;

    @ApiModelProperty(value = "背景图地址")
    private String imageUrl;

    @ApiModelProperty(value = "图片链接地址")
    private String linkUrl;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "标签id")
    @NotEmpty(message = "请选择至少一个标签", groups = {Group.Add.class})
    private List<String> tagIds;
}
