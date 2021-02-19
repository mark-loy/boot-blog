package com.mark.blog.helper;

import com.mark.blog.entity.Comment;
import com.mark.blog.entity.vo.CommentResponseVO;
import com.mark.blog.entity.vo.front.CommentResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/24 11:10
 */
@Component
public class CommentServiceHelper {

    /**
     * 后台==》递归遍历评论数据
     *
     * @param comments 当前文章的评论数据
     * @param parentId 评论的父级id
     * @return List<CommentResponseVO>
     */
    public List<CommentResponseVO> getCommentResponse(List<Comment> comments, String parentId) {
        // 最终封装的结果集合
        List<CommentResponseVO> commentResponse = new ArrayList<>();
        // 遍历
        for (Comment comment : comments) {
            // 判断评论类型
            if (parentId.equals(comment.getParentId())) {
                // 构建返回的评论对象
                CommentResponseVO commentResponseVO = new CommentResponseVO();
                BeanUtils.copyProperties(comment, commentResponseVO);
                // 设置二级评论
                commentResponseVO.setChildren(getCommentResponse(comments, comment.getId()));

                // 将返回的评论对象加入集合
                commentResponse.add(commentResponseVO);
            }
        }
        return commentResponse;
    }

    /**
     * 前台==》递归遍历评论数据
     *
     * @param comments 当前文章的评论数据
     * @param parentId 评论的父级id
     * @return List<CommentResultVOVO>
     */
    public List<CommentResultVO> getCommentResult(List<Comment> comments, String parentId) {
        // 最终封装的结果集合
        List<CommentResultVO> commentResult = new ArrayList<>();
        // 遍历
        for (Comment comment : comments) {
            // 判断评论禁用状态
            if (comment.getIsDisabled()) {
                // true：已禁用 ==> 进入下一次循环
                continue;
            }
            // 判断评论类型
            if (parentId.equals(comment.getParentId())) {
                // 构建返回的评论对象
                CommentResultVO commentResultVO = new CommentResultVO();
                BeanUtils.copyProperties(comment, commentResultVO);
                // 判断是否为一级评论
                if ("0".equals(parentId)) {
                    commentResultVO.setAcceptName("");
                    commentResultVO.setAcceptId("");
                }

                // 设置二级评论
                commentResultVO.setChildren(getCommentResult(comments, comment.getId()));

                // 将返回的评论对象加入集合
                commentResult.add(commentResultVO);
            }
        }
        return commentResult;
    }

    /**
     * 计算评论总数
     *
     * @param commentResult 评论
     * @return Integer
     */
    public Integer getCommentTotal(List<CommentResultVO> commentResult) {
        int total = 0;
        if (commentResult == null || commentResult.size() == 0) {
            return 0;
        }
        // 遍历一级评论
        for (CommentResultVO commentResultVO : commentResult) {
            // 评论数 +1
            total += 1;
            // 获取二级评论
            List<CommentResultVO> children = commentResultVO.getChildren();
            // 判断非空
            if (children == null || children.size() == 0) {
                total += 0;
                continue;
            }
            // 获取二级评论集合长度
            total += children.size();
        }
        return total;
    }
}
