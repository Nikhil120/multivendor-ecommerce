package com.faciotech.facio.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faciotech.facio.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	@Query(value = "SELECT * FROM category c WHERE c.id=?2 and c.business_id=?1", nativeQuery = true)
	Optional<Category> findByBusinessAndCategory(Integer businessId, int categoryId);

	@Query(value = "SELECT * FROM category c WHERE c.business_id=?1", nativeQuery = true)
	List<Category> findAllCategoryForBusiness(Integer businessId);
}
