package com.repository.config.secuity;

import com.repository.entity.UsersEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by Finderlo on 11/17/2016.
 */
public class SecUserDetails implements UserDetails {


    //    public MyUserDetails(SystemUser user, List<UserRole> roles){
//        super(user);
//        this.roles = roles;
//    }
    UsersEntity usersEntity;

    public SecUserDetails(UsersEntity usersEntity) {
        this.usersEntity = usersEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        if(roles == null || roles.size() <1){
//            return AuthorityUtils.commaSeparatedStringToAuthorityList("");
//        }
//        StringBuilder commaBuilder = new StringBuilder();
//        for(UserRole role : roles){
//            commaBuilder.append(role.getRole()).append(",");
//        }
//        String authorities = commaBuilder.substring(0,commaBuilder.length()-1);
//        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(usersEntity.getUsersIdentity().toString());
    }

    @Override
    public String getPassword() {
        return usersEntity.getUsersPassword();
    }

    @Override
    public String getUsername() {
        return usersEntity.getUsersId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}