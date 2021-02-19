package com.mark.sta.controller;


import com.mark.common.entity.Result;
import com.mark.sta.service.StaArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 文章统计表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
@Api(value = "统计管理", tags = {"统计管理服务接口"})
@RestController
@RequestMapping("api/sta/article")
public class StaArticleController {

    @Resource
    private StaArticleService staArticleService;

    /**
     * 生成文章统计数据
     * @param date 日期
     * @return Result
     */
    @ApiOperation("生成文章统计数据")
    @PostMapping("/{date}")
    public Result generateArticleSta(@ApiParam(value = "日期", example = "2020-01-24") @PathVariable("date") String date) {
        staArticleService.saveArticleSta(date);
        return Result.ok();
    }
}

