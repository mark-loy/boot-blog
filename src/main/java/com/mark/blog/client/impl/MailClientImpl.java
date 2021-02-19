package com.mark.blog.client.impl;


import com.mark.base.vo.CommentMailRpcVO;
import com.mark.blog.client.MailClient;
import com.mark.common.entity.Result;
import com.mark.mds.service.MailService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/5 10:50
 */
@Component
public class MailClientImpl implements MailClient {

    @Resource
    private MailService mailService;

    @Override
    public Result sendCommentMail(CommentMailRpcVO commentMailVO) {
        mailService.sendCommentMail(commentMailVO);
        return Result.ok();
    }

}
