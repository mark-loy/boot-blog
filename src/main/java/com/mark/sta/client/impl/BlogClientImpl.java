package com.mark.sta.client.impl;

import com.mark.blog.service.ArticleService;
import com.mark.common.entity.Result;
import com.mark.sta.client.BlogClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/25 10:25
 */
@Component
public class BlogClientImpl implements BlogClient {

    @Resource
    private ArticleService articleService;

    @Override
    public Result generateArticleStaData(String date) {
        Map<String, Object> resultMap = articleService.generateStaData(date);
        return Result.ok().data(resultMap);
    }
}
