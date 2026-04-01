package com.springboot.hotel_management.controller;

import com.springboot.hotel_management.dto.RoomDto;
import com.springboot.hotel_management.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/register-room")
    public String registerRoomForm(Model model){
        model.addAttribute("room",new RoomDto());
        model.addAttribute("roomType",List.of( "Single", "Double", "Deluxe", "Executive" ));

        return "room/register-room";
    }
    @PostMapping("/register-room")
    public String saveANewRoom(@Valid @ModelAttribute("room") RoomDto roomDto,
                               BindingResult bindingResult,
                                Model model){
        if (bindingResult.hasErrors()) return "register-room";
        roomService.save(roomDto);
        model.addAttribute("room",roomDto);
        return "room/room-register-success";
    }
    @GetMapping("/get-all-rooms")
    public String getAllRooms(Model model){
        model.addAttribute("rooms",roomService.findAll());
        return "room/get-all-rooms";
    }
}
