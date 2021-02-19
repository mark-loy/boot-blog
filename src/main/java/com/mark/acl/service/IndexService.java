package com.mark.acl.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/10 12:21
 */
public interface IndexService {


    /**
     * 获取用户信息
     * @param username 用户名
     * @return Map<String, Object>
     */
    Map<String, Object> getUserInfo(String username);

    /**
     * 获取用户的菜单信息
     * @param username 用户名
     * @return List<JSONObject>
     */
    List<JSONObject> getMenu(String username);
}
