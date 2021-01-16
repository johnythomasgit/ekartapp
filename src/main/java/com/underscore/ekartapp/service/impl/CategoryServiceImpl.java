/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service.impl;

import com.underscore.ekartapp.entity.Category;
import com.underscore.ekartapp.form.CategoryForm;
import com.underscore.ekartapp.form.StatusUpdateForm;
import com.underscore.ekartapp.repository.CategoryRepository;
import com.underscore.ekartapp.repository.ItemRepository;
import com.underscore.ekartapp.service.CategoryService;
import com.underscore.ekartapp.view.CategoryView;
import com.underscore.ekartapp.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author johny
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;


    @Override
    public List<CategoryView> getAllCategories() {
        List<Category> list = categoryRepository.findAll();
        list = list.stream().filter(category -> (category.getStatus() != Category.Status.DELETED.value)).collect(Collectors.toList());
        List<CategoryView> categoryList = list.stream().map(category -> new CategoryView(category)).collect(Collectors.toList());
        return categoryList;
    }

    @Override
    public CategoryView addCategory(CategoryForm form) {
        Category category = categoryRepository.findByName(form.getName());
        if (category != null) {
            if (category.getStatus() != Category.Status.ACTIVE.value) {
                category.setStatus(Category.Status.ACTIVE.value);
                return new CategoryView(categoryRepository.save(category));
            } else {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "category.exist");
            }

        }
        return new CategoryView(categoryRepository.save(new Category(form)));
    }

    @Override
    public ResponseView deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id);
        if (category == null || category.getStatus() == Category.Status.DELETED.value) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category.not.found");
        }

        category.setStatus(Category.Status.DELETED.value);
        categoryRepository.save(category);
        return new ResponseView();
    }

    @Override
    public CategoryView updateStatus(StatusUpdateForm form) {
        Category category = categoryRepository.findById(form.getId());
        if (category == null || category.getStatus() == Category.Status.DELETED.value) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category.not.found");
        }
        category.setStatus(form.getStatus());

        return new CategoryView(categoryRepository.save(category));
    }

    @Override
    public CategoryView updateCategory(Integer id, CategoryForm form) {
        Category category = categoryRepository.findById(id);
        if (category == null || category.getStatus() == Category.Status.DELETED.value) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category.not.found");
        }
        category.update(form);
        return new CategoryView(categoryRepository.save(category));
    }

}
