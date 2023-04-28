package com.faciotech.facio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faciotech.facio.entity.Business;


public interface BusinessRespository extends JpaRepository<Business, Integer> {
}
