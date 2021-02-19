package com.mark.blog.entity.vo.front;

import com.mark.blog.entity.vo.CommentResponseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/4 21:01
 */
@Data
@ApiModel(value = "前台评论结果对象")
public class CommentResultVO implements Serializable {
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

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "二级评论集合")
    private List<CommentResultVO> children;
}
