package com.springboot.hotel_management.controller;

import com.springboot.hotel_management.dao.UserDao;
import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Reservation;
import com.springboot.hotel_management.entity.Room;
import com.springboot.hotel_management.service.ClientService;
import com.springboot.hotel_management.service.ReservationService;
import com.springboot.hotel_management.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final ClientService clientService;
    private final RoomService roomService;
    private final ReservationService reservationService;
    private final UserDao userDao;


    @GetMapping("/staydesk")
    public String staydesk(){
        return "staydesk";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication){

        String role = authentication.getAuthorities().iterator().next().getAuthority();

        List<Room> rooms = roomService.availableRooms();
        List<Client> clientList = clientService.findAll();
        List<Room> occupiedRooms = roomService.occupiedRooms();

        List<Reservation> reservationList = reservationService.findAll();
        model.addAttribute("reservationList",reservationList);
        model.addAttribute("available",rooms.size());
        model.addAttribute("total",clientList.size());
        model.addAttribute("occupied",occupiedRooms.size());
        model.addAttribute("totalUser",userDao.findAll().size());


        if(role.equals("ROLE_ADMIN"))
            return "dashboards/admin-dashboard";
        else
            return "dashboard";
    }

}
