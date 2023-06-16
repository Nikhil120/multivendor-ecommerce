package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.UUID;

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

	private UUID optionId;

	private String optionName;

	private UUID valueId;

	private String valueName;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductVariantOptionDTO(ProductVariantOption productVariantOption) {
		this.optionId = productVariantOption.getProductOption().getId();
		this.optionName = productVariantOption.getProductOption().getName();
		if (productVariantOption.getProductOptionValue() == null) {
			this.valueId = null;
			this.valueName = null;
		} else {
			this.valueId = productVariantOption.getProductOptionValue().getId();
			this.valueName = productVariantOption.getProductOptionValue().getName();
		}
		this.createdAt = productVariantOption.getCreatedAt();
		this.updatedAt = productVariantOption.getUpdatedAt();
	}
}
