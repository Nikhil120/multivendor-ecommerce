package com.faciotech.facio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faciotech.facio.entity.ProductVariant;

public interface ProductVariantRespository extends JpaRepository<ProductVariant, Integer> {
}
