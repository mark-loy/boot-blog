package com.mark.blog.controller;


import com.mark.base.valid.Group;
import com.mark.blog.entity.vo.ArticleFormVO;
import com.mark.blog.entity.vo.ArticleQueryVO;
import com.mark.blog.entity.vo.ArticleResponseVO;
import com.mark.blog.service.ArticleService;
import com.mark.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Api(value = "文章管理", tags = {"文章管理服务接口"})
@RestController
@RequestMapping("api/blog/article")
public class ArticleController {


    @Resource
    private ArticleService articleService;

    /**
     * 多组合条件分页查询文章
     * @param current 当前页
     * @param limit 当页显示数
     * @param articleQuery 文章条件对象
     * @return Result
     */
    @ApiOperation("多组合条件查询文章")
    @PostMapping("/{current}/{limit}")
    public Result getArticlePage(@ApiParam("当前页") @PathVariable("current") Long current,
                                 @ApiParam("当页显示数") @PathVariable("limit") Long limit,
                                 @ApiParam("文章条件对象") @RequestBody(required = false) ArticleQueryVO articleQuery) {
        // 分页参数校验
        if (current < 1 || limit < 1) {
            return Result.error().message("分页参数错误");
        }
        // 获取文章分页数据
        Map<String, Object> resultMap = articleService.getArticlePage(current, limit, articleQuery);
        return Result.ok().data(resultMap);
    }

    /**
     * 保存文章数据
     * @param articleFormVO 文章表单对象
     * @return Result
     */
    @ApiOperation("保存文章数据")
    @PostMapping("/")
    public Result saveArticle(@ApiParam("文章表单对象") @Validated({Group.Add.class}) @RequestBody ArticleFormVO articleFormVO) {
        articleService.saveArticle(articleFormVO);
        return Result.ok();
    }

    /**
     * 保存并发布文章数据
     * @param articleFormVO 文章表单对象
     * @return Result
     */
    @ApiOperation("保存并发布文章数据")
    @PostMapping("/pub")
    public Result saveArticlePublish(@ApiParam("文章表单对象") @Validated({Group.Add.class}) @RequestBody ArticleFormVO articleFormVO) {
        articleService.saveArticlePublish(articleFormVO);
        return Result.ok();
    }

    /**
     * 根据文章id查询文章信息
     * @param articleId 文章id
     * @return Result
     */
    @ApiOperation("根据id查询文章信息")
    @GetMapping("/{aid}")
    public Result getArticleById(@ApiParam("文章id") @PathVariable("aid") String articleId) {
        ArticleResponseVO articleResponseVO = articleService.getArticleById(articleId);
        return Result.ok().data("article", articleResponseVO);
    }

    /**
     * 修改文章信息
     * @param articleFormVO 文章表单对象
     * @return Result
     */
    @ApiOperation("修改文章信息")
    @PutMapping("/")
    public Result updateArticle(@ApiParam("文章表单对象") @Validated({Group.Update.class}) @RequestBody ArticleFormVO articleFormVO) {
        articleService.updateArticle(articleFormVO);
        return Result.ok();
    }

    /**
     * 修改并发布文章信息
     * @param articleFormVO 文章表单对象
     * @return Result
     */
    @ApiOperation("修改并发布文章信息")
    @PutMapping("/pub")
    public Result updateArticlePublish(@ApiParam("文章表单对象") @Validated({Group.Update.class}) @RequestBody ArticleFormVO articleFormVO) {
        articleService.updateArticlePublish(articleFormVO);
        return Result.ok();
    }

    /**
     * 根据id删除文章
     * @param articleId 文章id
     * @return Result
     */
    @ApiOperation("根据id删除文章")
    @DeleteMapping("/{aid}")
    public Result deleteArticle(@ApiParam("文章id") @PathVariable("aid") String articleId) {
        articleService.deleteArticle(articleId);
        return Result.ok();
    }

    /**
     * 批量删除文章
     * @param articlesId 文章id集合
     * @return Result
     */
    @ApiOperation("批量删除文章")
    @DeleteMapping("/")
    public Result deleteBatchArticle(@ApiParam("文章id集合") @RequestParam("aids") List<String> articlesId) {
        articleService.deleteBatchArticle(articlesId);
        return Result.ok();
    }

    /**
     * 修改文章的发布状态
     * @param articleId 文章id
     * @param isReleased 是否发布
     * @return Result
     */
    @ApiOperation("修改文章的发布状态")
    @PutMapping("/{aid}/{status}")
    public Result updateArticleStatus(@ApiParam("文章id") @PathVariable("aid") String articleId,
                                      @ApiParam("是否发布") @PathVariable("status") Boolean isReleased) {
        articleService.updateArticleStatus(articleId, isReleased);
        return Result.ok();
    }

    /**
     * 修改文章的置顶状态
     * @param articleId 文章id
     * @param isTop 是否置顶
     * @return Result
     */
    @ApiOperation("修改文章的发布状态")
    @PutMapping("/top/{aid}/{status}")
    public Result updateArticleTopStatus(@ApiParam("文章id") @PathVariable("aid") String articleId,
                                      @ApiParam("是否置顶") @PathVariable("status") Boolean isTop) {
        articleService.updateArticleTopStatus(articleId, isTop);
        return Result.ok();
    }
}

