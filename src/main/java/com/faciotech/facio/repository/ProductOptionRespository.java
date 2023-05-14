package com.faciotech.facio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faciotech.facio.entity.ProductOption;

public interface ProductOptionRespository extends JpaRepository<ProductOption, Integer> {
}
