package com.springboot.hotel_management.controller;

import com.springboot.hotel_management.dto.LoginRequestDto;
import com.springboot.hotel_management.dto.LoginResponseDto;
import com.springboot.hotel_management.dto.SignupRequestDto;
import com.springboot.hotel_management.entity.User;
import com.springboot.hotel_management.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("login",new LoginRequestDto());
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest request){

        request.getSession().setAttribute(
                "REDIRECT_AFTER_LOGIN",
                "/auth/admin-register"
        );

        return "restrict";
    }

    @GetMapping("/admin-register")
    public String adminRegister(Model model){
        model.addAttribute("signup",new SignupRequestDto());
        return "register";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto,
                        Model model){
//        LoginResponseDto response = authService.login(loginRequestDto);
        return "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("signup") SignupRequestDto signupRequestDto,
                               Authentication authentication,
                               Model model) {

        User.Role newUserRole = signupRequestDto.getRole(); // from form

        if(newUserRole == null){
            model.addAttribute("error","Role is mandatory");
            return "register";
        }

        String loggedInRole = authentication.getAuthorities()
                .iterator().next().getAuthority();

        // 🔥 RULES
        if (loggedInRole.equals("ROLE_ADMIN")) {

            // Admin can create ADMIN or MANAGER
            if (!(newUserRole == User.Role.ROLE_ADMIN || newUserRole == User.Role.ROLE_MANAGER)) {
                model.addAttribute("error", "Admin can only create ADMIN or MANAGER");
                return "register-signupRequestDto";
            }

        } else if (loggedInRole.equals("ROLE_MANAGER")) {

            // Manager can create only RECEPTIONIST
            if (!(newUserRole == User.Role.ROLE_RECEPTIONIST)) {
                model.addAttribute("error", "Manager can only create RECEPTIONIST");
                return "register-signupRequestDto";
            }

        } else {
            model.addAttribute("error", "You are not allowed to create users");
            return "register-signupRequestDto";
        }

        // ✅ SAVE USER
        authService.signup(signupRequestDto);

        return "redirect:/staydesk";
    }
}
