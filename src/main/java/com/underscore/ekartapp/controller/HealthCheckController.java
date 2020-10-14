/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;

import com.underscore.ekartapp.view.ResponseView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johnythomas
 */
@RestController
public class HealthCheckController {
    @GetMapping("/")
    public ResponseView healthCheck() {
        return new ResponseView("OK");
    }
    
}