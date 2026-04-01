package com.springboot.hotel_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(initialValue = 1000)
    private int clientId;
    private String clientName;
    private String Address;
    private String phoneNumber;
    private String aadhaarNumber;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Reservation> reservation;

//    @OneToMany
//    private List<Room> room;
}
