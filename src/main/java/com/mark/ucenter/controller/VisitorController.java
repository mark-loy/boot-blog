package com.mark.ucenter.controller;


import com.mark.common.entity.Result;
import com.mark.ucenter.entity.Visitor;
import com.mark.ucenter.entity.vo.VisitorQueryVO;
import com.mark.ucenter.service.VisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 访客表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
@Api(value = "访客管理", tags = {"访客服务接口"})
@RestController
@RequestMapping("api/ucenter/visitor")
public class VisitorController {

    @Resource
    private VisitorService visitorService;

    /**
     * 多条件组合分页查询访客数据
     * @param current 当前页
     * @param limit 当页显示数
     * @param visitorQueryVO 访客查询对象
     * @return Result
     */
    @ApiOperation("多条件组合分页查询访客数据")
    @PostMapping("/{current}/{limit}")
    public Result getVisitorPage(@ApiParam("当前页") @PathVariable("current") Long current,
                                 @ApiParam("当页显示数") @PathVariable("limit") Long limit,
                                 @ApiParam("访客查询对象") @RequestBody(required = false)VisitorQueryVO visitorQueryVO) {
        if (current < 1 || limit < 1) {
            return Result.error().message("分页参数不合法");
        }
        Map<String, Object> map = visitorService.getVisitorQueryPage(current, limit, visitorQueryVO);
        return Result.ok().data(map);
    }

    /**
     * 修改访客状态
     * @param vid 访客id
     * @param status 访客状态
     * @return Result
     */
    @ApiOperation("修改访客状态")
    @PutMapping("/{vid}")
    public Result updateVisitorState(@ApiParam("访客id") @PathVariable("vid") String vid,
                                     @ApiParam("访客状态") @RequestParam("status") Boolean status) {
        Visitor visitor = new Visitor();
        visitor.setId(vid);
        visitor.setIsDisabled(status);
        visitorService.updateById(visitor);
        return Result.ok();
    }

    /**
     * 删除访客
     * @param visitorId 访客id
     * @return Result
     */
    @ApiOperation("删除访客")
    @DeleteMapping("/{vid}")
    public Result deleteVisitor(@ApiParam("访客id") @PathVariable("vid") String visitorId) {
        visitorService.removeById(visitorId);
        return Result.ok();
    }

}

