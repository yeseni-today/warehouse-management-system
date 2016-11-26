package com.repository.config.secuity;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by Finderlo on 11/17/2016.
 */

@Component
public class SecAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SecUsersDetailService userService;

    private static final Logger logger = Logger.getLogger(SecAuthenticationProvider.class);

    /**
     * 自定义验证方式
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        logger.info("authenticate: " + username + ":" + password);
        SecUserDetails user = (SecUserDetails) userService.loadUserByUsername(username);
        if (user == null) {
            System.out.println("登陆失败，找不到用户");
            throw new BadCredentialsException("Username not found.");
        }

        //加密过程在这里体现
        if (!password.trim().equals(user.getPassword().trim())) {
            System.out.println("input:" + password.trim());
            System.out.println("right:" + user.getPassword().trim());
            System.out.println("Wrong password.");
            throw new BadCredentialsException("Wrong password.");
        }

        System.out.println("登陆成功");

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}