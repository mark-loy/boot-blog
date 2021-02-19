package com.mark.blog.helper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.base.vo.UserRpcVO;
import com.mark.blog.client.AclClient;
import com.mark.blog.entity.*;
import com.mark.blog.entity.vo.ArticleResponseVO;
import com.mark.blog.entity.vo.front.ArticleResultVO;
import com.mark.blog.service.*;
import com.mark.blog.util.BlogConstantUtil;
import com.mark.common.entity.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/23 12:34
 */
@Component
public class ArticleServiceHelper {

    @Resource
    private CategoryService categoryService;

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleContentService articleContentService;

    @Resource
    private AclClient aclClient;

    /**
     * 获取文章数据的返回结果
     * @param article 文章对象
     * @return ArticleResponseVO
     */
    public ArticleResponseVO getArticleResponse(Article article) {

        // 构建返回的文章对象
        ArticleResponseVO articleResponse = new ArticleResponseVO();
        // 设置文章属性
        BeanUtils.copyProperties(article, articleResponse);

        // 根据分类id查询分类对象
        Category category = categoryService.getById(article.getCategoryId());
        if (category == null) {
            // 不存在该分类，则将分类id置空
            articleResponse.setCategoryId("");
        } else {
            // 设置分类名称
            articleResponse.setCategoryName(category.getName());
        }

        // 在文章标签中间表中，根据文章id查询标签id
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        articleTagWrapper.eq("article_id", article.getId());
        List<ArticleTag> articleTags = articleTagService.list(articleTagWrapper);
        if (articleTags.size() != 0) {
            // 从articleTags集合中取出标签id集合
            List<String> tagsId = articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());
            // 获取标签集合
            List<Tag> tags = (List<Tag>)tagService.listByIds(tagsId);
            // 从tags中取出标签名称集合
            List<String> tagsName = tags.stream().map(Tag::getName).collect(Collectors.toList());
            // 设置标签名称集合
            articleResponse.setTagsName(tagsName);
            articleResponse.setTagIds(tagsId);
        } else {
            articleResponse.setTagsName(new ArrayList<>());
            articleResponse.setTagIds(new ArrayList<>());
        }

        // 远程调用acl服务，获取用户信息
        UserRpcVO userRpcVO = aclClient.selectUserById(article.getUserId());
        if (userRpcVO == null) {
            // 不存在该用户，则将用户id置空
            articleResponse.setUserId("");
        } else {
            // 设置用户名称
            articleResponse.setUserName(userRpcVO.getNickname());
        }

        // 获取文章内容
        ArticleContent articleContent = articleContentService.getById(article.getId());
        if (articleContent != null) {
            // 设置文章内容
            articleResponse.setContent(articleContent.getContent());

        }

        return articleResponse;
    }

    /**
     * 获取文章返回集合
     * @param articles 文章实体集合
     * @return List<ArticleResultVO>
     */
    public List<ArticleResultVO> getArticlesResult(List<Article> articles) {
        List<ArticleResultVO> articleList = new ArrayList<>();

        for (Article article : articles) {
            // 将返回数据加入集合
            articleList.add(getArticleResult(article));
        }
        return articleList;
    }

    /**
     * 获取文章返回数据
     * @param article 文章实体
     * @return ArticleResultVO
     */
    public ArticleResultVO getArticleResult(Article article) {
        // 构建返回数据
        ArticleResultVO articleResult = new ArticleResultVO();
        BeanUtils.copyProperties(article, articleResult);

        // 查询分类
        Category category = categoryService.getById(article.getCategoryId());
        if (category != null) {
            articleResult.setCateId(category.getId()).setCateName(category.getName());
        }

        // 判断是否为热度文章
        // 设置为热度文章
        articleResult.setIsHot(article.getViewCount() >= BlogConstantUtil.HOT_CONDITION_VAL);

        // 查询文章内容
        ArticleContent content = articleContentService.getById(article.getId());
        articleResult.setContent(content.getContent());

        return articleResult;
    }
}
