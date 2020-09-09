/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.view;

import com.underscore.ekartapp.entity.User;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author nirmal
 */
public class UserView {

    public Integer id;
    public String name;
    public String email;
    public short role;
    public String phone;
    public String imagePath;
    public String imageUrl;
    public short status;
    public String paymentId;
    public long createdDate;
    public long updatedDate;
    public List<UserAddressView> addresses;

    public UserView(Integer id, String name, String email, String googleId, String password, short role, String phone, String imagePath, String imageUrl, short status, String paymentId, long createdDate, long updatedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.phone = phone;
        this.imagePath = imagePath;
        this.imageUrl = imageUrl;
        this.status = status;
        this.paymentId = paymentId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
    
    
    public UserView(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.phone = user.getPhone();
        this.imagePath = user.getImagePath();
        this.imageUrl = user.getImageUrl();
        this.status = user.getStatus();
        this.paymentId = user.getPaymentId();
        this.addresses = (user.getUserAddressList()!=null)?(user.getUserAddressList().stream().map(obj-> new UserAddressView(obj)).collect(Collectors.toList())):null;
        this.createdDate = user.getCreatedDate().getTime();
        this.updatedDate = user.getCreatedDate().getTime();
    }

    
    
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

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    
}
