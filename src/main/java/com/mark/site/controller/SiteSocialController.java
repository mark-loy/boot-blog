package com.mark.site.controller;


import com.mark.common.entity.Result;
import com.mark.site.entity.SiteSocial;
import com.mark.site.service.SiteSocialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 社交信息表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-30
 */
@Api(value = "社交管理", tags = {"社交服务接口管理"})
@RestController
@RequestMapping("api/site/social")
public class SiteSocialController {

    @Resource
    private SiteSocialService socialService;

    /**
     * 获取所有的社交信息
     * @return Result
     */
    @ApiOperation("获取所有的社交信息")
    @GetMapping("/")
    public Result getAllSocial() {
        List<SiteSocial> socials = socialService.list(null);
        return Result.ok().data("socials", socials);
    }

    /**
     * 保存社交信息
     * @param siteSocial 社交信息对象
     * @return Result
     */
    @ApiOperation("保存社交信息")
    @PostMapping("/")
    public Result saveSocial(@ApiParam("社交信息对象") @RequestBody SiteSocial siteSocial) {
        socialService.save(siteSocial);
        return Result.ok();
    }

    /**
     * 根据id所有社交信息
     * @param socialId 社交id
     * @return Result
     */
    @ApiOperation("根据id所有社交信息")
    @GetMapping("/{sid}")
    public Result getSocialId(@ApiParam("社交id") @PathVariable("sid") String socialId) {
        SiteSocial social = socialService.getById(socialId);
        return  Result.ok().data("social", social);
    }

    /**
     * 修改社交信息
     * @param siteSocial 社交信息对象
     * @return Result
     */
    @ApiOperation("修改社交信息")
    @PutMapping("/")
    public Result updateSocial(@ApiParam("社交信息对象") @RequestBody SiteSocial siteSocial) {
        socialService.updateById(siteSocial);
        return Result.ok();
    }

    /**
     * 根据id删除社交信息
     * @param socialId 社交id
     * @return Result
     */
    @ApiOperation("根据id删除社交信息")
    @DeleteMapping("/{sid}")
    public Result deleteSocial(@ApiParam("社交id") @PathVariable("sid") String socialId) {
        socialService.removeById(socialId);
        return Result.ok();
    }
}

