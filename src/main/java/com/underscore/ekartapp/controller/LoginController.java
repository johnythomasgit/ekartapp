/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.form.LoginForm;
import com.underscore.ekartapp.service.UserService;
import com.underscore.ekartapp.view.ResponseView;
import com.underscore.ekartapp.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author nirmal
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public UserView currentUser() {
        return userService.currentUser();
    }

    @PostMapping
    public ResponseView login(@Valid @RequestBody LoginForm form, Errors errors) {
        return new ResponseView(userService.login(form, errors));
    }

    @PutMapping
    public ResponseView refresh(@RequestBody String refreshToken) {
        return new ResponseView(userService.refresh(refreshToken));
    }

//    @PostMapping("/forgot")
//    public ResponseView userForgotPassword(@Valid @RequestBody ForgotPasswordForm form, Errors errors) {
//        return new ResponseView(userService.userForgotPassword( form,  errors));
//    }
}
