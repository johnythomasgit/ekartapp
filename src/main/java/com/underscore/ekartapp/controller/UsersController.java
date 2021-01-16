/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.form.UserForm;
import com.underscore.ekartapp.form.UserUpdateForm;
import com.underscore.ekartapp.service.UserService;
import com.underscore.ekartapp.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

/**
 * @author johnythomas
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService userService;

    Logger logger = Logger.getLogger(UsersController.class.getName());

    @PostMapping
    public ResponseView add(@Valid @RequestBody UserForm form) {
        return new ResponseView(userService.add(form));
    }

    @PutMapping
    public ResponseView update(@Valid @RequestBody UserUpdateForm form) {
        return new ResponseView(userService.update(form));
    }

}
