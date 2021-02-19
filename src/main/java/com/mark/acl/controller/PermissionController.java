package com.mark.acl.controller;


import com.mark.acl.entity.Permission;
import com.mark.acl.entity.vo.PermissionFormVO;
import com.mark.acl.service.PermissionService;
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
 * 权限 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
@Api(value = "菜单管理", tags = {"菜单服务接口管理"})
@RestController
@RequestMapping("admin/acl/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    /**
     * 查询所有菜单
     * @return Result
     */
    @ApiOperation("查询所有菜单")
    @GetMapping("/all")
    public Result getAllPermission() {
        List<Permission> permissions = permissionService.selectAllPermission();
        return Result.ok().data("permissions", permissions);
    }

    /**
     * 保存菜单
     * @param permissionForm 菜单表单实体
     * @return Result
     */
    @ApiOperation("保存菜单")
    @PostMapping
    public Result savePermission(@RequestBody @Validated(value = {Group.Add.class}) PermissionFormVO permissionForm) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionForm, permission);
        permissionService.save(permission);
        return Result.ok();
    }

    /**
     * 通过菜单id查询菜单信息
     * @param pid 菜单id
     * @return Result
     */
    @ApiOperation("通过菜单id查询菜单信息")
    @GetMapping("/{pid}")
    public Result selectPermissionById(@ApiParam("菜单id") @PathVariable("pid") String pid) {
        Permission permission = permissionService.getById(pid);
        return Result.ok().data("permission", permission);
    }

    /**
     * 修改菜单
     * @param permissionForm 菜单表单实体
     * @return Result
     */
    @ApiOperation("修改菜单")
    @PutMapping
    public Result updatePermission(@RequestBody @Validated({Group.Update.class}) PermissionFormVO permissionForm) {
        Permission permission = new Permission();
        BeanUtils.copyProperties(permissionForm, permission);
        permissionService.updateById(permission);
        return Result.ok();
    }

    /**
     * 删除菜单及子菜单
     * @param pid 菜单id
     * @return Result
     */
    @ApiOperation("删除菜单及子菜单")
    @DeleteMapping("/{pid}")
    public Result deletePermission(@ApiParam("菜单id") @PathVariable("pid") String pid) {
        permissionService.deletePermissionLoop(pid);
        return Result.ok();
    }

}

