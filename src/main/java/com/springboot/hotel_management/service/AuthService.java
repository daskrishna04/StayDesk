package com.springboot.hotel_management.service;

import com.springboot.hotel_management.dao.UserDao;
import com.springboot.hotel_management.dto.LoginRequestDto;
import com.springboot.hotel_management.dto.LoginResponseDto;
import com.springboot.hotel_management.dto.SignupRequestDto;
import com.springboot.hotel_management.dto.SignupResponseDto;
import com.springboot.hotel_management.entity.User;
//import com.springboot.hotel_management.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;

//    private final AuthUtil authUtil;

//    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
//        );
//
//        User user = (User) authentication.getPrincipal();
//
//        String token = authUtil.getAccessToken(user);
//
//        return new LoginResponseDto(token,user.getRole());
//    }

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = userDao.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("user already exists");

        user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .role(signupRequestDto.getRole())
                .build();

        userDao.save(user);

        return new SignupResponseDto(user.getUserId(),user.getUsername());

    }
}
