package com.mark.blog.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.blog.entity.Article;
import com.mark.blog.entity.ArticleTag;
import com.mark.blog.entity.Category;
import com.mark.blog.entity.Tag;
import com.mark.blog.entity.vo.front.ArticleFrontQueryVO;
import com.mark.blog.entity.vo.front.CategoryResultVO;
import com.mark.blog.entity.vo.front.FocusMapVO;
import com.mark.blog.entity.vo.front.TagResultVO;
import com.mark.blog.service.ArticleService;
import com.mark.blog.service.ArticleTagService;
import com.mark.blog.service.CategoryService;
import com.mark.blog.service.TagService;
import com.mark.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/28 19:49
 */
@Api(value = "首页管理", tags = {"首页接口管理"})
@RestController
@RequestMapping("api/blog/home")
public class HomePageController {

    @Resource
    private ArticleService articleService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private TagService tagService;

    @Resource
    private ArticleTagService articleTagService;

    /**
     * 获取文章列表数据
     * @param page 当前页
     * @param size 当页显示数
     * @param articleFrontQueryVO 文章查询对象
     * @return Result
     */
    @ApiOperation("获取文章列表数据")
    @PostMapping("/articles/{page}/{size}")
    public Result getArticleList(@ApiParam("当前页") @PathVariable("page") Long page,
                                 @ApiParam("当前显示数") @PathVariable("size") Long size,
                                 @ApiParam("文章查询对象") @RequestBody(required = false) ArticleFrontQueryVO articleFrontQueryVO
                                 ) {
        if (page < 1 || size < 1) {
            return  Result.error().message("分页参数不正确");
        }
        Map<String, Object> map = articleService.getFrontArticles(page, size, articleFrontQueryVO);
        return Result.ok().data(map);
    }

    /**
     * 获取焦点图列表数据
     * @return Result
     */
    @ApiOperation("获取焦点图列表数据")
    @GetMapping("/focus")
    public Result getFocusMap() {
        List<FocusMapVO> focusMaps = articleService.getFocusMap();
        return Result.ok().data("features", focusMaps);
    }

    /**
     * 获取所有分类数据
     * @return Result
     */
    @ApiOperation("获取所有分类数据")
    @GetMapping("/all/category")
    public Result getAllCategory() {
        // 查询所有分类
        List<Category> categories = categoryService.list(null);
        // 全部分类文章数
        final Integer[] totalArticle = {0};
        List<CategoryResultVO> categoryResultList = categories.stream().map((category) -> {
            QueryWrapper<Article> articleWrapper = new QueryWrapper<>();
            articleWrapper.eq("category_id", category.getId());
            // 查询分类的文章数
            int count = articleService.count(articleWrapper);
            CategoryResultVO categoryResultVO = new CategoryResultVO();
            BeanUtils.copyProperties(category, categoryResultVO);
            // 设置文章数
            categoryResultVO.setArticleCount(count);
            totalArticle[0] = count + totalArticle[0];
            return categoryResultVO;
        }).collect(Collectors.toList());
        return Result.ok().data("categories", categoryResultList).data("total", totalArticle[0]);
    }

    /**
     * 获取所有标签数据
     * @return Result
     */
    @ApiOperation("获取所有标签数据")
    @GetMapping("/all/tag")
    public Result getAllTag() {
        // 查询所有标签
        List<Tag> tags = tagService.list(null);
        List<TagResultVO> tagResultList = tags.stream().map(tag -> {
            QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
            articleTagWrapper.eq("tag_id", tag.getId());
            // 获取标签的文章数
            int count = articleTagService.count(articleTagWrapper);
            TagResultVO tagResultVO = new TagResultVO();
            BeanUtils.copyProperties(tag, tagResultVO);
            // 设置文章数
            tagResultVO.setArticleCount(count);
            return tagResultVO;
        }).collect(Collectors.toList());
        return Result.ok().data("tags", tagResultList);
    }
}
