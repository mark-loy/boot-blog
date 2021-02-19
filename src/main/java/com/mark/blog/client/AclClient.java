package com.mark.blog.client;

import com.mark.base.vo.UserRpcVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/15 21:57
 */
public interface AclClient {

    /**
     * 获取用户信息
     * @param uid 用户id
     * @return Result
     */
    @GetMapping("api/acl/index/user/{uid}")
    UserRpcVO selectUserById(@PathVariable("uid") String uid);
}
