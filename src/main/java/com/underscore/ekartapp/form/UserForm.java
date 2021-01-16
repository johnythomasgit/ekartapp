/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.form;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author nirmal
 */
public class UserForm {

    @NotBlank(message = "name.is.empty")
    @Size(max = 100, message = "name.not.valid")
    private String name;

    @NotBlank(message = "email.is.empty")
    @Email(message = "email.not.valid")
    @Size(max = 255, message = "email.not.valid")
    private String email;

    @Nullable
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,15}$", message = "password.not.valid")
    private String password;

    @NotBlank(message = "phone.is.empty")
    @Size(max = 20, message = "phone.not.valid")
    @Pattern(regexp = "^\\d*$", message = "phone.not.valid")
    private String phone;

    @NotBlank(message = "address.is.empty")
    @Size(max = 255, message = "address.not.valid")
    private String address;

    @Size(max = 255, message = "apartment.not.valid")
    private String city;

    @Size(max = 20, message = "post.not.valid")
    private String post;

    @Nullable
    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }


}
