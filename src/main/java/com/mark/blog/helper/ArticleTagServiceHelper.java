package com.mark.blog.helper;

import com.mark.blog.entity.ArticleTag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/23 13:12
 */
@Component
public class ArticleTagServiceHelper {

    /**
     * 使用流
     * 将标签id集合转换为文章标签对象集合
     * @param tagIds 标签id集合
     * @return List<ArticleTag>
     */
    public List<ArticleTag> tagIdsConversionTags(List<String> tagIds, String articleId) {
        return tagIds.stream().map(tagId -> {
            // 构建文章标签对象
            ArticleTag articleTag = new ArticleTag();
            // 设置文章id
            articleTag.setArticleId(articleId);
            // 设置标签id
            articleTag.setTagId(tagId);
            return articleTag;
        }).collect(Collectors.toList());
    }
}
