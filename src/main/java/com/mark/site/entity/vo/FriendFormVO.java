package com.mark.site.entity.vo;

import com.mark.base.valid.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/5 21:09
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "友站实体")
public class FriendFormVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("友站id")
    @NotBlank(message = "友站id不能为空", groups = {Group.Update.class})
    private String id;

    @ApiModelProperty("友站名称")
    @NotBlank(message = "友站名称不能为空", groups = {Group.Add.class})
    private String name;

    @ApiModelProperty("友站链接")
    @NotBlank(message = "友站链接不能为空", groups = {Group.Add.class})
    private String friendUrl;

    @ApiModelProperty("友站图片链接")
    @NotBlank(message = "友站图片链接不能为空", groups = {Group.Add.class})
    private String imageUrl;

    @ApiModelProperty("友站简介")
    @NotBlank(message = "友站简介不能为空", groups = {Group.Add.class})
    private String friendSummary;
}
