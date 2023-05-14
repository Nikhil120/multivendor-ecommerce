package com.faciotech.facio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faciotech.facio.entity.ProductOptionValue;

public interface ProductOptionValueRespository extends JpaRepository<ProductOptionValue, Integer> {
}
