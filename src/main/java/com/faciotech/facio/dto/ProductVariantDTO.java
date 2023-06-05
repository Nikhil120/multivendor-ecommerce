package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.faciotech.facio.entity.ProductVariant;
import com.faciotech.facio.entity.ProductVariantOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductVariantDTO {

	private Integer id;

	private String sku;

	private String name;

	private double maxPrice;

	private double salesPrice;

	private double costPrice;

	private ProductDTO product;

	private List<ProductVariantOptionDTO> optionValueDTOs;

	private List<ProductImageDTO> productImages;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductVariantDTO(ProductVariant productVariant) {
		this.id = productVariant.getId();
		this.sku = productVariant.getSku();
		this.name = productVariant.getName();
		this.maxPrice = productVariant.getMaxPrice();
		this.salesPrice = productVariant.getSalesPrice();
		this.costPrice = productVariant.getCostPrice();
		this.createdAt = productVariant.getCreatedAt();
		this.updatedAt = productVariant.getUpdatedAt();
		this.optionValueDTOs = new ArrayList<>();

		for (ProductVariantOption productVariantOption : productVariant.getProductVariantOptions()) {
			optionValueDTOs.add(new ProductVariantOptionDTO(productVariantOption));
		}
	}
}
