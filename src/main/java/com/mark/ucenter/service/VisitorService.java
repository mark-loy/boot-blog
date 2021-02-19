package com.mark.ucenter.service;

import com.mark.ucenter.entity.Visitor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mark.ucenter.entity.vo.VisitorLoginVO;
import com.mark.ucenter.entity.vo.VisitorQueryVO;
import com.mark.ucenter.entity.vo.VisitorRegisterVO;

import java.util.Map;

/**
 * <p>
 * 访客表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
public interface VisitorService extends IService<Visitor> {

    /**
     * 多条件组合分页查询访客信息
     * @param current 当前页
     * @param limit 当页显示数
     * @param visitorQueryVO 访客查询对象
     * @return Map<String, Object>
     */
    Map<String, Object> getVisitorQueryPage(Long current, Long limit, VisitorQueryVO visitorQueryVO);

    /**
     * 访客注册
     * @param visitorRegisterVO 访客注册表单
     */
    void toRegister(VisitorRegisterVO visitorRegisterVO);

    /**
     * 访客登录
     * @param visitorLoginVO 访客登录表单
     * @return String
     */
    String toLogin(VisitorLoginVO visitorLoginVO);
}
