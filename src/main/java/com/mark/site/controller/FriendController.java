package com.mark.site.controller;


import com.mark.base.valid.Group;
import com.mark.common.entity.Result;
import com.mark.site.entity.Friend;
import com.mark.site.entity.vo.FriendFormVO;
import com.mark.site.service.FriendService;
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
 * 友站信息表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-02-05
 */
@Api(value = "友站后台管理", tags = {"友站后台接口管理"})
@RestController
@RequestMapping("api/site/friend")
public class FriendController {

    @Resource
    private FriendService friendService;

    /**
     * 查询所有友站信息
     * @return Result
     */
    @ApiOperation("查询所有友站信息")
    @GetMapping("/")
    public Result getAllFriend() {
        List<Friend> friends = friendService.list(null);
        return Result.ok().data("friends", friends);
    }


    /**
     * 保存友站信息
     * @param friendVO 友站表单
     * @return Result
     */
    @ApiOperation("保存友站信息")
    @PostMapping("/")
    public Result saveFriend(@ApiParam("友站表单") @RequestBody @Validated(value = {Group.Add.class}) FriendFormVO friendVO) {
        Friend friend = new Friend();
        BeanUtils.copyProperties(friendVO, friend);
        friendService.save(friend);
        return Result.ok();
    }

    /**
     * 根据id查询友站信息
     * @param friendId 友站id
     * @return Result
     */
    @ApiOperation("根据id查询友站信息")
    @GetMapping("/{fid}")
    public Result getFriendById(@ApiParam("友站id") @PathVariable("fid") String friendId) {
        Friend friend = friendService.getById(friendId);
        return Result.ok().data("friend", friend);
    }

    /**
     * 修改友站信息
     * @param friendVO 友站表单
     * @return Result
     */
    @ApiOperation("修改友站信息")
    @PutMapping("/")
    public Result updateFriend(@ApiParam("友站表单") @RequestBody @Validated(value = {Group.Update.class}) FriendFormVO friendVO) {
        Friend friend = new Friend();
        BeanUtils.copyProperties(friendVO, friend);
        friendService.updateById(friend);
        return Result.ok();
    }

    /**
     * 删除友站
     * @param friendId 友站id
     * @return Result
     */
    @ApiOperation("删除友站")
    @DeleteMapping("/{fid}")
    public Result deleteFriend(@ApiParam("友站id") @PathVariable("fid") String friendId) {
        friendService.removeById(friendId);
        return Result.ok();
    }
}

