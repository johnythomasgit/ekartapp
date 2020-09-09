/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.underscore.ekartapp.repository;


import com.underscore.ekartapp.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

/**
 *
 * @author nirmal
 */
public interface UserRepository extends Repository<User, Integer> {

    List<User> findAll();
    
    List<User> findByStatus(short status);

    Long countByRoleEqualsAndNameContainingIgnoreCase(short role, String searchKey);

    Page<User> findByStatusNotAndRoleEqualsAndNameContainingIgnoreCaseOrderByUpdatedDateDesc(short status,short role, String searchKey, Pageable page);
    
    List<User> findByStatusNotAndRoleEqualsOrderByUpdatedDateDesc(short status,short role);

    void deleteById(Integer id);

    Optional<User> findById(Integer userId);

    Optional<User> findByIdAndPassword(Integer id, String password);
//    @Query(value = "select * from User  where email COLLATE utf8mb4_bin =?1",nativeQuery = true)
    Optional<User> findByEmail(String email);

    User save(User user);
}
