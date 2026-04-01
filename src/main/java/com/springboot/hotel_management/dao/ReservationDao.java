package com.springboot.hotel_management.dao;

import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReservationDao extends JpaRepository<Reservation,Integer> {
    @Query("delete from Reservation r where r.client=:client")
    @Modifying
    @Transactional
    void deleteByClient(@Param("client") Client client);

//    @Query("select r " +
//            "    from Reservation r join Client c " +
//            "    on r.client_id = c.client_id " +
//            "    where c.aadhaarNumber = :aadhaarNumber")
    Reservation findByClientAadhaarNumber( String aadhaarNumber);

    Reservation findByRoomRoomNo(int roomNo);


}
