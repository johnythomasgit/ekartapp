/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.repository;

import com.underscore.ekartapp.entity.Item;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 *
 * @author johnythomas
 */
public interface ItemRepository extends Repository<Item, Integer> {
    Item findById(Integer itemId);
    List<Item> findAll();
    
    List<Item> findByStatusOrderByUpdatedDateDesc(short status);
    @Query(value="SELECT i.* from item i WHERE i.stats=?1 AND i.name like %?2% AND IF(?3>0,i.categoryId=?3,true) AND IF(?4, a.upddatedDate > now()- INTERVAL 1 DAY, true)",nativeQuery = true)
    List<Item> getItemList(short status,String key,Integer categoryId,Boolean isFresh);
    Item save(Item item);
    void deleteById(Integer id);
    
}
