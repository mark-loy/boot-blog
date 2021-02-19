package com.mark.acl.service;

import com.mark.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mark.base.vo.UserRpcVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-02-09
 */
public interface UserService extends IService<User> {

    /**
     * 通过用户名查询用户
     * @param username 用户名
     * @return User
     */
    User selectByUsername(String username);

    /**
     * 给用户分配角色
     * @param uid 用户id
     * @param rids 角色id
     */
    void saveAssignUserRole(String uid, List<String> rids);

    /**
     * 通过用户id获取用户分配的角色
     * @param uid 用户id
     * @return Map<String, Object>
     */
    Map<String, Object> selectAssignRoleByUser(String uid);

}
