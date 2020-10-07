/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.repository;

import com.underscore.ekartapp.entity.User;
import com.underscore.ekartapp.entity.UserAddress;
import java.util.List;
import org.springframework.data.repository.Repository;

/**
 *
 * @author johnythomas
 */
public interface UserAddressRepository extends Repository<UserAddress, Integer>  {
    UserAddress save(UserAddress obj);
    UserAddress findById(Integer id);
    List<UserAddress> findByUserId(User obj);
}
