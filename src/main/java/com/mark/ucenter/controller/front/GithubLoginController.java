package com.mark.ucenter.controller.front;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.base.enums.CustomExceptionEnum;
import com.mark.base.exception.CustomException;
import com.mark.common.utils.HttpClientUtil;
import com.mark.common.utils.JwtUtil;
import com.mark.ucenter.entity.Visitor;
import com.mark.ucenter.service.VisitorService;
import com.mark.ucenter.util.UCenterConstant;
import io.swagger.annotations.Api;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/2 12:45
 */
@Api(value = "github认证登录", tags = {"github认证登录接口管理"})
@Controller
@RequestMapping("api/ucenter/login/github")
public class GithubLoginController {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private VisitorService visitorService;

    /**
     * GitHub登录：
     * 步骤一：跳转到授权页面
     *
     * @return String
     */
    @GetMapping("/")
    public String getGithubLogin(HttpServletRequest request) {
        // 定义模板url
        String url = "https://github.com/login/oauth/authorize" +
                "?client_id=%s" +
                "&redirect_uri=%s" +
                "&scope=user" +
                "&state=%s";
        // 定义state
        String state = "";
        // 从redis中取state, 判断state是否存在，不存在则生成state
        String sessionId = request.getSession().getId();
        state = redisTemplate.opsForValue().get(sessionId);
        if (StringUtils.isEmpty(state)) {
            // 生成state
            state = UUID.randomUUID().toString().replace("-", "");
            // 存入redis,有效期为30分钟
            redisTemplate.opsForValue().set(sessionId, state, 30, TimeUnit.MINUTES);
        }

        // 返回替换模板url
        return "redirect:" + String.format(url, UCenterConstant.GITHUB_CLIENT_ID, UCenterConstant.GITHUB_REDIRECT_URL, state);
    }

    /**
     * GitHub登录：
     * 步骤二：获取code ==> 通过code得到token ==> 通过token拿去用户信息
     *
     * @param code  响应码
     * @param state 步骤一中的状态码
     * @return Result
     */
    @GetMapping("/callback")
    public String getTokenUserInfo(@RequestParam("code") String code,
                                   @RequestParam("state") String state,
                                   HttpServletRequest request) {
        // 从redis中获取state，并于回调的state进行比较
        String sessionId = request.getSession().getId();
        String stateRedis = redisTemplate.opsForValue().get(sessionId);
        // 比较state
        if (!state.equals(stateRedis)) {
            throw new CustomException(CustomExceptionEnum.STATE_NO_MATCH);
        }
        // 定义请求token模板url
        String tokenUrl = "https://github.com/login/oauth/access_token";
        Map<String, String> tokenMapParams = new HashMap<>(4);
        tokenMapParams.put("client_id", UCenterConstant.GITHUB_CLIENT_ID);
        tokenMapParams.put("client_secret", UCenterConstant.GITHUB_SECRET);
        tokenMapParams.put("code", code);
        tokenMapParams.put("state", state);
        // 发送post请求
        String tokenResult = HttpClientUtil.doPostMap(tokenUrl, tokenMapParams);
        String token = tokenResult.split("&")[0].split("=")[1];

        // 定义请求用户信息模板url
        String userUrl = "https://api.github.com/user";
        // 设置请求头
        Map<String, String> headers = new HashMap<>(1);
        headers.put("Authorization", "token " + token);
        // 获取用户信息
        String userResult = HttpClientUtil.doGetHeaders(userUrl, headers);
        // 转换userResult为Map
        HashMap userMap = JSON.parseObject(userResult, HashMap.class);

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
