package com.springboot.hotel_management.controller;

import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Reservation;
import com.springboot.hotel_management.entity.Room;
import com.springboot.hotel_management.service.ClientService;
import com.springboot.hotel_management.service.InvoicePdfService;
import com.springboot.hotel_management.service.ReservationService;
import com.springboot.hotel_management.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ClientService clientService;
    private final RoomService roomService;
    private final ReservationService reservationService;

    private final InvoicePdfService invoicePdfService;

    @GetMapping("/reservation-options")
    public String reservationOptions(){
        return "reservation/reservation-options";
    }

    @GetMapping("/reservation-form")
    public String reservationForm(Model model){
        model.addAttribute("reservation",new Reservation());
        model.addAttribute("clients",clientService.findAll());
        model.addAttribute("availableRooms",roomService.availableRooms());
        return "reservation/reservation-form";
    }
//    @PostMapping("/reservation-save")
//    public String reservationSuccess(@ModelAttribute("reservation") Reservation reservation,Model model){
//
//        model.addAttribute(reservation);
//        Reservation resv =Reservation.builder().
//                client(reservation.getClient()).
//                room(reservation.getRoom()).
//                balance(reservation.getBalance()).build();
//        reservationService.save(resv);
//
//        return "reservation-success";
//    }

    @PostMapping("/reservation-save")
    public String saveReservation(
            @RequestParam int clientId,
            @RequestParam int roomNo,
            @RequestParam double balance,
            Model model) {

        Client client = clientService.findById(clientId);

        Room room = roomService.findById(roomNo);

        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setRoom(room);
        reservation.setBalance(balance);

        reservationService.save(reservation);

        roomService.changeAvailabilityStatus(roomNo);

        model.addAttribute("reservation", reservation);
        return "reservation/reservation-success";
    }

    @GetMapping("/reservation-confirm")
    public String searchReservation(
            @RequestParam String type,
            @RequestParam String value,
            Model model) {

        Reservation reservation = null;

        switch (type) {
            case "reservationId" ->
                    reservation = reservationService.findById(Integer.parseInt(value));

            case "aadhaar" ->
                    reservation = reservationService.findByClientAadhaarNumber(value);

            case "roomNo" ->
                    reservation = reservationService.findByRoomNo(Integer.parseInt(value));
        }

        if (reservation == null) {
            model.addAttribute("error", "No reservation found");
            return "reservation/search-reservation";
        }

        long days = ChronoUnit.DAYS.between(
                reservation.getClient().getCheckInTime(),
                LocalDateTime.now()
        );
        if (days == 0) days = 1;

        double bill = days * reservation.getRoom().getPricePerNight();



        model.addAttribute("reservation", reservation);
        model.addAttribute("billAmount",bill);
        return "reservation/reservation-details";
    }

    @GetMapping("/reservation-search")
    public String res(){
        return "reservation/search-reservation";
    }

    @PostMapping("/checkout-success/{id}")
    public String checkOut(@PathVariable int id,
                           Model model){
        Reservation reservation = reservationService.checkOut(id);

        long days = ChronoUnit.DAYS.between(
                reservation.getClient().getCheckInTime(),
                LocalDateTime.now()
        );
        if (days == 0) days = 1;

        double bill = days * reservation.getRoom().getPricePerNight();

        model.addAttribute("reservation",reservation);
        model.addAttribute("totalAmount",bill);
        return "reservation/checkout-success";
    }



    @GetMapping("/invoice/{id}")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable int id) throws Exception {

//        Reservation reservation = reservationService.findById(id);
//                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        Client client = clientService.findById(id);

        long days = ChronoUnit.DAYS.between(
                client.getCheckInTime(),
                LocalDateTime.now()
        );
//        if (days == 0) days = 1;
//
//        double bill = days * reservation.getRoom().getPricePerNight();
//
        byte[] pdf = invoicePdfService.generateInvoice(client);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }


}
