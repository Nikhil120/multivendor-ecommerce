package com.faciotech.facio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faciotech.facio.entity.Address;


public interface AddressRespository extends JpaRepository<Address, Integer> {
}
