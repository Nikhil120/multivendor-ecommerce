package com.faciotech.facio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faciotech.facio.entity.ProductVariantOption;

public interface ProductVariantOptionRespository extends JpaRepository<ProductVariantOption, Integer> {
}
