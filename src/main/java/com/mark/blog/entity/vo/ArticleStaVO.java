package com.mark.blog.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/24 21:40
 */
@Data
public class ArticleStaVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    private Integer publishCount;
}
