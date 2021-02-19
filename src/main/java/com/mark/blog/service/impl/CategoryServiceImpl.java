package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.blog.entity.Category;
import com.mark.blog.mapper.CategoryMapper;
import com.mark.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 条件查询并执行分页
     * @param categoryPage 分页对象
     * @param name 分类名称
     */
    @Override
    public void executeCategoryPage(Page<Category> categoryPage, String name) {
        // 构建条件对象
        QueryWrapper<Category> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.orderByDesc("gmt_create");
        // 判断name
        if (!StringUtils.isEmpty(name)) {
            categoryWrapper.like("name", name);
        }
        // 执行分页
        baseMapper.selectPage(categoryPage, categoryWrapper);
    }
}
