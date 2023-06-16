package com.faciotech.facio.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faciotech.facio.entity.ProductOption;

public interface ProductOptionRespository extends JpaRepository<ProductOption, Integer> {

	@Query(value = "SELECT * FROM product_option p WHERE p.id=?1", nativeQuery = true)
	Optional<ProductOption> findById(UUID optionId);
}
