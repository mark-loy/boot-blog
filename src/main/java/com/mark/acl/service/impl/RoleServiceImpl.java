package com.mark.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.acl.entity.Permission;
import com.mark.acl.entity.Role;
import com.mark.acl.entity.RolePermission;
import com.mark.acl.entity.UserRole;
import com.mark.acl.helper.PermissionServiceHelper;
import com.mark.acl.mapper.RoleMapper;
import com.mark.acl.service.PermissionService;
import com.mark.acl.service.RolePermissionService;
import com.mark.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.acl.service.UserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private PermissionService permissionService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private PermissionServiceHelper permissionHelper;

    @Resource
    private UserRoleService userRoleService;

    /**
     * 给角色分配权限
     * @param roleId 角色id
     * @param perIds 菜单id集合
     */
    @Override
    public void saveAssignRolePermission(String roleId, List<String> perIds) {
        // 先删除原有角色分配的菜单
        QueryWrapper<RolePermission> rolePermissionQuery = new QueryWrapper<>();
        rolePermissionQuery.eq("role_id", roleId);
        rolePermissionService.remove(rolePermissionQuery);

        List<RolePermission> rolePermissions = perIds.stream().map(perId -> {
            // 构建角色权限对象
            RolePermission rolePermission = new RolePermission();
            // 设置角色id及权限id
            rolePermission.setRoleId(roleId).setPermissionId(perId);
            return rolePermission;
        }).collect(Collectors.toList());
        // 批量保存角色权限数据
        rolePermissionService.saveBatch(rolePermissions);
    }

    /**
     * 通过角色id获取菜单
     * @param rid 角色id
     * @return List<Permission>
     */
    @Override
    public List<Permission> selectPermissionByRole(String rid) {
        // 获取角色菜单列表数据
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id", rid));
        // 获取所有菜单列表
        List<Permission> permissions = permissionService.list(null);
        // 遍历所有菜单列表
        for (Permission permission : permissions) {
            // 遍历角色菜单列表
            for (RolePermission rolePermission : rolePermissionList) {
                if (permission.getId().equals(rolePermission.getPermissionId())) {
                    // 设置该菜单已选中
                    permission.setIsSelect(true);
                }
            }
        }
        // 生成菜单,返回菜单数据
        return permissionHelper.packagePermission(permissions);
    }

    /**
     * 通过用户id获取角色
     * @param id 用户id
     * @return List<Role>
     */
    @Override
    public List<Role> selectRoleByUserId(String id) {
        //根据用户id拥有的角色id
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
