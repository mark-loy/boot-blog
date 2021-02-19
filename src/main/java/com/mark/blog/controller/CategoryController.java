package com.mark.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.base.valid.Group;
import com.mark.blog.entity.Category;
import com.mark.blog.entity.vo.CategoryFormVO;
import com.mark.blog.service.CategoryService;
import com.mark.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 分类表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Api(value = "分类管理", tags = {"分类管理服务接口"})
@RestController
@RequestMapping("api/blog/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 查询所有的分类信息
     * @return Result
     */
    @ApiOperation("查询所有的分类信息")
    @GetMapping("/all")
    public Result getAllCategory() {
        List<Category> categories = categoryService.list(null);
        return Result.ok().data("categories", categories);
    }

    /**
     * 条件带分页查询分类
     * @param current 当前页
     * @param limit 当页显示数
     * @param name 分类名
     * @return Result
     */
    @ApiOperation("条件带分页查询")
    @GetMapping("/{current}/{limit}")
    public Result getCategoryPage(@ApiParam("当前页") @PathVariable("current") Long current,
                                  @ApiParam("当页显示数") @PathVariable("limit") Long limit,
                                  @ApiParam("分类名") @RequestParam(value = "name", required = false) String name) {
        if (current < 1 || limit < 1) {
            return Result.error().message("分页参数不合法");
        }
        // 构建分页对象
        Page<Category> categoryPage = new Page<>(current, limit);
        // 对分类数据执行分页
        categoryService.executeCategoryPage(categoryPage, name);
        // 获取分类的分页数据
        List<Category> categories = categoryPage.getRecords();
        long total = categoryPage.getTotal();
        return Result.ok().data("categories", categories).data("total", total);
    }

    /**
     * 保存分类信息
     * @param categoryFormVO 分类表单对象
     * @return Result
     */
    @ApiOperation("保存分类信息")
    @PostMapping("/")
    public Result saveCategory(@ApiParam("分类表单对象") @Validated({Group.Add.class}) @RequestBody CategoryFormVO categoryFormVO) {
        // 按分类名查询分类
        QueryWrapper<Category> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.eq("name", categoryFormVO.getName());
        int count = categoryService.count(categoryWrapper);
        // 判断结果集数量
        if (count > 0) {
            // 说明存在该分类
            return Result.error().message("分类名已存在");
        }
        // 否则保存分类信息
        Category category = new Category();
        // 设置分类对象属性值
        BeanUtils.copyProperties(categoryFormVO, category);
        // 执行保存
        categoryService.save(category);
        return Result.ok();
    }

    /**
     * 根据id查询分类信息
     * @param categoryId 分类id
     * @return Result
     */
    @ApiOperation("根据id查询分类信息")
    @GetMapping("/{cid}")
    public Result getCategoryById(@ApiParam("分类id") @PathVariable("cid") String categoryId) {
        Category category = categoryService.getById(categoryId);
        return Result.ok().data("category", category);
    }

    /**
     * 修改分类信息
     * @param categoryFormVO 分类表单对象
     * @return Result
     */
    @ApiOperation("修改分类信息")
    @PutMapping("/")
    public Result updateCategory(@ApiParam("分类表单对象") @Validated({Group.Update.class}) @RequestBody CategoryFormVO categoryFormVO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryFormVO, category);
        categoryService.updateById(category);
        return Result.ok();
    }

    /**
     * 根据id删除分类
     * @param categoryId 分类id
     * @return Result
     */
    @ApiOperation("根据id删除分类")
    @DeleteMapping("/{cid}")
    public Result deleteCategory(@ApiParam("分类id") @PathVariable("cid") String categoryId) {
        categoryService.removeById(categoryId);
        return Result.ok();
    }

    /**
     * 批量删除分类
     * @param cidList 分类id集合
     * @return Result
     */
    @ApiOperation("批量删除分类")
    @DeleteMapping("/batch")
    public Result deleteBatchCategory(@ApiParam("分类id集合") @RequestParam("cidList") List<String> cidList) {
        categoryService.removeByIds(cidList);
        return Result.ok();
    }
}

