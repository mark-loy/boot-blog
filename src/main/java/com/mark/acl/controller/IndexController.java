package com.mark.acl.controller;

import com.alibaba.fastjson.JSONObject;
import com.mark.acl.entity.User;
import com.mark.acl.entity.vo.UserResponseVO;
import com.mark.acl.service.IndexService;
import com.mark.acl.service.UserService;
import com.mark.base.vo.UserRpcVO;
import com.mark.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/10 12:19
 */
@Api(value = "登录页管理")
@RestController
public class IndexController {

    @Resource
    private IndexService indexService;

    @Resource
    private UserService userService;

    /**
     * 获取用户信息
     * @return Result
     */
    @ApiOperation("获取用户信息")
    @GetMapping("admin/acl/index/info")
    public Result info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.ok().data(userInfo);
    }

    /**
     * 获取用户的菜单信息
     * @return Result
     */
    @ApiOperation("获取用户的菜单信息")
    @GetMapping("admin/acl/index/menu")
    public Result getMenu(){
        //获取当前登录用户菜单
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.ok().data("permissionList", permissionList);
    }

    /**
     * 获取所有用户
     * @return Result
     */
    @ApiOperation("获取所有用户")
    @GetMapping("api/acl/index/user/all")
    public Result getUser() {
        // 查询所有的用户
        List<User> users = userService.list(null);
        // 将所有用户映射为返回的用户对象
        List<UserResponseVO> usersResponse = users.stream().map(user -> {
            // 构建返回的user对象
            UserResponseVO userResponse = new UserResponseVO();
            BeanUtils.copyProperties(user, userResponse);
            return userResponse;
        }).collect(Collectors.toList());
        return  Result.ok().data("users", usersResponse);
    }
}
