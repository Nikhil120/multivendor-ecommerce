package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.faciotech.facio.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
	private UUID id;
	
	private String categoryId;

	private String name;

	private String description;

	private int productCount;

	private BusinessDTO business;

	private List<ProductDTO> products;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.categoryId = category.getCategoryId();
		this.name = category.getName();
		this.description = category.getDescription();
		this.productCount = category.getProductCount();
		this.createdAt = category.getCreatedAt();
		this.updatedAt = category.getUpdatedAt();
	}
}
