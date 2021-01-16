/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service;

import com.underscore.ekartapp.form.ItemForm;
import com.underscore.ekartapp.form.ItemUpdateForm;
import com.underscore.ekartapp.form.StatusUpdateForm;
import com.underscore.ekartapp.view.ItemView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author johnythomas
 */
public interface ItemService {
    ItemView addItem(ItemForm form);

    ItemView updateItem(ItemUpdateForm form);

    ItemView updateItemStatus(StatusUpdateForm form);

    List<ItemView> getAll();

    List<ItemView> getSellerItems();

    List<ItemView> getAll(String key, Integer categoryId, Boolean fresh);

    void downloadImageFile(String fileName, HttpServletResponse response);
}
