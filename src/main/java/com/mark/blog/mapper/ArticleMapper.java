package com.mark.blog.mapper;

import com.mark.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mark.blog.entity.vo.ArticleStaVO;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 文章表 Mapper 接口
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 统计文章的浏览数、评论数、点赞数
     * @param date 日期
     * @return ArticleStaVO
     */
    ArticleStaVO generateStaData(String date);

}
