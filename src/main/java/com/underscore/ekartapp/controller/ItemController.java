/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.exception.BadRequestException;
import com.underscore.ekartapp.form.ItemForm;
import com.underscore.ekartapp.service.ItemService;
import com.underscore.ekartapp.view.ResponseView;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnythomas
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;
    
    @PostMapping
    public ResponseView addItem(@Valid ItemForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseView(itemService.addItem(form));
    }
    
    @GetMapping
    public ResponseView getAll(){
        return new ResponseView(itemService.getAll());
    }
    
    
}
