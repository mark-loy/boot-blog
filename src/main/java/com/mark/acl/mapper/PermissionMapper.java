package com.mark.acl.mapper;

import com.mark.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询所有权限
     * @return List<String>
     */
    List<String> selectAllPermissionValue();

    /**
     * 根据用户id查询菜单权限
     * @param id 用户id
     * @return List<String>
     */
    List<String> selectPermissionValueByUserId(String id);

    /**
     * 根据用户id查询菜单
     * @param userId 用户id
     * @return List<Permission>
     */
    List<Permission> selectPermissionByUserId(String userId);
}
