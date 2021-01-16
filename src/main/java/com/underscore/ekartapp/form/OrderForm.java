/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.form;

import javax.validation.constraints.NotNull;

/**
 * @author johny
 */
public class OrderForm {

    @NotNull(message = "item.id.is.empty")
    private Integer itemId;

    @NotNull(message = "quantity.is.empty")
    private Integer quantity;

    @NotNull(message = "price.is.empty")
    private Integer price;

    @NotNull(message = "deliveryType.is.empty")
    private Short deliveryType;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Short getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Short deliveryType) {
        this.deliveryType = deliveryType;
    }

}
