package com.springboot.hotel_management.dao;

import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientDao extends JpaRepository<Client,Integer> {

    Client findByAadhaarNumber(String aadhaarNumber);

}
