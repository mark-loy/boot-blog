package com.mark.ucenter.controller.front;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.common.utils.HttpClientUtil;
import com.mark.common.utils.JwtUtil;
import com.mark.ucenter.entity.Visitor;
import com.mark.ucenter.service.VisitorService;
import com.mark.ucenter.util.UCenterConstant;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/3 20:54
 */
@Api(value = "gitee认证登录", tags = {"gitee认证登录接口管理"})
@Controller
@RequestMapping("api/ucenter/login/gitee")
public class GiteeLoginController {

    @Resource
    private VisitorService visitorService;

    /**
     * Gitee登录：
     *     步骤一：去gitee权限页面
     * @param request 请求
     * @return String
     */
    @GetMapping("/")
    public String toGiteeLogin(HttpServletRequest request) {
        // 定义模板url
        String templateUrl = "https://gitee.com/oauth/authorize" +
                "?client_id=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=user_info";

        return "redirect:" + String.format(templateUrl, UCenterConstant.GITEE_CLIENT_ID, UCenterConstant.GITEE_REDIRECT_URL);
    }

    /**
     * Gitee登录：
     *     步骤二：通过code获取token ==》 通过token获取用户信息
     * @param code 凭证码
     * @return String
     */
    @GetMapping("/callback")
    public String getTokenUser(@RequestParam("code") String code) {
        // 定义模板url
        String templateUrl = "https://gitee.com/oauth/token" +
                "?grant_type=authorization_code" +
                "&code=%s" +
                "&client_id=%s" +
                "&redirect_uri=%s";
        // 替换url
        String url = String.format(templateUrl, code, UCenterConstant.GITEE_CLIENT_ID, UCenterConstant.GITEE_REDIRECT_URL);
        Map<String, String> httpBody = new HashMap<>(1);
        httpBody.put("client_secret", UCenterConstant.GITEE_SECRET);
        // 设置请求头
        Map<String, String> headers = new HashMap<>(1);
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        // 发送post请求
        String resultStr = HttpClientUtil.doPostBodyMapHeader(url, httpBody, headers);
        System.out.println(resultStr);
        // 将结果string，转为map类型
        HashMap tokenMap = JSON.parseObject(resultStr, HashMap.class);
        String token = (String) tokenMap.get("access_token");

        // 定义请求用户信息url
        String userUrl = "https://gitee.com/api/v5/user?access_token=" + token;

        String userStr = HttpClientUtil.doGetHeaders(userUrl, headers);
        System.out.println(userStr);
        HashMap userMap = JSON.parseObject(userStr, HashMap.class);

        // 获取用户id
        Integer id = (Integer) userMap.get("id");
        QueryWrapper<Visitor> visitorWrapper = new QueryWrapper<>();
        visitorWrapper.eq("account_id", id + "");
        Visitor visitor = visitorService.getOne(visitorWrapper);
        // 判断非空
        if (visitor == null) {
            // 说明第一次登录，添加访客信息
            visitor = new Visitor();
            visitor.setNickname((String) userMap.get("login"));
            visitor.setAvatar((String) userMap.get("avatar_url"));
            visitor.setAccountId(id + "");
            visitorService.save(visitor);
        }

        // 将访客信息存入token中
        Map<String, Object> map = new HashMap<>(3);
        map.put("id", visitor.getId());
        map.put("nickname", visitor.getNickname());
        map.put("avatar", visitor.getAvatar());
        String jwtToken = JwtUtil.getJwtToken(map);
        return "redirect:"+ UCenterConstant.SITE_URL +"/?token=" + jwtToken;
    }


}
