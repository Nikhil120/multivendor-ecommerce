package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.faciotech.facio.entity.ProductImage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {
	private UUID id;

	private String url;

	private ProductDTO product;

	private ProductVariantDTO productVariant;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ProductImageDTO(ProductImage productImage) {
		this.id = productImage.getId();
		this.url = productImage.getUrl();
		this.createdAt = productImage.getCreatedAt();
		this.updatedAt = productImage.getUpdatedAt();
	}
}
