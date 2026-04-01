package com.springboot.hotel_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    @NotBlank(message ="Name is Required")
    @Pattern(regexp = "^(?=.{2,50}$)[A-Za-z]+(?:[ .][A-Za-z]+)*$",
            message ="Name must be 2-50 character and contains only"+
                    " letters and valid separator (space, dot)")
    private String clientName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "\\d{10}",message = "Phone Number should be 10 digits only")
    private String phoneNumber;

    @NotBlank(message = "Aadhaar number is required")
    @Pattern(regexp = "\\d{12}",message = "Aadhaar Number should be 12 digits only")
    private String aadhaarNumber;

    @NotBlank(message = "Address is required")
    private String Address;

    @NotNull(message = "Check In Time is mandatory")
    private LocalDateTime checkInTime;

    @NotNull(message = "Check Out Time is mandatory")
    private LocalDateTime checkOutTime;

}
