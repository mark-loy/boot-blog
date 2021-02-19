package com.mark.ucenter.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/2 12:55
 */
@Component
public class UCenterConstant implements InitializingBean {

    @Value("${third.login.github.clientId}")
    private String githubClientId;

    @Value("${third.login.github.secret}")
    private String githubSecret;

    @Value("${third.login.github.redirectUrl}")
    private String githubRedirectUrl;

    @Value("${third.login.gitee.clientId}")
    private String giteeClientId;

    @Value("${third.login.gitee.secret}")
    private String giteeSecret;

    @Value("${third.login.gitee.redirectUrl}")
    private String giteeRedirectUrl;

    @Value("${site.siteUrl}")
    private String siteUrl;

    public static String GITHUB_CLIENT_ID;

    public static String GITHUB_SECRET;

    public static String GITHUB_REDIRECT_URL;

    public static String GITEE_CLIENT_ID;

    public static String GITEE_SECRET;

    public static String GITEE_REDIRECT_URL;

    public static String SITE_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        GITHUB_CLIENT_ID = githubClientId;
        GITHUB_SECRET = githubSecret;
        GITHUB_REDIRECT_URL = githubRedirectUrl;
        GITEE_CLIENT_ID = giteeClientId;
        GITEE_SECRET = giteeSecret;
        GITEE_REDIRECT_URL = giteeRedirectUrl;
        SITE_URL = siteUrl;
    }
}
