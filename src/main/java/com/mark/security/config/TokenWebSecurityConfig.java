package com.mark.security.config;

import com.mark.security.filter.TokenAuthenticationFilter;
import com.mark.security.filter.TokenLoginFilter;
import com.mark.security.security.DefaultPasswordEncoder;
import com.mark.security.security.TokenLogoutHandler;
import com.mark.security.security.TokenManager;
import com.mark.security.security.UnauthorizedEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * <p>
 * Security配置类
 * </p>
 *
 * @author qy
 * @since 2019-11-18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenManager tokenManager;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final RedisTemplate redisTemplate;

    @Autowired
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    /**
     * HttpSecurity配置设置
     *
     * @param http HttpSecurity
     * @throws Exception 异常
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new UnauthorizedEntryPoint())
                .and()
                .cors()
                .and()
                .csrf().disable().authorizeRequests().anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/admin/acl/logout").addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate))
                .and()
                // 注意，这里添加的过滤器需要注意顺序，应该将登录的过滤器后添加
                // 授权过滤器
                .addFilter(new TokenAuthenticationFilter(authenticationManager(), tokenManager, redisTemplate))
                // 认证（登录）过滤器
                .addFilter(new TokenLoginFilter(authenticationManager(), tokenManager, redisTemplate))
                .httpBasic();
    }

    /**
     * 密码处理
     *
     * @param auth AuthenticationManagerBuilder
     * @throws Exception 异常
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    /**
     * 配置哪些请求不拦截
     *
     * @param web WebSecurity
     */
    @Override
    public void configure(WebSecurity web) {
        /*web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**",
                "/css/**", "/js/**", "/img/**", "/fonts/**", "/favicon.ico"
        );*/
        web.ignoring().antMatchers("/api/**",
                "/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**"
        );
    }
}