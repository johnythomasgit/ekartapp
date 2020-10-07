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
import javax.validation.constraints.Size;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author nirmal
 */
public class UserUpdateForm {
    
    @NotNull(message = "item.id.is.empty")
    private Integer id;

    @NotBlank(message = "name.is.empty")
    @Size(max = 100,message = "name.not.valid")
    private String name;
    
    @NotBlank(message = "email.is.empty")
    @Email(message = "email.not.valid")
    @Size(max = 255,message = "email.not.valid")
    private String email;

    @NotBlank(message = "phone.is.empty")
    @Size(max=20,message = "phone.not.valid")
    @Pattern(regexp="^\\d*$",message = "phone.not.valid")
    private String phone ;
    
    @NotBlank(message = "address.is.empty")
    @Size(max = 255,message = "address.not.valid")
    private String address;

    @Size(max=255,message = "apartment.not.valid")
    private String city;
    
    @Size(max=20,message = "post.not.valid")
    private String post;
        
    @Nullable
    private MultipartFile file;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
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
