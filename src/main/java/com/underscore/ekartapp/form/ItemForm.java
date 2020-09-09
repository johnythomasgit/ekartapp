/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.form;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author johny
 */
public class ItemForm {
    
    @NotNull(message = "item.name.is.empty")
    @Size(min = 1, max = 100, message = "item.name.is.not.valid")
    private String name;
    
    @NotNull(message = "item.description.is.empty")
    @Size(min = 1, max = 1000, message = "item.description.is.not.valid")
    private String description;
    
    @NotNull(message = "item.price.is.empty")
    @Digits(integer=7,fraction = 0,message = "price.exceeds.size")
    private Integer price;
    
    @NotNull(message = "item.quantity.is.empty")
    @Digits(integer=7,fraction = 0,message = "item.quantity.exceeds.size")
    private Integer quantity;
    
    @NotNull(message = "item.delivery.type.is.empty")
    private Short deliveryType;
    
    @NotNull(message = "item.category.id.is.empty")
    private Integer categoryId;

    @NotNull(message = "item.images.is.empty")
    private MultipartFile  images[];

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public short getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(short deliveryType) {
        this.deliveryType = deliveryType;
    }

    public MultipartFile[] getImages() {
        return images;
    }

    public void setImages(MultipartFile[] images) {
        this.images = images;
    }
    
    
}
