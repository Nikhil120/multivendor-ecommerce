package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.faciotech.facio.entity.ProductOptionValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionValueDTO {

	private Integer id;

	private String name;

	private ProductOptionDTO productOption;

	private List<ProductVariantOptionDTO> productVariantOptions;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductOptionValueDTO(ProductOptionValue productOptionValue) {
		this.id = productOptionValue.getId();
		this.name = productOptionValue.getName();
		this.createdAt = productOptionValue.getCreatedAt();
		this.updatedAt = productOptionValue.getUpdatedAt();
	}
}
