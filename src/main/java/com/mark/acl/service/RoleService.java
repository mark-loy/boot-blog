package com.mark.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.mark.acl.entity.Permission;
import com.mark.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
public interface RoleService extends IService<Role> {

    /**
     * 给角色分配菜单
     * @param roleId 角色id
     * @param perId 菜单id集合
     */
    void saveAssignRolePermission(String roleId, List<String> perId);

    /**
     * 通过角色获取菜单列表
     * @param rid 角色id
     * @return Result
     */
    List<Permission> selectPermissionByRole(String rid);

    /**
     * 通过用户id获取角色列表
     * @param id 用户id
     * @return List<Role>
     */
    List<Role> selectRoleByUserId(String id);

}
