package com.mark.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.blog.entity.Comment;
import com.mark.blog.entity.vo.CommentResponseVO;
import com.mark.blog.helper.CommentServiceHelper;
import com.mark.blog.mapper.CommentMapper;
import com.mark.blog.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentServiceHelper commentServiceHelper;

    /**
     * 分页查询评论数据
     *
     * @param articleId 文章id
     * @return Map<String, Object>
     */
    @Override
    public Map<String, Object> getCommentPage(String articleId) {
        // 构建条件查询对象
        QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
        // 设置文章id
        commentWrapper.eq("article_id", articleId);
        // 执行分页
        List<Comment> comments = baseMapper.selectList(commentWrapper);
        // 封装评论数据
        List<CommentResponseVO> commentResponse = commentServiceHelper.getCommentResponse(comments, "0");

        HashMap<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("comments", commentResponse);
        return resultMap;
    }

    /**
     * 修改评论状态，一级评论需级联修改状态
     *
     * @param commentId 评论id
     * @param status    状态值
     */
    @Override
    public void updateCommentStatus(String commentId, Boolean status) {
        // 先查询评论信息
        Comment comment = baseMapper.selectById(commentId);
        // 判断该评论是否为一级评论
        if ("0".equals(comment.getParentId())) {
            // 是一级评论，则需级联修改二级评论状态
            QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
            commentWrapper.eq("parent_id", commentId);
            // 获取所有的二级评论
            List<Comment> commentsChildTemp = baseMapper.selectList(commentWrapper);
            // 修改所有二级评论状态
            List<Comment> commentsChild = commentsChildTemp.stream().map(commentChild -> commentChild.setIsDisabled(status)).collect(Collectors.toList());

            // 执行修改
            this.updateBatchById(commentsChild);
        }

        // 该评论不是一级评论，则直接修改状态
        comment.setIsDisabled(status);
        baseMapper.updateById(comment);
    }

    /**
     * 删除评论或关联删除子评论
     * @param commentId 评论id
     */
    @Override
    public void deleteComment(String commentId) {
        // 先查询评论信息
        Comment comment = baseMapper.selectById(commentId);
        // 判断该评论是否为一级评论
        if ("0".equals(comment.getParentId())) {
            // 是一级评论，则需级联删除二级评论
            QueryWrapper<Comment> commentWrapper = new QueryWrapper<>();
            commentWrapper.eq("parent_id", commentId);
            // 获取所有的二级评论
            List<Comment> commentsChildTemp = baseMapper.selectList(commentWrapper);
            // 获取所有二级评论id
            List<String> commentsChild = commentsChildTemp.stream().map(Comment::getId).collect(Collectors.toList());

            // 执行删除
            baseMapper.deleteBatchIds(commentsChild);
        }

        // 该评论不是一级评论，直接删除
        baseMapper.deleteById(commentId);
    }

    /**
     * 批量删除评论或删除关联子评论
     * @param commentIds 评论id集合
     */
    @Override
    public void deleteBatchComment(List<String> commentIds) {
        commentIds.forEach(this::deleteComment);
    }
}
