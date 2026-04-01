package com.springboot.hotel_management.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {
    @NotNull(message = "Room No is mandatory")
    private int roomNo;

    @NotBlank(message = "Room Type is required")
    @Pattern(regexp = "^(Single|Double|Deluxe|Executive)$",
                message = "invalid input.")
    private String roomType;

    @NotNull(message = "Price is required")
    @Max(10000)
    @Min(2000)
    private double pricePerNight;

    private boolean availabilityStatus;
}
