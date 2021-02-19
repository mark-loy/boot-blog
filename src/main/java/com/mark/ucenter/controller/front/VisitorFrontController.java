package com.mark.ucenter.controller.front;

import com.mark.common.entity.Result;
import com.mark.common.utils.JwtUtil;
import com.mark.ucenter.entity.vo.VisitorLoginVO;
import com.mark.ucenter.entity.vo.VisitorRegisterVO;
import com.mark.ucenter.service.VisitorService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/31 18:10
 */
@Api(value = "访客前台管理", tags = {"访客前台接口管理"})
@RestController
@RequestMapping("api/ucenter/front")
public class VisitorFrontController {

    @Resource
    private VisitorService visitorService;

    /**
     * 访客注册
     * @param visitorRegisterVO 访客注册表单
     * @return Result
     */
    @ApiOperation("访客注册")
    @PostMapping("/register")
    public Result visitorRegister(@ApiParam("访客注册表单") @Valid @RequestBody VisitorRegisterVO visitorRegisterVO) {
        if (visitorRegisterVO == null) {
            return Result.error().message("注册信息为空");
        }
        visitorService.toRegister(visitorRegisterVO);
        return Result.ok();
    }

    /**
     * 访客登录
     * @param visitorLoginVO 访客登录表单
     * @return Result
     */
    @ApiOperation(("访客登录"))
    @PostMapping("/login")
    public Result visitorLogin(@ApiParam("访客登录表单") @Valid @RequestBody VisitorLoginVO visitorLoginVO) {
        if (visitorLoginVO == null) {
            return Result.error().message("登录信息为空");
        }
        String token = visitorService.toLogin(visitorLoginVO);
        return Result.ok().data("token", token);
    }

    /**
     * 获取访客信息
     * @param httpServletRequest request请求
     * @return Result
     */
    @ApiOperation("获取访客信息")
    @GetMapping("/info")
    public Result getVisitorInfo(HttpServletRequest httpServletRequest) {
        // 从请求头中获取token
        String token = httpServletRequest.getHeader("X-Token");
        if (StringUtils.isEmpty(token)) {
            return Result.error().message("token为空");
        }

        // 解析token
        Claims claims = JwtUtil.parseToken(token);
        if (claims == null) {
            return Result.error().message("token解析错误");
        }
        String id = (String) claims.get("id");
        String nickname = (String) claims.get("nickname");
        String avatar = (String) claims.get("avatar");

        Map<String, Object> map = new HashMap<>(3);
        map.put("id", id);
        map.put("nickname", nickname);
        map.put("avatar", avatar);
        return Result.ok().data(map);
    }


}
