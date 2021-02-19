package com.mark.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.acl.entity.User;
import com.mark.acl.entity.vo.UserFormVO;
import com.mark.acl.entity.vo.UserResponseVO;
import com.mark.acl.service.UserService;
import com.mark.base.valid.Group;
import com.mark.common.entity.Result;
import com.mark.common.utils.MD5;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
@Api(value = "用户管理", tags = {"用户服务接口管理"})
@RestController
@RequestMapping("admin/acl/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 查询用户分页数据
     * @param current 当前页
     * @param limit 当页显示数
     * @return Result
     */
    @ApiOperation("查询用户分页数据")
    @PostMapping("/{current}/{limit}")
    public Result selectUserPage(@ApiParam("当前页") @PathVariable("current") Long current,
                                 @ApiParam("当页显示数") @PathVariable("limit") Long limit) {
        // 判断分页参数
        if (current < 1 || limit < 1) {
            return Result.ok().message("分页参数不合法");
        }
        Page<User> userPage = new Page<>(current, limit);
        QueryWrapper<User> userQuery = new QueryWrapper<>();
        userQuery.orderByDesc("gmt_create");
        userService.page(userPage, userQuery);
        // 获取分页数据
        List<User> users = userPage.getRecords();
        long total = userPage.getTotal();
        return Result.ok().data("users", users).data("total", total);
    }

    /**
     * 保存用户
     * @param userForm 用户表单实体
     * @return Result
     */
    @ApiOperation("保存用户")
    @PostMapping
    public Result saveUser(@RequestBody @Validated({Group.Add.class})UserFormVO userForm) {
        // 通过用户查询用户
        int count = userService.count(new QueryWrapper<User>().eq("username", userForm.getUsername()));
        if (count > 0) {
            return Result.error().message("用户名已存在");
        }
        // 对密码进行MD5加密
        userForm.setPassword(MD5.encrypt(userForm.getPassword()));

        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.save(user);
        return Result.ok();
    }

    /**
     * 通过id查询用户
     * @param uid 用户id
     * @return Result
     */
    @ApiOperation("通过id查询用户")
    @GetMapping("/{uid}")
    public Result selectUserById(@ApiParam("用户id") @PathVariable("uid") String uid) {
        User user = userService.getById(uid);
        UserResponseVO userResult = new UserResponseVO();
        BeanUtils.copyProperties(user, userResult);
        return Result.ok().data("user", userResult);
    }

    /**
     * 修改用户
     * @param userForm 用户表单实体
     * @return Result
     */
    @ApiOperation("修改用户")
    @PutMapping
    public Result updateUser(@RequestBody @Validated({Group.Update.class}) UserFormVO userForm) {
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        userService.updateById(user);
        return Result.ok();
    }

    /**
     * 删除用户
     * @param uid 用户id
     * @return Result
     */
    @ApiOperation("删除用户")
    @DeleteMapping("/{uid}")
    public Result deleteUser(@ApiParam("用户id") @PathVariable("uid") String uid) {
        userService.removeById(uid);
        return Result.ok();
    }

    /**
     * 给用户分配角色
     * @param uid 用户id
     * @param rids 角色id集合
     * @return Result
     */
    @ApiOperation("给用户分配角色")
    @PostMapping("/doAssign")
    public Result saveAssignUserRole(@ApiParam("用户id") @RequestParam("uid") String uid,
                                     @ApiParam("角色id集合") @RequestParam("rids") List<String> rids) {
        userService.saveAssignUserRole(uid, rids);
        return Result.ok();
    }

    /**
     * 通过用户获取角色
     * @param uid 用户id
     * @return Result
     */
    @ApiOperation("通过用户获取角色")
    @GetMapping("/toAssign/{uid}")
    public Result selectAssignRoleByUser(@ApiParam("用户id") @PathVariable("uid") String uid) {
        Map<String, Object> map = userService.selectAssignRoleByUser(uid);
        return Result.ok().data(map);
    }
}

