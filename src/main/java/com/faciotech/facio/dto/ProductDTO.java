package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.faciotech.facio.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
