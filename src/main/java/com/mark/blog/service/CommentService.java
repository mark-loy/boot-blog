package com.mark.blog.service;

import com.mark.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
public interface CommentService extends IService<Comment> {

    /**
     * 查询评论数据
     * @param articleId 文章id
     * @return Map<String, Object>
     */
    Map<String, Object> getCommentPage(String articleId);

    /**
     * 修改评论状态
     * @param commentId 评论id
     * @param status 状态值
     */
    void updateCommentStatus(String commentId, Boolean status);

    /**
     * 删除评论
     * @param commentId 评论id
     */
    void deleteComment(String commentId);

    /**
     * 批量删除评论
     * @param commentIds 评论id集合
     */
    void deleteBatchComment(List<String> commentIds);
}
