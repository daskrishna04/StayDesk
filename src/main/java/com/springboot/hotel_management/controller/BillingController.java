package com.springboot.hotel_management.controller;

import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BillingController {

    private final ClientService clientService;

    @GetMapping("/search-client")
    public String searchForClient(Model model){
        model.addAttribute("client",new Client());
        return "billing/search-client";
    }

    @PostMapping("/search-result")
    public String searchClient(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String value,
            Model model) {

        if (type == null || value == null) {
            model.addAttribute("error", "Please select search type and enter value");
            return "billing/search-client";
        }

        Client client = null;

        if ("clientId".equals(type)) {
            client = clientService.findById(Integer.parseInt(value));
        } else if ("aadhaar".equals(type)) {
            client = clientService.findByAadhaarNumber(value);
        }

        if (client == null) {
            model.addAttribute("error", "Client not found");
            return "billing/search-client";
        }

        model.addAttribute("client", client);
        return "billing/client-details";
    }

}
