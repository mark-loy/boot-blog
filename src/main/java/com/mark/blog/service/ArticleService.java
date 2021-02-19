package com.mark.blog.service;

import com.mark.blog.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mark.blog.entity.vo.ArticleQueryVO;
import com.mark.blog.entity.vo.ArticleFormVO;
import com.mark.blog.entity.vo.ArticleResponseVO;
import com.mark.blog.entity.vo.front.ArticleFrontQueryVO;
import com.mark.blog.entity.vo.front.FocusMapVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
public interface ArticleService extends IService<Article> {

    /**
     * 保存文章数据
     * @param articleFormVO 文章表单对象
     * @return String
     */
    String saveArticle(ArticleFormVO articleFormVO);

    /**
     * 多组合条件分页查询文章数据
     * @param current 当前页
     * @param limit 当页显示数
     * @param articleQuery 查询条件对象
     * @return Map<String, Object>
     */
    Map<String, Object> getArticlePage(Long current, Long limit, ArticleQueryVO articleQuery);

    /**
     * 根据文章id查询文章数据，并返回包装结果
     * @param articleId 文章id
     * @return ArticleResponseVO
     */
    ArticleResponseVO getArticleById(String articleId);

    /**
     * 修改文章数据
     * @param articleFormVO 文章表单对象
     */
    void updateArticle(ArticleFormVO articleFormVO);

    /**
     * 删除文章数据
     * @param articleId 文章id
     */
    void deleteArticle(String articleId);

    /**
     * 批量删除文章数据
     * @param articlesId 文章id集合
     */
    void deleteBatchArticle(List<String> articlesId);

    /**
     * 生成统计数据
     * @param date 日期
     * @return Map<String, Object>
     */
    Map<String, Object> generateStaData(String date);

    /**
     * 修改文章的发布状态
     * @param articleId 文章id
     * @param isReleased 是否发布
     */
    void updateArticleStatus(String articleId, Boolean isReleased);

    /**
     * 保存并发布文章数据
     * @param articleFormVO 文章表单对象
     */
    void saveArticlePublish(ArticleFormVO articleFormVO);

    /**
     * 修改并发布文章数据
     * @param articleFormVO 文章表单对象
     */
    void updateArticlePublish(ArticleFormVO articleFormVO);

    /**
     * 首页查询文章列表
     * @param page 当前页
     * @param size 当页显示数
     * @param articleFrontQueryVO 前台文章查询对象
     * @return Map<String, Object>
     */
    Map<String, Object> getFrontArticles(Long page, Long size, ArticleFrontQueryVO articleFrontQueryVO);

    /**
     * 修改文章置顶状态
     * @param articleId 文章id
     * @param isTop 是否置顶
     */
    void updateArticleTopStatus(String articleId, Boolean isTop);

    /**
     * 获取焦点图数据
     * @return List<FocusMapVO>
     */
    List<FocusMapVO> getFocusMap();

}
