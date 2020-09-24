/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.repository;

import com.underscore.ekartapp.entity.Category;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 *
 * @author johnythomas
 */
public interface CategoryRepository extends Repository<Category, Integer> {
        
    List<Category> findAll();
    Category findById(Integer id);
    Category findByName(String name);
    Category save (Category category);
    void deleteById(Integer id);
}
