package com.mark.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
public interface TagService extends IService<Tag> {

    /**
     * 条件带分页查询标签数据
     * @param tagPage 分页对象
     * @param name 标签名
     */
    void executeTagPage(Page<Tag> tagPage, String name);

    /**
     * 删除标签及关联删除中间表数据
     * @param tagId 标签id
     */
    void deleteTag(String tagId);

    /**
     * 批量删除标签
     * @param tidList 标签id集合
     */
    void deleteTagBatch(List<String> tidList);
}
