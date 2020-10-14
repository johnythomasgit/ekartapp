/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service.impl;

import com.underscore.ekartapp.config.SecurityConfig;
import com.underscore.ekartapp.entity.User;
import com.underscore.ekartapp.entity.UserAddress;
import com.underscore.ekartapp.exception.BadRequestException;
import com.underscore.ekartapp.exception.InvalidTokenException;
import com.underscore.ekartapp.exception.NotFoundException;
import com.underscore.ekartapp.exception.TokenExpiredException;
import com.underscore.ekartapp.form.LoginForm;
import com.underscore.ekartapp.form.UserForm;
import com.underscore.ekartapp.form.UserUpdateForm;
import com.underscore.ekartapp.repository.UserAddressRepository;
import com.underscore.ekartapp.repository.UserRepository;
import static com.underscore.ekartapp.security.AccessTokenUserDetailsService.PURPOSE_ACCESS_TOKEN;
import static com.underscore.ekartapp.security.AccessTokenUserDetailsService.PURPOSE_REFRESH_TOKEN;
import com.underscore.ekartapp.security.TokenGenerator;
import com.underscore.ekartapp.security.TokenGenerator.Status;
import com.underscore.ekartapp.security.TokenGenerator.Token;
import com.underscore.ekartapp.service.UserService;
import com.underscore.ekartapp.view.LoginView;
import com.underscore.ekartapp.view.UserView;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author johnythomas
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserAddressRepository userAddressRepository;
    
    @Autowired
    private TokenGenerator tokenGenerator;
    
    @Autowired
    private SecurityConfig securityConfig;

    @Override
    public UserView add(UserForm form) {
       
        User user = userRepository.findByEmail(form.getEmail()).orElse(null);

        if (user != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "user.already.exists");
        }
        String password = passwordEncoder.encode(form.getPassword());
        user = userRepository.save(new User(form, password));
        UserAddress userAddress = userAddressRepository.save(new UserAddress(form,user));
        user = userRepository.findById(user.getId()).orElse(user);
        user.setUserAddressList((List<UserAddress>) userAddressRepository.findByUserId(user));
        if(user.getUserAddressList()==null){
            System.out.println("\n\n Nulle ........\n\n");
        }
//        if (form.getPassword() == null) {
//            String id = String.format("%010d", user.getId());
//            Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, securityConfig.getAccessTokenExpiry());
//            Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + user.getPassword(), securityConfig.getRefreshTokenExpiry());
//            return new LoginView(user, accessToken, refreshToken);
//        }
        return new UserView(user);
    }

    @Override
    public UserView currentUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UserView> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LoginView login(LoginForm form, Errors errors) throws BadRequestException {
            if (errors.hasErrors()) {
            throw new BadRequestException("invalid.credentials");
        }
        User user = userRepository.findByEmail(form.getEmail()).orElse(null);

        if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
            throw new BadRequestException("invalid.credentials");
        }
        if (User.Status.ACTIVE.value != user.getStatus()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "user.not.active");
        }
        String id = String.format("%010d", user.getId());
        Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, securityConfig.getAccessTokenExpiry());
        Token refreshToken = tokenGenerator.create(PURPOSE_REFRESH_TOKEN, id + user.getPassword(), securityConfig.getRefreshTokenExpiry());
        return new LoginView(user, accessToken, refreshToken);
    }

    @Override
    public LoginView refresh(String refreshToken) throws BadRequestException {
       Status status;
        try {
            status = tokenGenerator.verify(PURPOSE_REFRESH_TOKEN, refreshToken);
        }
        catch (InvalidTokenException e) {
            throw new BadRequestException("Invalid token", e);
        }
        catch (TokenExpiredException e) {
            throw new BadRequestException("Token expired", e);
        }

        int userId;
        try {
            userId = Integer.parseInt(status.data.substring(0, 10));
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Invalid token", e);
        }

        String password = status.data.substring(10);

        User user = userRepository.findByIdAndPassword(userId, password).orElseThrow(UserServiceImpl::badRequestException);

        String id = String.format("%010d", user.getId());
        Token accessToken = tokenGenerator.create(PURPOSE_ACCESS_TOKEN, id, securityConfig.getAccessTokenExpiry());
        return new LoginView(
                user,
                new LoginView.TokenView(accessToken.value, accessToken.expiry),
                new LoginView.TokenView(refreshToken, status.expiry)
        );
    }
    
    private static BadRequestException badRequestException() {
        return new BadRequestException("invalid.credentials");
    }

    @Override
    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void downloadImageFile(String fileName, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public UserView update(UserUpdateForm form) {
        User user = userRepository.findById(form.getId()).orElseThrow(NotFoundException::new);
        user.update(form);
        
        UserAddress userAddress = userAddressRepository.findById(user.getUserAddressList().get(0).getId());
        userAddress.update(form);
        userAddressRepository.save(userAddress);
        
        user.setUserAddressList((List<UserAddress>) userAddressRepository.findByUserId(user));
        userRepository.save(user);
        
        user = userRepository.findById(user.getId()).orElse(user);
        return new UserView(user);
    }
    
}
