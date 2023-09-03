package com.linmour.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //开启Security功能
@EnableGlobalMethodSecurity(prePostEnabled = true) //启动方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    //配置密码加密器
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}

    //配置哪些请求不拦截
    //TODO 将需要Feign的方法前缀都用上api，得到api/select/user/{user_id}这样的路径不受限制
    // 由于api路径是由服务模块自己去调用的，所以gateway不用做路径请求的处理
    @Override
    public void configure(WebSecurity web) throws Exception {
        System.out.println("*********************************************");
        web.ignoring().antMatchers("/login","/doc.html#/**","/swagger-resources");
    }

 
    //配置安全策略
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("读取配置*****************WHITE");
        http.authorizeRequests()
                .antMatchers("/websocket/**").permitAll()
                // 除上面外的所有请求全部需要认证才能访问
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }
}