package com.linmour.account.security;

import com.linmour.account.filter.AuthorizeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    AccessDeniedHandler accessDeniedHandler;
    @Resource
    private AuthorizeFilter authorizeFilter;

    @Resource
    private AuthorizeFilter authorizeFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                //关闭csrf
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/account/merchant/login").anonymous()
                .antMatchers("/account/merchant/a").permitAll()
                // 登陆后才能访问
                .antMatchers("/account/merchant/logout").authenticated()
                // 除上面外的所有请求全部需要认证才能访问
                .anyRequest().authenticated()
                .and()
                .logout()
                .disable()
                //允许跨域
                .cors()
                .and()
                // 添加自定义过滤器
<<<<<<< HEAD
                .addFilterBefore(authorizeFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
=======
                .addFilterBefore(authorizeFilter, UsernamePasswordAuthenticationFilter.class);
>>>>>>> 994951962ca7a3aeb2f38814752d741052da3c04
    }


}
