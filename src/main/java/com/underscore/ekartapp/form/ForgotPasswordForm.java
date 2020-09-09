/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author nirmal
 */
public class ForgotPasswordForm {
    
    @NotBlank(message = "email.is.empty")
    @Email(message = "email.not.valid")
    private String email;
    
    @NotNull(message = "password.is.empty")
    @Pattern(regexp ="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$", message = "password.not.valid")
    private String password;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

}
