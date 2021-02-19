package com.mark.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.base.enums.CustomExceptionEnum;
import com.mark.base.exception.CustomException;
import com.mark.common.utils.JwtUtil;
import com.mark.common.utils.MD5;
import com.mark.ucenter.entity.Visitor;
import com.mark.ucenter.entity.vo.VisitorLoginVO;
import com.mark.ucenter.entity.vo.VisitorQueryVO;
import com.mark.ucenter.entity.vo.VisitorRegisterVO;
import com.mark.ucenter.mapper.VisitorMapper;
import com.mark.ucenter.service.VisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 访客表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 多条件组合分页查询访客数据
     * @param current 当前页
     * @param limit 当页显示数
     * @param visitorQueryVO 访客查询对象
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getVisitorQueryPage(Long current, Long limit, VisitorQueryVO visitorQueryVO) {
        // 构建分页对象
        Page<Visitor> visitorPage = new Page<>(current, limit);
        // 构建条件查询对象
        QueryWrapper<Visitor> visitorWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(visitorQueryVO.getNickname())) {
            visitorWrapper.like("nickname", visitorQueryVO.getNickname());
        }
        if (!StringUtils.isEmpty(visitorQueryVO.getIsDisabled())) {
            visitorWrapper.eq("is_disabled", visitorQueryVO.getIsDisabled());
        }
        // 执行分页
        baseMapper.selectPage(visitorPage, visitorWrapper);

        // 获取分页数据
        List<Visitor> visitors = visitorPage.getRecords();
        long total = visitorPage.getTotal();

        Map<String, Object> map = new HashMap<>(2);
        map.put("visitors", visitors);
        map.put("total", total);
        return map;
    }

    /**
     * 访客注册
     * @param visitorVO 访客注册表单
     */
    @Override
    public void toRegister(VisitorRegisterVO visitorVO) {
        // 获取注册参数
        String nickname = visitorVO.getNickname();
        String password = visitorVO.getPassword();
        String email = visitorVO.getEmail();
        String code = visitorVO.getCode();

        // 把email作为key，从redis中取出code
        String codeRedis = redisTemplate.opsForValue().get(email);
        if (StringUtils.isEmpty(codeRedis)) {
            throw new CustomException(CustomExceptionEnum.CODE_NOT_EXIST);
        }
        // 将验证码转为小写，再进行比较
        if (!code.toLowerCase().equals(codeRedis.toLowerCase())) {
            throw new CustomException(CustomExceptionEnum.CODE_NO_CORRECT);
        }
        // 通过email查询库中是否存在
        QueryWrapper<Visitor> visitorWrapper = new QueryWrapper<>();
        visitorWrapper.eq("email", email);
        Integer integer = baseMapper.selectCount(visitorWrapper);
        if (integer > 0) {
            throw new CustomException(CustomExceptionEnum.VISITOR_EXIST);
        }
        // 新增访客
        Visitor registerVisitor = new Visitor();
        // 密码进行MD5加密
        String enPassword = MD5.encrypt(password);
        // 设置访客属性
        registerVisitor
                .setEmail(email)
                .setNickname(nickname)
                .setPassword(enPassword);
        // 执行保存
        baseMapper.insert(registerVisitor);
    }

    /**
     * 访客登录
     * @param visitorLoginVO 访客登录表单
     * @return String
     */
    @Override
    public String toLogin(VisitorLoginVO visitorLoginVO) {
        // 判断非空
        String email = visitorLoginVO.getEmail();
        String password = visitorLoginVO.getPassword();

        // 通过email,查询访客信息
        QueryWrapper<Visitor> visitorWrapper = new QueryWrapper<>();
        visitorWrapper.eq("email", email);
        Visitor visitor = baseMapper.selectOne(visitorWrapper);
        if (visitor == null) {
            throw new CustomException(CustomExceptionEnum.VISITOR_NO_EXIST);
        }
        // 对输入密码进行加密
        String enPassword = MD5.encrypt(password);
        // 判断密码
        if (!enPassword.equals(visitor.getPassword())) {
            throw new CustomException(CustomExceptionEnum.PASSWORD_ERROR);
        }
        // 判断账户状态
        if (visitor.getIsDisabled()) {
            // 该账户已封禁
            throw new CustomException(CustomExceptionEnum.VISITOR_BAN);
        }
        // 密码正确，生成token
        Map<String, Object> tokenMap = new HashMap<>(4);
        tokenMap.put("id", visitor.getId());
        tokenMap.put("nickname", visitor.getNickname());
        tokenMap.put("email", email);
        tokenMap.put("avatar", visitor.getAvatar());

        return JwtUtil.getJwtToken(tokenMap);
    }
}
