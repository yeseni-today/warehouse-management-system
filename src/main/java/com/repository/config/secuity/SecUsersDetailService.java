package com.repository.config.secuity;

import com.repository.dao.UsersDao;
import com.repository.entity.UsersEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Finderlo on 11/17/2016.
 */

@Component
public class SecUsersDetailService implements UserDetailsService {
    @Autowired
    private UsersDao usersDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UsersEntity user;
        try {
            user = usersDao.findById(userName);
        } catch (Exception e) {
            throw new UsernameNotFoundException("user select fail");
        }
        if (user == null) {
            throw new UsernameNotFoundException("no user found");
        } else {
            try {
                return new SecUserDetails(user);
            } catch (Exception e) {
                throw new UsernameNotFoundException("user role select fail");
            }
        }
    }
}