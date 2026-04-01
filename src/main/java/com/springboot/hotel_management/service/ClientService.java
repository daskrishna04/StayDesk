package com.springboot.hotel_management.service;

import com.springboot.hotel_management.dao.ClientDao;
import com.springboot.hotel_management.dto.ClientDto;
import com.springboot.hotel_management.entity.Client;
import com.springboot.hotel_management.entity.Room;
import com.springboot.hotel_management.exception.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientDao clientDao;

    public void save(ClientDto clientDto){
        Client client = Client.builder().
                clientName(clientDto.getClientName()).
                phoneNumber(clientDto.getPhoneNumber()).
                aadhaarNumber(clientDto.getAadhaarNumber()).
                checkInTime(clientDto.getCheckInTime()).
                checkOutTime(clientDto.getCheckOutTime()).build();

        client.setAddress(clientDto.getAddress());
        clientDao.save(client);
    }


    public Client findById(int id){
        return clientDao.findById(id).orElseThrow(()->new ClientNotFoundException("client not found"));
    }


    public void deleteById(int id){
        clientDao.deleteById(id);
    }

    public List<Client> findAll(){
        List<Client> clientList = clientDao.findAll();
        return clientList;
    }

    public void delete(Client client){
        clientDao.delete(client);
    }

    public Client findByAadhaarNumber(String aadhaarNumber) {
        Client client = clientDao.findByAadhaarNumber(aadhaarNumber);
        if(client == null) throw new ClientNotFoundException("client not found");
        return client;
    }
}
