/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.form.CategoryForm;
import com.underscore.ekartapp.service.CategoryService;
import com.underscore.ekartapp.view.ResponseView;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johny
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseView getAllCategory() {

        return new ResponseView(categoryService.getAllCategories());
    }
    
    @GetMapping("/public")
    public ResponseView getAllCategoryPublic() {

        return new ResponseView(categoryService.getAllCategories());
    }
    
    @PostMapping
    public ResponseView addCategory(@Valid @RequestBody CategoryForm form) {
        return new ResponseView(categoryService.addCategory(form));
    }
}
