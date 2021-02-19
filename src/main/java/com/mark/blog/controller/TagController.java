package com.mark.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.base.valid.Group;
import com.mark.blog.entity.Tag;
import com.mark.blog.entity.vo.TagFormVO;
import com.mark.blog.service.TagService;
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
 * 标签表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Api(value = "标签管理", tags = {"标签管理服务接口"})
@RestController
@RequestMapping("api/blog/tag")
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 查询所有的标签信息
     * @return Result
     */
    @ApiOperation("查询所有的标签信息")
    @GetMapping("/all")
    public Result getAllTag() {
        List<Tag> tags = tagService.list(null);
        return Result.ok().data("tags", tags);
    }

    /**
     * 条件带分页查询标签
     * @param current 当前页
     * @param limit 当页显示数
     * @param name 标签名
     * @return Result
     */
    @ApiOperation("条件带分页查询标签")
    @GetMapping("/{current}/{limit}")
    public Result getTagPage(@ApiParam("当前页") @PathVariable("current") Long current,
                                  @ApiParam("当页显示数") @PathVariable("limit") Long limit,
                                  @ApiParam("标签名") @RequestParam(value = "name", required = false) String name) {
        if (current < 1 || limit < 1) {
            return Result.error().message("分页参数不合法");
        }
        // 构建分页对象
        Page<Tag> tagPage = new Page<>(current, limit);
        // 对分类数据执行分页
        tagService.executeTagPage(tagPage, name);
        // 获取分类的分页数据
        List<Tag> tags = tagPage.getRecords();
        long total = tagPage.getTotal();
        return Result.ok().data("tags", tags).data("total", total);
    }

    /**
     * 保存标签信息
     * @param tagFormVO 标签表单对象
     * @return Result
     */
    @ApiOperation("保存标签信息")
    @PostMapping("/")
    public Result saveTag(@ApiParam("标签表单对象") @Validated({Group.Add.class}) @RequestBody TagFormVO tagFormVO) {
        // 按分类名查询分类
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        tagWrapper.eq("name", tagFormVO.getName());
        int count = tagService.count(tagWrapper);
        // 判断结果集数量
        if (count > 0) {
            // 说明存在该标签
            return Result.error().message("标签名已存在");
        }
        // 否则保存标签信息
        Tag tag = new Tag();
        // 设置标签对象属性值
        BeanUtils.copyProperties(tagFormVO, tag);
        // 执行保存
        tagService.save(tag);
        return Result.ok();
    }

    /**
     * 根据id查询标签信息
     * @param tagId 标签id
     * @return Result
     */
    @ApiOperation("根据id查询标签信息")
    @GetMapping("/{tid}")
    public Result getTagById(@ApiParam("标签id") @PathVariable("tid") String tagId) {
        Tag tag = tagService.getById(tagId);
        return Result.ok().data("tag", tag);
    }

    /**
     * 修改标签信息
     * @param tagFormVO 标签表单对象
     * @return Result
     */
    @ApiOperation("修改标签信息")
    @PutMapping("/")
    public Result updateTag(@ApiParam("标签表单对象") @Validated({Group.Update.class}) @RequestBody TagFormVO tagFormVO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagFormVO, tag);
        tagService.updateById(tag);
        return Result.ok();
    }

    /**
     * 根据id删除标签
     * @param tagId 标签id
     * @return Result
     */
    @ApiOperation("根据id删除分类")
    @DeleteMapping("/{tid}")
    public Result deleteTag(@ApiParam("标签id") @PathVariable("tid") String tagId) {
        tagService.deleteTag(tagId);
        return Result.ok();
    }

    /**
     * 批量删除标签
     * @param tidList 标签id集合
     * @return Result
     */
    @ApiOperation("批量删除标签")
    @DeleteMapping("/batch")
    public Result deleteBatchTag(@ApiParam("标签id集合") @RequestParam("tidList") List<String> tidList) {
        tagService.deleteTagBatch(tidList);
        return Result.ok();
    }

}

