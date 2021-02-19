package com.mark.mds.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.base.vo.CommentMailRpcVO;
import com.mark.common.entity.Result;
import com.mark.mds.entity.Mail;
import com.mark.mds.entity.vo.SiteNoticeVO;
import com.mark.mds.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

/**
 * <p>
 * 邮件管理表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-02-06
 */
@Api(value = "邮件发送管理", tags = {"邮件发送服务接口"})
@Validated
@RestController
@RequestMapping("api/mds/mail")
public class MailController {

    @Resource
    private MailService mailService;

    /**
     * 发送邮件验证码
     * @param to 邮件接收人
     */
    @ApiOperation("发送邮件验证码")
    @PostMapping("/send/code")
    public Result sendSimpleMail(@ApiParam("邮件接收人") @RequestParam("email") @Email(message = "邮箱不合法") String to){

        mailService.sendCodeMail(to);

        return Result.ok();
    }

    /**
     * 发送评论回复通知邮件
     * @param commentMailVO 评论邮件实体
     * @return Result
     */
    @ApiOperation("发送评论回复通知邮件")
    @PostMapping("/send/comment")
    public Result sendCommentMail(@ApiParam("评论邮件实体") @RequestBody @Valid CommentMailRpcVO commentMailVO) {
        mailService.sendCommentMail(commentMailVO);
        return Result.ok();
    }

    /**
     * 发送站点通知邮件
     * @param siteNoticeVO 通知邮件实体
     * @return Result
     */
    @ApiOperation("发送站点通知邮件")
    @PostMapping("/send/notice")
    public Result sendSiteNoticeMail(@ApiParam("通知邮件实体") @RequestBody @Valid SiteNoticeVO siteNoticeVO) {
        mailService.sendNoticeMail(siteNoticeVO);
        return Result.ok();
    }

    /**
     * 获取邮件分页数据
     * @param current 当前页
     * @param limit 当页显示数
     * @return Result
     */
    @ApiOperation("获取邮件分页数据")
    @GetMapping("/{current}/{limit}")
    public Result getMailPage(@ApiParam("当前页") @PathVariable("current") Long current,
                              @ApiParam("当页显示数") @PathVariable("limit") Long limit) {
        // 验证分页参数
        if (current < 1 || limit < 1) {
            return Result.error().message("分页参数不合法");
        }
        Page<Mail> mailPage = new Page<>(current, limit);
        QueryWrapper<Mail> mailWrapper = new QueryWrapper<>();
        mailWrapper.orderByDesc("gmt_create");
        mailService.page(mailPage, mailWrapper);

        // 获取分页数据
        List<Mail> mails = mailPage.getRecords();
        long total = mailPage.getTotal();
        return Result.ok().data("mails", mails).data("total", total);
    }

    /**
     * 删除邮件
     * @param mid 邮件id
     * @return Result
     */
    @ApiOperation("删除邮件")
    @DeleteMapping("/{mid}")
    public Result deleteMail(@ApiParam("邮件id") @PathVariable("mid") String mid) {
        mailService.removeById(mid);
        return Result.ok();
    }
}

