package com.repository.config.secuity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by Finderlo on 2016/11/8.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConifg extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    SecUsersDetailService usersDetailService;
    @Autowired
    SecAuthenticationProvider provider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/signin")
                .successForwardUrl("/query");
        http
                .authorizeRequests()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/generalError").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic()
                .and().csrf().disable();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth
////                .jdbcAuthentication()
////                .dataSource(dataSource)
////                .usersByUsernameQuery(
////                        "select users_ID,users_password,true " +
////                                "from users where users_ID=?"
////                )
////                .authoritiesByUsernameQuery(
////                        "select users_ID,'ROLE_USER' from users where users_ID=?"
////                );
//
//    }


    //
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
    }

    //全局设置
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //将验证过程交给自定义验证工具
        auth.authenticationProvider(provider);
    }

}
