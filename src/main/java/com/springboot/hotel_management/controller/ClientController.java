package com.springboot.hotel_management.controller;

import com.springboot.hotel_management.dto.ClientDto;
import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Reservation;
import com.springboot.hotel_management.entity.Room;
import com.springboot.hotel_management.service.ClientService;
import com.springboot.hotel_management.service.ReservationService;
import com.springboot.hotel_management.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;

    private final RoomService roomService;
    private final ReservationService reservationService;


    @GetMapping("/register-client")
    public String register(Model model){
        model.addAttribute("client",new ClientDto());
        return "client/register-client";
    }
    @PostMapping("/register-client")
    public String saveClient(@Valid @ModelAttribute("client") ClientDto clientDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "register-client";
        clientService.save(clientDto);

        return "client/register-success";
    }
    @Transactional
    @GetMapping("/delete-client/{id}")
    public String deleteClient(@PathVariable("id") int id){
        Client client = clientService.findById(id);

        List<Reservation> reservation = client.getReservation();
        for(Reservation res : reservation){
            Room room = res.getRoom();
            roomService.changeAvailabilityStatus(room.getRoomNo());
        }

        clientService.delete(client);
        return "client/delete-client";
    }
    @GetMapping("/get-all-clients")
    public String getAllClientList(Model model){
        List<Client> clientList = clientService.findAll();
        model.addAttribute("clients",clientList);
        return "client/get-all-clients";
    }
}
