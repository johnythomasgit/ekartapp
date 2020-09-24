/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.view;

import com.underscore.ekartapp.entity.ItemImage;

/**
 *
 * @author johnythomas
 */
public class ItemImageView {

    private String imageUrl;

    public ItemImageView(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ItemImageView(ItemImage itemImage) {
        this.imageUrl = itemImage.getImagePath();
    }

    public ItemImageView(ItemImage itemImage, String downloadUrl) {
        this.imageUrl = downloadUrl + "/media/downloadFile/" + itemImage.getImagePath();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
