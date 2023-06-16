package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.faciotech.facio.entity.Product;
import com.faciotech.facio.entity.ProductImage;
import com.faciotech.facio.entity.ProductOption;
import com.faciotech.facio.entity.ProductVariant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private UUID id;
	
	private String productCode;

	private String name;

	private double maxPrice;

	private double salesPrice;

	private double costPrice;

	private CategoryDTO category;

	private BusinessDTO business;

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
		this.category = new CategoryDTO(product.getCategory());
		this.haveProductVariant = product.isHaveProductVariant();
		this.isActive = product.isActive();
		this.createdAt = product.getCreatedAt();
		this.updatedAt = product.getUpdatedAt();
	}

	public void setProductOptions(List<ProductOption> productOptions) {
		List<ProductOptionDTO> productOptionDTOList = new ArrayList<>();

		for (ProductOption productOption : productOptions) {
			ProductOptionDTO productOptionDTO = new ProductOptionDTO(productOption);
			productOptionDTO.setProductOptionValues(productOption.getProductOptionValues());
			productOptionDTOList.add(productOptionDTO);
		}

		this.productOptions = productOptionDTOList;
	}

	public void setProductVariants(List<ProductVariant> productVariants) {
		List<ProductVariantDTO> productVariantDTOList = new ArrayList<>();

		for (ProductVariant productVariant : productVariants) {
			ProductVariantDTO productVariantDTO = new ProductVariantDTO(productVariant);
			productVariantDTOList.add(productVariantDTO);
		}

		this.productVariants = productVariantDTOList;
	}

	public void setProductImages(List<ProductImage> productImages) {
		List<ProductImageDTO> productImageDTOList = new ArrayList<>();

		for (ProductImage productImage : productImages) {
			ProductImageDTO productImageDTO = new ProductImageDTO(productImage);
			productImageDTOList.add(productImageDTO);
		}

		this.productImages = productImageDTOList;
	}
}
