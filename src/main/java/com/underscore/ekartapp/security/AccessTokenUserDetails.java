/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author nirmal
 */
public class AccessTokenUserDetails implements UserDetails {


    public int userId;
    private String userRole;


    public AccessTokenUserDetails(int userId, short role) {
        this.userId = userId;
        switch (role) {
            case 1:
                userRole = "BUYER";
                break;
            case 2:
                userRole = "SELLER";
                break;
            case 3:
                userRole = "ADMIN";
                break;
            default:
                break;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String ROLE_PREFIX = "ROLE_";
        List<GrantedAuthority> ROLES = new ArrayList<>();
        ROLES.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole));
        return ROLES;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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

//
//    private static final List<GrantedAuthority> ROLES = AuthorityUtils.createAuthorityList("ROLE_USER");
//
//    public final int userId;
//
//    public AccessTokenUserDetails(int userId) {
//        this.userId = userId;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return ROLES;
//    }
//
//    @Override
//    public String getPassword() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
