package com.mark.mds.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mark.base.vo.CommentMailRpcVO;
import com.mark.mds.entity.Mail;
import com.mark.mds.entity.vo.SiteNoticeVO;

/**
 * <p>
 * 邮件管理表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-02-06
 */
public interface MailService extends IService<Mail> {

    /**
     * 发送验证码邮件
     * @param to 邮件接收人
     */
    void sendCodeMail(String to);

    /**
     * 发送评论回复通知邮件
     * @param commentMailVO 评论邮件实体
     */
    void sendCommentMail(CommentMailRpcVO commentMailVO);

    /**
     * 发送站点通知邮件
     * @param siteNoticeVO 通知邮件实体
     */
    void sendNoticeMail(SiteNoticeVO siteNoticeVO);
}
