package com.faciotech.facio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faciotech.facio.entity.Product;

public interface ProductRespository extends JpaRepository<Product, Integer> {
	@Query(value = "SELECT * FROM product p WHERE p.id=?2 and p.business_id=?1", nativeQuery = true)
	Optional<Product> findByBusinessAndProduct(Integer businessId, int productId);

	@Query(value = "SELECT * FROM product p WHERE p.business_id=?1", nativeQuery = true)
	List<Product> findAllProductForBusiness(Integer businessId);
}
