package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.faciotech.facio.entity.ProductOption;
import com.faciotech.facio.entity.ProductOptionValue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionDTO {
	private UUID id;

	private String name;

	private ProductDTO product;

	private List<ProductVariantOptionDTO> productVariantOptions;

	private List<ProductOptionValueDTO> productOptionValues;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductOptionDTO(ProductOption productOption) {
		this.id = productOption.getId();
		this.name = productOption.getName();
		this.createdAt = productOption.getCreatedAt();
		this.updatedAt = productOption.getUpdatedAt();
	}

	public void setProductOptionValues(List<ProductOptionValue> productOptionValues) {
		List<ProductOptionValueDTO> productOptionValueDTOList = new ArrayList<>();

		for (ProductOptionValue productOptionValue : productOptionValues) {
			ProductOptionValueDTO productOptionValueDTO = new ProductOptionValueDTO(productOptionValue);

			productOptionValueDTOList.add(productOptionValueDTO);

		}

		this.productOptionValues = productOptionValueDTOList;
	}
}
