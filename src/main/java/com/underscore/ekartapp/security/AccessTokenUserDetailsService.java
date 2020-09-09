/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.security;

//import com.innovature.c2c.api.entity.User;
//import com.innovature.c2c.api.repository.UserRepository;
//import com.innovature.c2c.api.util.InvalidTokenException;
//import com.innovature.c2c.api.util.TokenExpiredException;
//import com.innovature.c2c.api.util.TokenGenerator;
//import com.innovature.c2c.api.util.TokenGenerator.Status;
import com.underscore.ekartapp.entity.User;
import com.underscore.ekartapp.exception.InvalidTokenException;
import com.underscore.ekartapp.exception.TokenExpiredException;
import com.underscore.ekartapp.repository.UserRepository;
import com.underscore.ekartapp.security.TokenGenerator.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *
 * @author nirmal
 */
public class AccessTokenUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    public static final String PURPOSE_ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String PURPOSE_REFRESH_TOKEN = "REFRESH_TOKEN";

    @Autowired
    private TokenGenerator tokenGenerator;
        
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {
        if (!PURPOSE_ACCESS_TOKEN.equals(token.getCredentials())) {
            throw new UsernameNotFoundException("Invalid credentials");
        }

        final Status status;
        User user =null;
        try {
            status = tokenGenerator.verify(PURPOSE_ACCESS_TOKEN, token.getPrincipal().toString());
            user = userRepository.findById(Integer.parseInt(status.data)).orElse(null);
        if(user==null){
            throw new UsernameNotFoundException("Invalid credentials");
        }
        if(User.Status.ACTIVE.value!=user.getStatus()){
            throw new UsernameNotFoundException("Invalid credentials");
        }
        
        } catch (InvalidTokenException e) {
            throw new UsernameNotFoundException("Invalid access token", e);
        } catch (TokenExpiredException e) {
            throw new UsernameNotFoundException("Access token expired", e);
        }

        return new AccessTokenUserDetails(user.getId(), (byte) user.getRole());
    }
}
