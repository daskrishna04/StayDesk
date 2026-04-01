package com.springboot.hotel_management.service;

import com.springboot.hotel_management.dao.ReservationDao;
import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Reservation;
import com.springboot.hotel_management.exception.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationDao reservationDao;

    public void save(Reservation reservation){
        reservationDao.save(reservation);
    }

    public void delete(int id){
        reservationDao.deleteById(id);
    }

    public void deleteByClient(Client client){
        reservationDao.deleteByClient(client);
    }

    public Reservation findById(int id){
        return reservationDao.findById(id).orElseThrow(()-> new ReservationNotFoundException("reservation not found"));
    }

    public Reservation findByClientAadhaarNumber(String aadhaarNumber){
        Reservation reservation = reservationDao.findByClientAadhaarNumber(aadhaarNumber);
        if(reservation == null) throw new ReservationNotFoundException("reservation not found");
        return reservation;
    }

    public Reservation findByRoomNo(int roomNo){
        Reservation reservation = reservationDao.findByRoomRoomNo(roomNo);
        if(reservation == null) throw new ReservationNotFoundException("reservation not found");
        return reservation;
    }

    public List<Reservation> findAll(){
        return reservationDao.findAll();
    }

    @Transactional
    public Reservation checkOut(int id){

        Reservation reservation = reservationDao.findById(id).orElseThrow(() -> new ReservationNotFoundException("reservation not found"));
        reservation.getRoom().setAvailabilityStatus(true);
        reservationDao.deleteById(id);

        return reservation;
    }
}
