package com.mark.blog.util;

import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * blog配置常量类
 * @author 木可
 * @version 1.0
 * @date 2021/1/29 12:05
 */
@Component
public class BlogConstantUtil implements InitializingBean {

    @Value("${blog.article.hotConditionVal}")
    private Integer hotConditionVal;

    public static Integer HOT_CONDITION_VAL;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOT_CONDITION_VAL = hotConditionVal;
    }
}
