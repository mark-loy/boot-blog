package com.mark.blog.controller;


import com.mark.blog.service.CommentService;
import com.mark.common.entity.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Api(value = "评论管理", tags = {"评论管理服务接口"})
@RestController
@RequestMapping("api/blog/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 获取评论信息
     * @param articleId 文章id
     * @return Result

     */
    @ApiOperation("获取评论分页信息")
    @GetMapping("/{aid}")
    public Result getCommentPage(@ApiParam("文章id") @PathVariable("aid") String articleId) {
        // 获取评论分页信息
        Map<String, Object> map = commentService.getCommentPage(articleId);

        return Result.ok().data(map);
    }

    /**
     * 修改评论状态
     * @param commentId 评论id
     * @param isDisabled 是否已禁用 false：未禁用 true：已禁用
     * @return Result
     */
    @ApiOperation("修改评论状态")
    @PutMapping("/{cid}/{isDisabled}")
    public Result updateCommentStatus(@ApiParam("评论id") @PathVariable("cid") String commentId,
                                      @ApiParam(value = "是否已禁用") @PathVariable("isDisabled") Boolean isDisabled) {
        commentService.updateCommentStatus(commentId, isDisabled);
        return Result.ok();
    }

    /**
     * 根据id删除评论
     * @param commentId 评论id
     * @return Result
     */
    @ApiOperation("根据id删除评论")
    @DeleteMapping("/{cid}")
    public Result deleteComment(@ApiParam("评论id") @PathVariable("cid") String commentId) {
        commentService.deleteComment(commentId);
        return Result.ok();
    }

    /**
     * 批量删除评论
     * @param commentIds 评论id集合
     * @return Result
     */
    @ApiOperation("批量删除评论")
    @DeleteMapping("/")
    public Result deleteBatchComment(@ApiParam("评论id集合") @RequestParam("commentIds") List<String> commentIds) {
        commentService.deleteBatchComment(commentIds);
        return Result.ok();
    }

}

