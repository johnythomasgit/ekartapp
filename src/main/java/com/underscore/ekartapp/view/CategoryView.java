/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.view;

import com.underscore.ekartapp.entity.Category;

/**
 *
 * @author johny
 */
public class CategoryView {

    private int id;
    private String name;
    private Short status;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public CategoryView(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryView(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.status = category.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
