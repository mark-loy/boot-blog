package com.mark.site.controller;


import com.mark.common.entity.Result;
import com.mark.site.entity.SiteInfo;
import com.mark.site.service.SiteInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 站点信息表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-30
 */
@Api(value = "站点信息管理", tags = {"站点服务接口管理"})
@RestController
@RequestMapping("api/site/info")
public class SiteInfoController {

    @Resource
    private SiteInfoService infoService;

    /**
     * 获取站点信息
     * @return Result
     */
    @ApiOperation("获取站点信息")
    @GetMapping("/")
    public Result getSiteInfo() {
        SiteInfo info = infoService.getById("1");
        return  Result.ok().data("siteInfo", info);
    }

    /**
     * 修改站点信息
     * @param siteInfo 站点信息对象
     * @return Result
     */
    @ApiOperation("修改站点信息")
    @PutMapping("/")
    public Result updateSiteInfo(@ApiParam("站点信息对象") @RequestBody SiteInfo siteInfo) {
        siteInfo.setId("1");
        infoService.updateById(siteInfo);
        return Result.ok();
    }

}

