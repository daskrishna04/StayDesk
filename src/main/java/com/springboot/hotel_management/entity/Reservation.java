package com.springboot.hotel_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int reservationId;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "room_no")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
