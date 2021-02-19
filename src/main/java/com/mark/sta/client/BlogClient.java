package com.mark.sta.client;

import com.mark.common.entity.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/1/25 10:25
 */
public interface BlogClient {

    /**
     * RPC服务消费者
     *    生成文章的每日统计数据
     * @param date 日期
     * @return Result
     */
    @PostMapping("api/blog/article/rpc/generate/{date}")
    Result generateArticleStaData(@PathVariable("date") String date);
}
