package com.faciotech.facio.dto;

import java.time.LocalDateTime;

import com.faciotech.facio.entity.ProductVariantOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantOptionDTO {

	private Integer optionId;

	private String optionName;

	private Integer valueId;

	private String valueName;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductVariantOptionDTO(ProductVariantOption productVariantOption) {
		this.optionId = productVariantOption.getProductOption().getId();
		this.optionName = productVariantOption.getProductOption().getName();
		this.valueId = productVariantOption.getProductOptionValue().getId();
		this.valueName = productVariantOption.getProductOptionValue().getName();
		this.createdAt = productVariantOption.getCreatedAt();
		this.updatedAt = productVariantOption.getUpdatedAt();
	}
}
