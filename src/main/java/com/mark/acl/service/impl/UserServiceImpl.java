package com.mark.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.acl.entity.Role;
import com.mark.acl.entity.User;
import com.mark.acl.entity.UserRole;
import com.mark.acl.mapper.UserMapper;
import com.mark.acl.service.RoleService;
import com.mark.acl.service.UserRoleService;
import com.mark.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.base.vo.UserRpcVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return User
     */
    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    /**
     * 给用户分配角色
     * @param uid 用户id
     * @param rids 角色id
     */
    @Override
    public void saveAssignUserRole(String uid, List<String> rids) {
        // 先删除原有用户分配的角色
        QueryWrapper<UserRole> userRoleQuery = new QueryWrapper<>();
        userRoleQuery.eq("user_id", uid);
        userRoleService.remove(userRoleQuery);

        List<UserRole> userRoles = rids.stream().map(rid -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(rid).setUserId(uid);
            return userRole;
        }).collect(Collectors.toList());

        // 保存新的用户角色
        userRoleService.saveBatch(userRoles);
    }

    /**
     * 通过用户id获取用户分配的角色
     * @param uid 用户id
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> selectAssignRoleByUser(String uid) {
        // 查询所有的角色
        List<Role> roles = roleService.list(null);
        // 查询该用户被分配的角色
        List<String> roleIdList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", uid))
                .stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 构建被分配的角色集合
        List<Role> assignRoles = new ArrayList<>();
        // 遍历所有角色
        for (Role role : roles) {
            if (roleIdList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        // 构建返回map
        Map<String, Object> map = new HashMap<>(2);
        map.put("assignRoles", assignRoles);
        map.put("roles", roles);
        return map;
    }
}
