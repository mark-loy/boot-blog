package com.mark.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mark.acl.entity.Role;
import com.mark.acl.entity.User;
import com.mark.acl.service.IndexService;
import com.mark.acl.service.PermissionService;
import com.mark.acl.service.RoleService;
import com.mark.acl.service.UserService;
import com.mark.base.enums.CustomExceptionEnum;
import com.mark.base.exception.CustomException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/10 12:21
 */
@Service
public class IndexServiceImpl implements IndexService {


    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private RedisTemplate<String, List<String>> redisTemplate;

    /**
     * 获取用户信息
     * @param username 用户名
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getUserInfo(String username) {

        User user = userService.selectByUsername(username);
        if (null == user) {
            throw new CustomException(CustomExceptionEnum.USER_NO_EXIST);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        List<String> roleNameList = roleList.stream().map(Role::getRoleName).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        Map<String, Object> result = new HashMap<>(4);
        result.put("name", user.getUsername());
        result.put("avatar", user.getAvatar());
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);
        return result;
    }

    /**
     * 获取用户菜单信息
     * @param username 用户名
     * @return List<JSONObject>
     */
    @Override
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);
        //根据用户id获取用户菜单权限
        return permissionService.selectPermissionByUserId(user.getId());
    }
}
