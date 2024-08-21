package com.example.ClientCRUD.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClientCRUD.model.Client;
import com.example.ClientCRUD.repo.ClientRepo;

@RestController
public class ClientController {
    @Autowired
    private ClientRepo clientRepo;

    @GetMapping("/getAllClients")
    public ResponseEntity<List<Client>> getAllClients() {
        try {

            List<Client> clientList = new ArrayList<>();
            clientRepo.findAll().forEach(clientList::add);

            if (clientList.isEmpty()) {
                return new ResponseEntity<>(clientList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getClientById/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Optional<Client> clientData = clientRepo.findById(id);

        if (clientData.isPresent()) {
            return new ResponseEntity<>(clientData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addClient")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        Client clientObj = clientRepo.save(client);

        return new ResponseEntity<>(clientObj, HttpStatus.OK);
    }

    @PostMapping("/updateClientById/{id}")
    public ResponseEntity<Client> updateClientById(@PathVariable Long id, @RequestBody Client newClientData) {
        Optional<Client> clientData = clientRepo.findById(id);

        if (clientData.isPresent()) {
            Client updatedClientData = clientData.get();
            updatedClientData.setClientName(newClientData.getClientName());
            updatedClientData.setAddresses(newClientData.getAddresses());

            Client clientObj = clientRepo.save(updatedClientData);

            return new ResponseEntity<>(clientObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteClientById/{id}")
    public ResponseEntity<HttpStatus> deleteClientById(@PathVariable Long id) {
        clientRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
