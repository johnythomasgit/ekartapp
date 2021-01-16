/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.view;

import com.underscore.ekartapp.entity.UserAddress;


/**
 * @author johnythomas
 */
public class UserAddressView {

    public Integer id;
    public String house;
    public String city;
    public String post;
    public String coordinates;

    public UserAddressView(Integer id, String house, String city, String post, String coordinates) {
        this.id = id;
        this.house = house;
        this.city = city;
        this.post = post;
        this.coordinates = coordinates;
    }

    public UserAddressView(UserAddress obj) {
        this.id = obj.getId();
        this.house = obj.getHouse();
        this.city = obj.getCity();
        this.post = obj.getPost();
        this.coordinates = obj.getCoordinates();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
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

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

}
