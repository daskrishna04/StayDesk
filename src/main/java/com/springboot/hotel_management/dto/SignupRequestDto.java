package com.springboot.hotel_management.dto;

import com.springboot.hotel_management.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    private String username;
    private String password;
    private User.Role role;
}
