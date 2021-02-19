package com.mark.site.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mark.common.entity.Result;
import com.mark.site.entity.Guestbook;
import com.mark.site.entity.vo.GuestbookFormVO;
import com.mark.site.service.GuestbookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 站点留言表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-02-06
 */
@Api(value = "留言管理", tags = {"留言接口管理"})
@RestController
@RequestMapping("api/site/guestbook")
public class GuestbookController {

    @Resource
    private GuestbookService bookService;

    /**
     * 获取留言分页信息
     * @param current 当前页
     * @param limit 当页显示数
     * @return Result
     */
    @ApiOperation("获取留言分页信息")
    @GetMapping("/{current}/{limit}")
    public Result getGuestbookPage(@ApiParam("当前页") @PathVariable("current") Long current,
                                   @ApiParam("当页显示数") @PathVariable("limit") Long limit) {
        // 分页条件判断
        if (current < 1 || limit < 1) {
            return Result.error().message("分页条件不合法");
        }
        Page<Guestbook> guestbookPage = new Page<>(current, limit);
        QueryWrapper<Guestbook> bookWrapper = new QueryWrapper<>();
        bookWrapper.orderByDesc("gmt_create");
        bookService.page(guestbookPage, bookWrapper);

        // 获取分页数据
        List<Guestbook> guestbooks = guestbookPage.getRecords();
        long total = guestbookPage.getTotal();
        return Result.ok().data("guestbooks", guestbooks).data("total", total);
    }

    /**
     * 保存留言
     * @param guestbookFormVO 留言表单
     * @return Result
     */
    @ApiOperation("保存留言")
    @PostMapping("/")
    public Result saveGuestbook(@ApiParam("留言表单") @RequestBody @Valid GuestbookFormVO guestbookFormVO) {
        Guestbook guestbook = new Guestbook();
        BeanUtils.copyProperties(guestbookFormVO, guestbook);
        bookService.save(guestbook);
        return Result.ok();
    }

    /**
     * 删除留言
     * @param gid 留言id
     * @return Result
     */
    @ApiOperation("删除留言")
    @DeleteMapping("/{gid}")
    public Result deleteGuestbook(@ApiParam("留言id")@PathVariable("gid") String gid) {
        bookService.removeById(gid);
        return Result.ok();
    }
}

