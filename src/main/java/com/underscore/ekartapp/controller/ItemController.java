/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.exception.BadRequestException;
import com.underscore.ekartapp.form.ItemForm;
import com.underscore.ekartapp.form.ItemUpdateForm;
import com.underscore.ekartapp.form.StatusUpdateForm;
import com.underscore.ekartapp.service.ItemService;
import com.underscore.ekartapp.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
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

    @PutMapping
    public ResponseView updateItem(@Valid ItemUpdateForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return new ResponseView(itemService.updateItem(form));
    }

    @PutMapping("/status")
    public ResponseView updateItemStatus(@Valid @RequestBody StatusUpdateForm form) {
        return new ResponseView(itemService.updateItemStatus(form));
    }

    @GetMapping
    public ResponseView getAll() {
        return new ResponseView(itemService.getAll());
    }

    @GetMapping("/seller")
    public ResponseView getSellerItems() {
        return new ResponseView(itemService.getSellerItems());
    }

    @GetMapping("/search")
    public ResponseView getAll(
            @RequestParam(name = "searchKey", required = false, defaultValue = "") String searchKey,
            @RequestParam(name = "categoryId", required = false, defaultValue = "0") Integer categoryId,
            @RequestParam(name = "freshOnly", required = false, defaultValue = "false") Boolean freshOnly) {
        return new ResponseView(itemService.getAll(searchKey, categoryId, freshOnly));
    }

}
