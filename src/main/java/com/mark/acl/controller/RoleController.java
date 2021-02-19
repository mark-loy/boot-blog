package com.mark.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.acl.entity.Permission;
import com.mark.acl.entity.Role;
import com.mark.acl.entity.vo.RoleFormVO;
import com.mark.acl.service.RoleService;
import com.mark.base.valid.Group;
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
 *  前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
@Api(value = "角色管理", tags = {"角色服务接口管理"})
@RestController
@RequestMapping("admin/acl/role")
public class RoleController {


    @Resource
    private RoleService roleService;

    /**
     * 查询角色分页数据
     * @param current 当前页
     * @param limit 当页显示数
     * @return Result
     */
    @ApiOperation("查询角色分页数据")
    @PostMapping("/{current}/{limit}")
    public Result selectRolePage(@ApiParam("当前页") @PathVariable("current") Long current,
                                 @ApiParam("当页显示数") @PathVariable("limit") Long limit) {
        // 分页参数判断
        if (current < 1 || limit < 1) {
            return Result.error().message("分页参数不合法");
        }
        Page<Role> rolePage = new Page<>(current, limit);
        QueryWrapper<Role> roleQuery = new QueryWrapper<>();
        roleQuery.orderByDesc("gmt_create");
        roleService.page(rolePage, roleQuery);
        // 获取分页数据
        List<Role> roles = rolePage.getRecords();
        long total = rolePage.getTotal();
        return Result.ok().data("roles", roles).data("total", total);
    }

    /**
     * 保存角色
     * @param roleForm 角色表单
     * @return Result
     */
    @ApiOperation("保存角色")
    @PostMapping
    public Result saveRole(@RequestBody @Validated({Group.Add.class}) RoleFormVO roleForm) {
        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role);
        roleService.save(role);
        return Result.ok();
    }

    /**
     * 根据角色id查询角色
     * @param rid 角色id
     * @return Result
     */
    @ApiOperation("根据角色id查询角色")
    @GetMapping("/{rid}")
    public Result selectRoleById(@ApiParam("角色id") @PathVariable("rid") String rid) {
        Role role = roleService.getById(rid);
        return Result.ok().data("role", role);
    }

    /**
     * 修改角色
     * @param roleForm 角色表单
     * @return Result
     */
    @ApiOperation("修改角色")
    @PutMapping
    public Result updateRole( @RequestBody @Validated({Group.Update.class}) RoleFormVO roleForm) {
        Role role = new Role();
        BeanUtils.copyProperties(roleForm, role);
        roleService.updateById(role);
        return Result.ok();
    }

    /**
     * 删除角色
     * @param rid 角色id
     * @return Result
     */
    @ApiOperation("删除角色")
    @DeleteMapping("/{rid}")
    public Result deleteRole(@ApiParam("角色id") @PathVariable("rid") String rid) {
        roleService.removeById(rid);
        return Result.ok();
    }


    /**
     * 给角色分配菜单
     * @param roleId 角色id
     * @param perIds 菜单id集合
     * @return Result
     */
    @ApiOperation("给角色分配菜单")
    @PostMapping("/doAssign")
    public Result saveAssignRolePermission(@ApiParam("角色id") @RequestParam("roleId") String roleId,
                                           @ApiParam("菜单id集合") @RequestParam("perIds") List<String> perIds) {
        roleService.saveAssignRolePermission(roleId, perIds);
        return Result.ok();
    }

    /**
     * 通过角色获取菜单列表
     * @param rid 角色id
     * @return Result
     */
    @ApiOperation("通过角色获取菜单列表")
    @GetMapping("/toAssign/{roleId}")
    public Result selectPermissionByRole(@ApiParam("角色id") @PathVariable("roleId") String rid) {
        List<Permission> permissions = roleService.selectPermissionByRole(rid);
        return Result.ok().data("permissions", permissions);
    }

}

