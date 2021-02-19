package com.mark.blog.client.impl;

import com.mark.acl.entity.User;
import com.mark.acl.service.UserService;
import com.mark.base.vo.UserRpcVO;
import com.mark.blog.client.AclClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/15 21:57
 */
@Component
public class AclClientImpl implements AclClient {

    @Resource
    private UserService userService;

    @Override
    public UserRpcVO selectUserById(String uid) {
        User user = userService.getById(uid);
        UserRpcVO userRpcVO = new UserRpcVO();
        BeanUtils.copyProperties(user, userRpcVO);
        return userRpcVO;
    }
}
