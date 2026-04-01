package com.springboot.hotel_management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    private int roomNo;
    private String type;
    private double pricePerNight;
    private boolean availabilityStatus;

    @OneToMany(mappedBy = "room")
    private List<Reservation> reservation;

}
