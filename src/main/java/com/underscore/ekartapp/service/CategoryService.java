/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service;


import com.underscore.ekartapp.form.CategoryForm;
import com.underscore.ekartapp.form.StatusUpdateForm;
import com.underscore.ekartapp.view.CategoryView;
import com.underscore.ekartapp.view.ResponseView;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author johny
 */
@Service
public interface CategoryService {
    List<CategoryView> getAllCategories();
    CategoryView addCategory(CategoryForm form);
    CategoryView updateCategory(Integer id,CategoryForm form);
    ResponseView deleteCategory(Integer id);
    CategoryView updateStatus(StatusUpdateForm form);
}
