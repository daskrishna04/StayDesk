package com.springboot.hotel_management.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

//    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        return httpSecurity
                .csrf(csrfConfigure ->csrfConfigure.disable())
//                .sessionManagement(sessionManagement ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        //public apis
                        .requestMatchers("/staydesk").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        //static page
//                        .requestMatchers("/register.html").permitAll()
//                        .requestMatchers("/register.html").permitAll()
//                        .requestMatchers("/admin-login.html").permitAll()
//                        .requestMatchers("/favicon.ico").permitAll()
//                        .requestMatchers("/**/*.html").permitAll()
                        //protected apis
                        .requestMatchers("/admin-register").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/room/**").hasRole("MANAGER")
                        .requestMatchers("/reservation/**","/client/**").hasAnyRole("ADMIN", "MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")

                        .successHandler((request, response, authentication) -> {

                            String redirectUrl = (String) request.getSession()
                                    .getAttribute("REDIRECT_AFTER_LOGIN");

                            if (redirectUrl != null) {
                                request.getSession().removeAttribute("REDIRECT_AFTER_LOGIN");
                                response.sendRedirect(redirectUrl);
                            } else {
                                response.sendRedirect("/dashboard");
                            }
                        })

                        .failureUrl("/auth/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // default
                        .logoutSuccessUrl("/auth/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();

    }

//    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("manager")
                .password(passwordEncoder().encode("pass"))
                .roles("MANAGER")
                .build();
        UserDetails user2 = User.withUsername("admin")
                .password(passwordEncoder().encode("pass"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user1,user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return configuration.getAuthenticationManager();
    }
}
