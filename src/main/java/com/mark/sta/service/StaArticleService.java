package com.mark.sta.service;

import com.mark.sta.entity.StaArticle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 文章统计表 服务类
 * </p>
 *
 * @author mark
 * @since 2021-01-21
 */
public interface StaArticleService extends IService<StaArticle> {

    /**
     * 保存统计数据
     * @param date 日期
     */
    void saveArticleSta(String date);
}
