package com.mark.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.blog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 分类表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
public interface CategoryService extends IService<Category> {

    /**
     * 对分类数据执行分页
     * @param categoryPage 分页对象
     * @param name 分类名称
     */
    void executeCategoryPage(Page<Category> categoryPage, String name);
}
