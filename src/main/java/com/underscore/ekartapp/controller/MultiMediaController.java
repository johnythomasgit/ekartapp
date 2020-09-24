/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.service.ItemService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnythomas
 */
@RestController
@RequestMapping("/media")
public class MultiMediaController {
    
    @Autowired
    private ItemService itemService;
    
    @GetMapping("/downloadFile/{fileName:.+}")
    public void downloadImageFile(@PathVariable String fileName, HttpServletResponse response) {
        itemService.downloadImageFile(fileName, response);
    }
    
}
