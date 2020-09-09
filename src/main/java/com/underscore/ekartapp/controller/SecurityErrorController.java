/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.controller;


import com.underscore.ekartapp.view.ResponseView;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johny
 */
@RestController
public class SecurityErrorController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@RequestMapping("/errors/400")
	ResponseView error(RequestRejectedException e) {
		return new ResponseView(null,"BAD_REQUEST","malformed url");
	}
}
