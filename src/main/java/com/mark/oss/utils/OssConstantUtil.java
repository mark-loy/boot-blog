package com.mark.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * oss配置参数
 * @author 木可
 * @version 1.0
 * @date 2021/1/26 16:28
 */
@Component
public class OssConstantUtil implements InitializingBean {

    @Value("${aliyun.accessKeyId}")
    private String key;

    @Value("${aliyun.accessKeySecret}")
    private String secret;

    @Value("${aliyun.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.filePatten}")
    private String filePatten;

    public static String KEY;

    public static String SECRET;

    public static String END_POINT;

    public static String BUCKET_NAME;

    public static String FILE_PATTEN;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY = key;
        SECRET = secret;
        END_POINT = endpoint;
        BUCKET_NAME = bucketName;
        FILE_PATTEN = filePatten;
    }
}
