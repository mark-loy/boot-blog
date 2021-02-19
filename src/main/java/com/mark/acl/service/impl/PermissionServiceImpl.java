package com.mark.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.acl.entity.Permission;
import com.mark.acl.helper.PermissionServiceHelper;
import com.mark.acl.mapper.PermissionMapper;
import com.mark.acl.service.PermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    private PermissionServiceHelper permissionHelper;

    /**
     * 根据用户id查询权限列表
     * @param id 用户id
     * @return List<String>
     */
    @Override
    public List<String> selectPermissionValueByUserId(String id) {
        List<String> selectPermissionValueList;
        if(permissionHelper.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }


    /**
     * 查询所有菜单
     * @return List<Permission>
     */
    @Override
    public List<Permission> selectAllPermission() {
        QueryWrapper<Permission> permissionWrapper = new QueryWrapper<>();
        permissionWrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(permissionWrapper);
        return permissionHelper.packagePermission(permissions);
    }

    /**
     * 递归删除菜单
     * @param pid 菜单id
     */
    @Override
    public void deletePermissionLoop(String pid) {
        // 查询所有菜单
        List<Permission> permissions = baseMapper.selectList(null);
        // 构建最终的需要删除的菜单id集合
        List<String> idList = new ArrayList<>();
        // 批量删除菜单
        baseMapper.deleteBatchIds(permissionHelper.packagePermissionId(permissions, pid, idList));
    }

    /**
     * 通过用户id获取菜单
     * @param id 用户id
     * @return List<JSONObject>
     */
    @Override
    public List<JSONObject> selectPermissionByUserId(String id) {
        List<Permission> selectPermissionList;
        if(permissionHelper.isSysAdmin(id)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(id);
        }
        // 封装菜单
        List<Permission> permissionList = permissionHelper.packagePermission(selectPermissionList);
        // 返回并构建菜单
        return permissionHelper.buildMenus(permissionList);
    }
}
