package com.faciotech.facio.dto;

import java.time.LocalDateTime;

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

//	private List<ProductOption> productOptions;
//
//	private List<ProductVariant> productVariants;
//
//	private List<ProductImage> productImages;

	private boolean haveProductVariant;

	private boolean isActive;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductDTO(Product product) {

	}
}
