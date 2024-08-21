package com.example.ClientCRUD.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ClientCRUD.model.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {

}
