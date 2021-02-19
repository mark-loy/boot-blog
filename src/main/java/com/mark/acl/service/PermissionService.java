package com.mark.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.mark.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户id查询菜单权限列表
     * @param id 用户id
     * @return List<String>
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 查询所有菜单
     * @return List<Permission>
     */
    List<Permission> selectAllPermission();

    /**
     * 通过菜单id删除菜单及子菜单
     * @param pid 菜单id
     */
    void deletePermissionLoop(String pid);

    /**
     * 通过用户id获取菜单
     * @param id 用户id
     * @return List<JSONObject>
     */
    List<JSONObject> selectPermissionByUserId(String id);
}
