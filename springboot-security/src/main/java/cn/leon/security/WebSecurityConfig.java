package cn.leon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //标注authorizeRequests()和 anyRequest().authenticated()就会要求所有进入应用的
                //HTTP请求都要进行认证
                .authorizeRequests()
                .anyRequest()
                .authenticated();

    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 自定义校验
        builder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
        // 基于内存校验
//        builder.inMemoryAuthentication().withUser("aaa").password("aaa");
//        builder.inMemoryAuthentication().withUser("bbb").password("bbb");
//        builder.inMemoryAuthentication().withUser("ccc").password("ccc");
//
//        builder.inMemoryAuthentication()
//                .withUser("itguang").password("123456").roles("USER").and()
//                .withUser("admin").password("123456").roles("ADMIN");
    }

    /**
     * 设置密码加密
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
