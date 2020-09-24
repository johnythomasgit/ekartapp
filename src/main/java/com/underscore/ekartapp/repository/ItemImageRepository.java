/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.repository;

import com.underscore.ekartapp.entity.Item;
import com.underscore.ekartapp.entity.ItemImage;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 *
 * @author johnythomas
 */
public interface ItemImageRepository extends Repository<ItemImage, Integer>{
    ItemImage findById(Integer itemId);
    ItemImage save(ItemImage itemImage);
    List<ItemImage> findByItemId(Item item);
    void deleteByItemId(Item item);
}
