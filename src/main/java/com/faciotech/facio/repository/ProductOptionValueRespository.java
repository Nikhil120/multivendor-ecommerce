package com.faciotech.facio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faciotech.facio.entity.ProductOptionValue;

public interface ProductOptionValueRespository extends JpaRepository<ProductOptionValue, Integer> {

	@Query(value = "SELECT * FROM product_option_value p WHERE p.id=?1", nativeQuery = true)
	Optional<ProductOptionValue> findById(UUID valueId);
}
