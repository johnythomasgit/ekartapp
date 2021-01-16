/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.view;

import com.underscore.ekartapp.entity.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author johnythomas
 */
public class ItemView {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private Short deliveryType;
    private short status;
    private String gender;
    private String age;
    private String colour;
    private long createdDate;
    private long updatedDate;
    private UserView user;
    private CategoryView category;
    private List<ItemImageView> itemImageList;

    public ItemView(Integer id, String name, String description, int quantity, Short deliveryType, short status, long createdDate, long updatedDate, UserView user, CategoryView category, List<ItemImageView> itemImageList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.deliveryType = deliveryType;
        this.status = status;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.user = user;
        this.category = category;
        this.itemImageList = itemImageList;
    }

    public ItemView(Item item, String downloadUrl) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.quantity = item.getQuantity();
        this.price = item.getPrice();
        this.age = item.getAge();
        this.gender = item.getGender();
        this.colour = item.getColour();
        this.deliveryType = item.getDeliveryType();
        this.status = item.getStatus();
        this.createdDate = item.getCreatedDate().getTime();
        this.updatedDate = item.getUpdatedDate().getTime();
        this.user = new UserView(item.getUserId());
        this.category = new CategoryView(item.getCategoryId());
        this.itemImageList = (item.getItemImageList() != null) ? (item.getItemImageList().stream().map(obj -> (new ItemImageView(obj, downloadUrl))).collect(Collectors.toList())) : null;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Short getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Short deliveryType) {
        this.deliveryType = deliveryType;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
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

    public UserView getUser() {
        return user;
    }

    public void setUser(UserView user) {
        this.user = user;
    }

    public CategoryView getCategory() {
        return category;
    }

    public void setCategory(CategoryView category) {
        this.category = category;
    }

    public List<ItemImageView> getItemImageList() {
        return itemImageList;
    }

    public void setItemImageList(List<ItemImageView> itemImageList) {
        this.itemImageList = itemImageList;
    }

}
