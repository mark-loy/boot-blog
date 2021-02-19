package com.mark.blog.client.impl;

import com.mark.base.vo.VisitorRpcVO;
import com.mark.blog.client.UCenterClient;
import com.mark.ucenter.entity.Visitor;
import com.mark.ucenter.service.VisitorService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/18 14:23
 */
@Component
public class UCenterClientImpl implements UCenterClient {

    @Resource
    private VisitorService visitorService;

    @Override
    public VisitorRpcVO getVisitorId(String visitorId) {
        Visitor visitor = visitorService.getById(visitorId);
        VisitorRpcVO visitorRpcVO = new VisitorRpcVO();
        BeanUtils.copyProperties(visitor, visitorRpcVO);
        return visitorRpcVO;
    }

}
