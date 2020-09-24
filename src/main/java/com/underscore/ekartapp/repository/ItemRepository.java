/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.repository;

import com.underscore.ekartapp.entity.Item;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 *
 * @author johnythomas
 */
public interface ItemRepository extends Repository<Item, Integer> {
    Item findById(Integer itemId);
    List<Item> findAll();
    List<Item> findByStatusOrderByUpdatedDateDesc(short status);
    Item save(Item item);
    void deleteById(Integer id);
    
}
