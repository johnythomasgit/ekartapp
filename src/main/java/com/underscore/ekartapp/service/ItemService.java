/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.service;

import com.underscore.ekartapp.form.ItemForm;
import com.underscore.ekartapp.view.ItemView;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author johnythomas
 */
public interface ItemService {
    ItemView addItem(ItemForm form);
    List<ItemView> getAll();
    void downloadImageFile(String fileName, HttpServletResponse response);
}
