package com.springboot.hotel_management.dao;

import com.springboot.hotel_management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,Integer> {


    Optional<User> findByUsername(String username);


}
