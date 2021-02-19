package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.blog.entity.ArticleTag;
import com.mark.blog.entity.Tag;
import com.mark.blog.mapper.TagMapper;
import com.mark.blog.service.ArticleTagService;
import com.mark.blog.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private ArticleTagService articleTagService;

    /**
     * 条件查询及分页
     * @param tagPage 分页对象
     * @param name 标签名
     */
    @Override
    public void executeTagPage(Page<Tag> tagPage, String name) {
        // 构建条件对象
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        // 判断name非空
        if (!StringUtils.isEmpty(name)) {
            tagWrapper.like("name", name);
        }
        // 执行分页查询
        baseMapper.selectPage(tagPage, tagWrapper);
    }

    /**
     * 删除标签及中间表数据
     * @param tagId 标签id
     */
    @Override
    public void deleteTag(String tagId) {
        // 先删除中间表数据
        QueryWrapper<ArticleTag> articleTagWrapper = new QueryWrapper<>();
        // 设置标签id
        articleTagWrapper.eq("tag_id", tagId);
        // 执行删除
        articleTagService.remove(articleTagWrapper);
        // 再删除标签
        baseMapper.deleteById(tagId);
    }

    /**
     * 批量删除标签
     * @param tidList 标签id集合
     */
    @Override
    public void deleteTagBatch(List<String> tidList) {
        // 遍历
        for (String tagId : tidList) {
            deleteTag(tagId);
        }
    }
}
