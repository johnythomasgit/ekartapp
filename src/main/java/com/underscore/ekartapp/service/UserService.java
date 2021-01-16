/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service;

//import com.innovature.c2c.api.exception.BadRequestException;
//import com.innovature.c2c.api.form.ForgotPasswordForm;
//import com.innovature.c2c.api.form.LoginForm;
//import com.innovature.c2c.api.form.ResetPasswordForm;
//import com.innovature.c2c.api.form.SellerRequestForm;
//import com.innovature.c2c.api.form.StatusUpdateForm;
//import com.innovature.c2c.api.form.UserForm;
//import com.innovature.c2c.api.form.UserPushForm;
//import com.innovature.c2c.api.form.UserUpdateForm;
//import com.innovature.c2c.api.util.Pager;
//import com.innovature.c2c.api.view.ForgotPasswordView;
//import com.innovature.c2c.api.view.LoginView;
//import com.innovature.c2c.api.view.ResponseView;
//import com.innovature.c2c.api.view.UserView;

import com.underscore.ekartapp.exception.BadRequestException;
import com.underscore.ekartapp.form.LoginForm;
import com.underscore.ekartapp.form.UserForm;
import com.underscore.ekartapp.form.UserUpdateForm;
import com.underscore.ekartapp.view.LoginView;
import com.underscore.ekartapp.view.UserView;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author nirmal
 */
public interface UserService {

    UserView add(UserForm form);

    UserView update(UserUpdateForm form);

    UserView currentUser();

    List<UserView> findAll();

    LoginView login(LoginForm form, Errors errors) throws BadRequestException;

    LoginView refresh(String refreshToken) throws BadRequestException;

    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);

    void downloadImageFile(String fileName, HttpServletResponse response);

//    Pager<UserView> getAll(short role, String searchKey, int pageNo, int pageLimit);
//
//    ForgotPasswordView userForgotPassword(ForgotPasswordForm form, Errors errors);
//
//    ForgotPasswordView forgotPasswordAdmin(ForgotPasswordForm form);
//
//    ResponseView resetPasswordAdmin(ResetPasswordForm form);
//
//    UserView updateUser(UserUpdateForm form);
//
//    ResponseView delete(int id);
//
//    ResponseView disable(int id);
//
//    ResponseView updateStatusAdmin(StatusUpdateForm form);
//
//    UserView updateSellerRequestStatus(SellerRequestForm form);
//
//    UserView updateSellerRequestStatusAdmin(StatusUpdateForm form);

//    void downloadUserCsv(short role, HttpServletResponse response);
//    
//    void downloadSellerCsv(short role, HttpServletResponse response);
////
////    UserView devicePushRegistration(UserPushForm form);
////
////    UserView devicePushDeRegistration(UserPushForm form);
}
