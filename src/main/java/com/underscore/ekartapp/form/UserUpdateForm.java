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

    @NotBlank(message = "name.is.empty")
    @Size(max = 100,message = "name.not.valid")
    private String name;
    
    @NotBlank(message = "email.is.empty")
    @Email(message = "email.not.valid")
    @Size(max = 255,message = "email.not.valid")
    private String email;
    
   
//    @NotNull(message = "role.is.empty")
    private byte role;
    
    @NotBlank(message = "address.is.empty")
    @Size(max = 255,message = "address.not.valid")
    private String address;
    
    @NotBlank(message = "phone.is.empty")
    @Size(max=20,message = "phone.not.valid")
    @Pattern(regexp="^\\d*$",message = "phone.not.valid")
    private String phone ;

//    @NotBlank(message = "apartment.is.empty")
    @Size(max=255,message = "apartment.not.valid")
    private String apartment;
    
//    @NotBlank(message = "post.is.empty")
    @Size(max=20,message = "post.not.valid")
    private String post;
    
//    @NotNull
    @Size(max = 20)
    private String post2;
    
    @NotNull(message = "prefecture.is.empty")
    @Size(max = 100,message = "prefecture.is.not.valid")
    private String prefecture;
    
    @Nullable
    @Size(max = 1000)
    private String facebookId;
    
    @Nullable
    @Size(max = 1000)
    private String twitterId;
    
    @Nullable
    private MultipartFile file;

    public String getPost2() {
        return post2;
    }

    public void setPost2(String post2) {
        this.post2 = post2;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }
    
    
    public byte getRole() {
        return role;
    }

    public void setRole(byte role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
