package com.mark.blog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.base.enums.CustomExceptionEnum;
import com.mark.base.exception.CustomException;
import com.mark.blog.entity.Article;
import com.mark.blog.entity.ArticleContent;
import com.mark.blog.entity.ArticleTag;
import com.mark.blog.entity.Comment;
import com.mark.blog.entity.vo.ArticleFormVO;
import com.mark.blog.entity.vo.ArticleQueryVO;
import com.mark.blog.entity.vo.ArticleResponseVO;
import com.mark.blog.entity.vo.ArticleStaVO;
import com.mark.blog.entity.vo.front.ArticleFrontQueryVO;
import com.mark.blog.entity.vo.front.ArticleResultVO;
import com.mark.blog.entity.vo.front.FocusMapVO;
import com.mark.blog.helper.ArticleServiceHelper;
import com.mark.blog.helper.ArticleTagServiceHelper;
import com.mark.blog.mapper.ArticleMapper;
import com.mark.blog.mapper.CommentMapper;
import com.mark.blog.service.ArticleContentService;
import com.mark.blog.service.ArticleService;
import com.mark.blog.service.ArticleTagService;
import com.mark.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private ArticleContentService articleContentService;

    @Resource
    private ArticleServiceHelper articleServiceHelper;

    @Resource
    private ArticleTagServiceHelper articleTagServiceHelper;

    @Resource
    private CommentMapper commentMapper;

    /**
     * 多组合条件分页查询文章数据
     *
     * @param current      当前页
     * @param limit        当页显示数
     * @param articleQuery 查询条件对象
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getArticlePage(Long current, Long limit, ArticleQueryVO articleQuery) {

        // 构建查询条件对象
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        articleWrapper.orderByDesc("gmt_create");
        // 查询条件非空校验
        if (!StringUtils.isEmpty(articleQuery.getTitle())) {
            articleWrapper.like("title", articleQuery.getTitle());
        }
        if (!StringUtils.isEmpty(articleQuery.getIsReleased())) {
            articleWrapper.eq("is_released", articleQuery.getIsReleased());
        }
        if (articleQuery.getBegin() != null) {
            articleWrapper.ge("gmt_create", articleQuery.getBegin());
        }
        if (articleQuery.getEnd() != null) {
            articleWrapper.le("gmt_create", articleQuery.getEnd());
        }
        // 构建分页对象
        Page<Article> articlePage = new Page<>(current, limit);
        // 执行分页
        baseMapper.selectPage(articlePage, articleWrapper);
        // 获取文章分页数据
        List<Article> articles = articlePage.getRecords();
        // 获取总记录数
        long total = articlePage.getTotal();

        // 构建最终返回的文章集合
        List<ArticleResponseVO> articleResult = new ArrayList<>();

        // 遍历文章数据
        for (Article article : articles) {
            // 将返回的文章对象加入集合
            articleResult.add(articleServiceHelper.getArticleResponse(article));
        }

        // 最终结果封装
        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("articles", articleResult);
        resultMap.put("total", total);
        return resultMap;
    }

    /**
     * 保存文章数据及关联数据
     *
     * @param articleFormVO 文章表单对象
     */
    @Override
    public String saveArticle(ArticleFormVO articleFormVO) {
        // 保存文章数据
        Article article = new Article();
        // 设置文章数据
        BeanUtils.copyProperties(articleFormVO, article);
        // 默认文章未置顶
        article.setIsTop(false);
        // 默认文章未发布
        article.setIsReleased(false);
        // 执行保存
        baseMapper.insert(article);

        // 保存文章内容
        ArticleContent articleContent = new ArticleContent();
        // 设置文章id
        articleContent.setId(article.getId());
        if (!StringUtils.isEmpty(articleFormVO.getContent())) {
            articleContent.setContent(articleFormVO.getContent());
        } else {
            // 设置文章内容为空
            articleContent.setContent("");
        }

        // 执行保存
        articleContentService.save(articleContent);

        // 保存文章标签中间表数据
        // 获取标签集合id
        List<String> tagIds = articleFormVO.getTagIds();
        // 将标签id集合转换为文章标签集合
        List<ArticleTag> articleTags = articleTagServiceHelper.tagIdsConversionTags(tagIds, article.getId());
        // 执行保存
        articleTagService.saveBatch(articleTags);

        return article.getId();
    }

    /**
     * 保存并发布文章数据
     *
     * @param articleFormVO 文章表单对象
     */
    @Override
    public void saveArticlePublish(ArticleFormVO articleFormVO) {
        // 保存文章数据
        String articleId = saveArticle(articleFormVO);

        // 修改文章发布状态
        Article article = new Article();
        // 设置文章id
        article.setId(articleId);
        // 设置文章为发布状态
        article.setIsReleased(true);
        // 执行修改
        baseMapper.updateById(article);
    }

    /**
     * 根据文章id查询文章数据，并返回包装结果
     *
     * @param articleId 文章id
     * @return ArticleResponseVO
     */
    @Override
    public ArticleResponseVO getArticleById(String articleId) {
        // 获取文章数据
        Article article = baseMapper.selectById(articleId);
        // 返回包装的文章数据
        return articleServiceHelper.getArticleResponse(article);
    }

    /**
     * 修改文章数据及关联数据
     *
     * @param articleFormVO 文章表单对象
     */
    @Override
    public void updateArticle(ArticleFormVO articleFormVO) {
        // 修改文章数据
        Article article = new Article();
        // 设置文章属性数据
        BeanUtils.copyProperties(articleFormVO, article);
        // 执行文章数据修改
        baseMapper.updateById(article);

        // 修改文章内容数据
        ArticleContent articleContent = new ArticleContent();
        articleContent.setId(articleFormVO.getId());
        articleContent.setContent(articleFormVO.getContent());
        articleContentService.updateById(articleContent);

        // 修改文章关联的标签数据
        //   1. 先删除中间表中该文章与标签的关联数据
        QueryWrapper<ArticleTag> articleTagQueryWrapper = new QueryWrapper<>();
        //      设置文章id
        articleTagQueryWrapper.eq("article_id", article.getId());
        //      执行删除
        articleTagService.remove(articleTagQueryWrapper);
        //   2. 再添加本次修改的标签数据
        //      转换标签id集合为文章标签集合
        List<ArticleTag> articleTags = articleTagServiceHelper.tagIdsConversionTags(articleFormVO.getTagIds(), article.getId());
        //      执行保存
        articleTagService.saveBatch(articleTags);
    }

    /**
     * 修改并发布文章
     *
     * @param articleFormVO 文章表单对象
     */
    @Override
    public void updateArticlePublish(ArticleFormVO articleFormVO) {
        // 修改文章数据
        updateArticle(articleFormVO);
        // 修改文章发布状态
        Article article = new Article();
        // 设置文章id
        article.setId(articleFormVO.getId());
        // 设置文章发布状态
        article.setIsReleased(true);
        // 执行修改
        baseMapper.updateById(article);
    }

    /**
     * 删除文章数据及关联数据
     *
     * @param articleId 文章id
     */
    @Override
    public void deleteArticle(String articleId) {
        // 删除文章内容
        articleContentService.removeById(articleId);

        // 删除文章标签中间表的关联数据
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        articleTagWrapper.eq("article_id", articleId);
        articleTagService.remove(articleTagWrapper);

        // 删除文章的评论数据
        QueryWrapper<Comment> commentQuery = new QueryWrapper<>();
        commentQuery.eq("article_id", articleId);
        commentQuery.select("id");
        List<Comment> comments = commentMapper.selectList(commentQuery);
        List<String> commentIds = comments.stream().map(Comment::getId).collect(Collectors.toList());
        commentMapper.deleteBatchIds(commentIds);

        // 删除文章数据
        baseMapper.deleteById(articleId);
    }

    /**
     * 批量删除文章数据及关联数据
     *
     * @param articlesId 文章id集合
     */
    @Override
    public void deleteBatchArticle(List<String> articlesId) {
        for (String articleId : articlesId) {
            this.deleteArticle(articleId);
        }
    }

    /**
     * 生成统计数据
     *
     * @param date 日期
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> generateStaData(String date) {
        // 获取当前文章的总浏览数、评论数、点赞数
        ArticleStaVO currentCount = baseMapper.generateStaData(date);
        // 获取昨前一天文章的总浏览数、评论数、点赞数
        // 获取前一天的日期
        DateTime yesterday = DateUtil.offsetDay(DateUtil.parseDate(date), -1);
        ArticleStaVO yesterdayCount = baseMapper.generateStaData(yesterday.toString("yyyy-MM-dd"));

        // 获取当天的发表文章数量
        Integer publishCount = currentCount.getPublishCount();

        // 判断前一天的统计数据是否为null
        if (yesterdayCount.getViewCount() == null) {
            yesterdayCount.setViewCount(0);
        }
        if (yesterdayCount.getCommentCount() == null) {
            yesterdayCount.setCommentCount(0);
        }
        if (yesterdayCount.getLikeCount() == null) {
            yesterdayCount.setLikeCount(0);
        }
        // 拿当天的减去前一天的浏览数、评论数、点赞数
        Integer viewCount = currentCount.getViewCount() - yesterdayCount.getViewCount();
        Integer commentCount = currentCount.getCommentCount() - yesterdayCount.getCommentCount();
        Integer likeCount = currentCount.getLikeCount() - yesterdayCount.getLikeCount();

        Map<String, Object> map = new HashMap<>(3);
        map.put("viewNum", viewCount);
        map.put("commentNum", commentCount);
        map.put("likeNum", likeCount);
        map.put("publishNum", publishCount);
        return map;
    }

    /**
     * 修改文章的发布状态
     *
     * @param articleId  文章id
     * @param isReleased 是否发布
     */
    @Override
    public void updateArticleStatus(String articleId, Boolean isReleased) {
        // 查询文章数据
        Article article = baseMapper.selectById(articleId);
        if (article == null) {
            throw new CustomException(CustomExceptionEnum.NO_ARTICLE);
        }
        // 设置文章的发布状态
        article.setIsReleased(isReleased);
        // 执行修改
        baseMapper.updateById(article);
    }

    /**
     * 首页查询文章数据列表
     *
     * @param page                当前页
     * @param size                当页显示数
     * @param articleFrontQueryVO 前台文章查询对象
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getFrontArticles(Long page, Long size, ArticleFrontQueryVO articleFrontQueryVO) {
        // 构建分页对象
        Page<Article> articlePage = new Page<>(page, size);
        // 构建查询对象
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        // 排序规则：文章先进行置顶排序，再进行时间发布排序
        articleWrapper.orderByDesc("is_top", "view_count", "gmt_create");
        // 设置文章为发布状态
        articleWrapper.eq("is_released", 1);

        if (!StringUtils.isEmpty(articleFrontQueryVO.getTitle())) {
            articleWrapper.like("title", articleFrontQueryVO.getTitle());
        }
        if (!StringUtils.isEmpty(articleFrontQueryVO.getCateId())) {
            articleWrapper.eq("category_id", articleFrontQueryVO.getCateId());
        }
        if (!StringUtils.isEmpty(articleFrontQueryVO.getTagId())) {
            // 查询中间表
            QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
            articleTagWrapper.eq("tag_id", articleFrontQueryVO.getTagId());
            List<ArticleTag> tagList = articleTagService.list(articleTagWrapper);
            List<String> articleId = tagList.stream().map(ArticleTag::getArticleId).collect(Collectors.toList());

            articleWrapper.in("id", articleId);
        }

        // 执行分页
        baseMapper.selectPage(articlePage, articleWrapper);

        // 获取分页结果
        List<Article> articles = articlePage.getRecords();
        long current = articlePage.getCurrent();
        boolean hasNext = articlePage.hasNext();

        // 将文章实体转为文章返回数据
        List<ArticleResultVO> articleList = articleServiceHelper.getArticlesResult(articles);

        // 封装返回结果
        Map<String, Object> map = new HashMap<>(2);
        map.put("current", current);
        map.put("articles", articleList);
        map.put("hasNext", hasNext);
        return map;
    }

    /**
     * 修改文章置顶状态
     *
     * @param articleId 文章id
     * @param isTop     是否置顶
     */
    @Override
    public void updateArticleTopStatus(String articleId, Boolean isTop) {
        // 查询文章数据
        Article article = baseMapper.selectById(articleId);
        if (article == null) {
            throw new CustomException(CustomExceptionEnum.NO_ARTICLE);
        }
        // 设置文章的发布状态
        article.setIsTop(isTop);
        // 执行修改
        baseMapper.updateById(article);
    }

    /**
     * 获取文章焦点图数据
     *
     * @return List<FocusMapVO>
     */
    @Override
    public List<FocusMapVO> getFocusMap() {
        // 构建条件查询对象
        QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
        articleWrapper.orderByDesc("is_top", "view_count", "gmt_create");
        // 查询前三条焦点图数据
        articleWrapper.last("limit 3");
        articleWrapper.select("id", "image_title", "image_url");
        List<Article> articles = baseMapper.selectList(articleWrapper);

        // 构建返回集合
        return articles.stream().map(article -> {
            FocusMapVO focusMapVO = new FocusMapVO();
            focusMapVO.setId(article.getId()).setImageTitle(article.getImageTitle()).setImageUrl(article.getImageUrl());
            return focusMapVO;
        }).collect(Collectors.toList());
    }
}
