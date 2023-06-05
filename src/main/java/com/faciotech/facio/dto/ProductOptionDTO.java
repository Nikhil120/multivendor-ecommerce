package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.faciotech.facio.entity.ProductOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionDTO {

	private Integer id;

	private String name;

	private ProductDTO product;

	private List<ProductVariantOptionDTO> productVariantOptions;

	private List<ProductOptionValueDTO> productOptionValues;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductOptionDTO(ProductOption productOption) {
		this.id = product.getId();
		this.name = productOption.getName();
		this.createdAt = productOption.getCreatedAt();
		this.updatedAt = productOption.getUpdatedAt();
	}
}
