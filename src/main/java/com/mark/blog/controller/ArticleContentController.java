package com.mark.blog.controller;


import com.mark.blog.entity.ArticleContent;
import com.mark.blog.service.ArticleContentService;
import com.mark.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 文章表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Api(value = "文章内容管理", tags = {"文章内容管理服务接口"})
@RestController
@RequestMapping("api/blog/article/content")
public class ArticleContentController {


    @Resource
    private ArticleContentService contentService;

    /**
     * 根据id获取文章内容
     * @param articleId 文章id
     * @return Result
     */
    @ApiOperation("根据id获取文章内容")
    @GetMapping("/{aid}")
    public Result getArticleContent(@ApiParam("文章id") @PathVariable("aid") String articleId) {
        ArticleContent content = contentService.getById(articleId);
        return Result.ok().data("content", content);
    }

    /**
     * 修改文章内容
     * @param articleContent 文章内容对象
     * @return Result
     */
    @ApiOperation("修改文章内容")
    @PutMapping("/")
    public Result updateArticleContent(@ApiParam("文章内容对象") @RequestBody ArticleContent articleContent) {
        // 执行修改
        contentService.updateById(articleContent);
        return Result.ok();
    }
}

