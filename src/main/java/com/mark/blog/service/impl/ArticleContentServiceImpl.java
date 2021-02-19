package com.mark.blog.service.impl;

import com.mark.blog.entity.ArticleContent;
import com.mark.blog.mapper.ArticleContentMapper;
import com.mark.blog.service.ArticleContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文章内容表 服务实现类
 * </p>
 *
 * @author mark
 * @since 2021-01-22
 */
@Service
public class ArticleContentServiceImpl extends ServiceImpl<ArticleContentMapper, ArticleContent> implements ArticleContentService {

}
