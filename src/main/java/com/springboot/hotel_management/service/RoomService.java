package com.springboot.hotel_management.service;

import com.springboot.hotel_management.dao.RoomDao;
import com.springboot.hotel_management.dto.RoomDto;
import com.springboot.hotel_management.entity.Room;
import com.springboot.hotel_management.exception.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomDao roomDao;

    public void save(RoomDto roomDto){
        Room room = Room.builder().
                roomNo(roomDto.getRoomNo()).
                type(roomDto.getRoomType()).
                pricePerNight(roomDto.getPricePerNight()).
                availabilityStatus(roomDto.isAvailabilityStatus()).build();

        roomDao.save(room);
    }

    public List<Room> occupiedRooms(){
        return roomDao.findByAvailabilityStatusFalse();
    }

    public List<Room> availableRooms(){
        return roomDao.findByAvailabilityStatusTrue();
    }

    public Room findById(int roomNo) {
        return roomDao.findById(roomNo).orElseThrow(()->new RoomNotFoundException("room not found"));
    }

    public List<Room> findAll(){
        return roomDao.findAll();
    }

    public void changeAvailabilityStatus(int roomN0){
        roomDao.changeAvailabilityStatus(roomN0);
    }
}
