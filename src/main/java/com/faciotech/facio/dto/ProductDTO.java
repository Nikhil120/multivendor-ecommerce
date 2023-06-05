package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.faciotech.facio.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private Integer id;

	private String productCode;

	private String name;

	private double maxPrice;

	private double salesPrice;

	private double costPrice;

	private CategoryDTO categoryDTO;

	private BusinessDTO businessDTO;

	private List<ProductOptionDTO> productOptions;

	private List<ProductVariantDTO> productVariants;

	private List<ProductImageDTO> productImages;

	private boolean haveProductVariant;

	private boolean isActive;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductDTO(Product product) {
		this.id = product.getId();
		this.productCode = product.getProductCode();
		this.name = product.getName();
		this.maxPrice = product.getMaxPrice();
		this.salesPrice = product.getSalesPrice();
		this.costPrice = product.getCostPrice();
		this.categoryDTO = new CategoryDTO(product.getCategory());
		this.haveProductVariant = product.isHaveProductVariant();
		this.isActive = product.isActive();
		this.createdAt = product.getCreatedAt();
		this.updatedAt = product.getUpdatedAt();
	}
}
