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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ClientCRUD.model.Address;
import com.example.ClientCRUD.model.Client;
import com.example.ClientCRUD.repo.AddressRepo;
import com.example.ClientCRUD.repo.ClientRepo;

@RestController
public class AddressController {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private AddressRepo addressRepo;

    @GetMapping("/getAllAddresses")
    public ResponseEntity<List<Address>> getAllAddresses() {
        try {

            List<Address> addressList = new ArrayList<>();
            addressRepo.findAll().forEach(addressList::add);

            if (addressList.isEmpty()) {
                return new ResponseEntity<>(addressList, HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/getAddressById/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Optional<Address> addressData = addressRepo.findById(id);

        if (addressData.isPresent()) {
            return new ResponseEntity<>(addressData.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addAddress")
    public ResponseEntity<Address> addAddress(@RequestParam Long clientId, @RequestBody Address address) {

        Client client = clientRepo.findById(clientId).orElseThrow();
        client.addAddress(address);
        Address addressObj = addressRepo.save(address);

        clientRepo.save(client);

        return new ResponseEntity<>(addressObj, HttpStatus.OK);
    }

    @PostMapping("/updateAddressById/{id}")
    public ResponseEntity<Address> updateAddressById(@PathVariable Long id, @RequestBody Address newAddressData) {
        Optional<Address> addressData = addressRepo.findById(id);

        if (addressData.isPresent()) {
            Address updatedAddressData = addressData.get();
            updatedAddressData.setCity(newAddressData.getCity());
            updatedAddressData.setStreet(newAddressData.getStreet());

            Address addressObj = addressRepo.save(updatedAddressData);

            return new ResponseEntity<>(addressObj, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteAddressById/{id}")
    public ResponseEntity<HttpStatus> deleteAddressById(@PathVariable Long id) {
        addressRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
