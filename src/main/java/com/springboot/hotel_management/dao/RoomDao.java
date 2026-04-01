package com.springboot.hotel_management.dao;

import com.springboot.hotel_management.entity.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomDao extends JpaRepository<Room,Integer> {

    List<Room> findByAvailabilityStatusFalse();

    List<Room> findByAvailabilityStatusTrue();

    @Query("update Room r set r.availabilityStatus = not r.availabilityStatus where r.roomNo =:roomno")
    @Modifying
    @Transactional
    void changeAvailabilityStatus(@Param("roomno") int roomNo);

    //optional-------------->>>>>>>>>>>>>>

//    @Query("select r from Room r where r.availabilityStatus = true")
//    @Modifying
//    @Transactional
//    public List<Room> availableRooms();


}
