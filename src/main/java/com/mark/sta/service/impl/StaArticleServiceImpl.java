package com.mark.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mark.base.enums.CustomExceptionEnum;
import com.mark.base.exception.CustomException;
import com.mark.common.entity.Result;
import com.mark.sta.client.BlogClient;
import com.mark.sta.entity.StaArticle;
import com.mark.sta.mapper.StaArticleMapper;
import com.mark.sta.service.StaArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 文章统计表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
@Service
public class StaArticleServiceImpl extends ServiceImpl<StaArticleMapper, StaArticle> implements StaArticleService {

    @Resource
    private BlogClient blogClient;

    /**
     * 远程调用文章服务，生成统计数据，保存统计数据
     * @param date 日期
     */
    @Override
    public void saveArticleSta(String date) {
        // 远程调用文章服务,获取文章的统计数据
        Result articleStaDataResult = blogClient.generateArticleStaData(date);
        // 判断调用结果
        if (!articleStaDataResult.getSuccess()) {
            throw new CustomException(CustomExceptionEnum.REQUEST_SERVICE_FAIL);
        }
        // 获取统计数据
        Integer viewCount = (Integer) articleStaDataResult.getData().get("viewNum");
        Integer commentNum = (Integer) articleStaDataResult.getData().get("commentNum");
        Integer likeNum = (Integer) articleStaDataResult.getData().get("likeNum");
        Integer publishNum = (Integer) articleStaDataResult.getData().get("publishNum");

        // 先删除统计表中该日期的统计数据，即保证每天只有一条统计数据
        QueryWrapper<StaArticle> staArticleWrapper = new QueryWrapper<>();
        staArticleWrapper.eq("sta_date", date);
        baseMapper.delete(staArticleWrapper);

        // 再保存当前生成的统计数据
        StaArticle staArticle = new StaArticle();
        staArticle
                // 设置统计日期
                .setStaDate(date)
                // 设置浏览数
                .setViewNum(viewCount)
                // 设置评论数
                .setCommentNum(commentNum)
                // 设置文章发表数
                .setPublishNum(publishNum)
                // 设置点赞数
                .setLikeNum(likeNum);
        // 执行保存
        baseMapper.insert(staArticle);
    }
}
