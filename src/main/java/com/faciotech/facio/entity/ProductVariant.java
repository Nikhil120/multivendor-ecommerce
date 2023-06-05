package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.faciotech.facio.dto.ProductVariantDTO;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_variant")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = true, unique = true, length = 30)
	private String sku;

	@Column(nullable = false, length = 40)
	private String name;

	@Column(nullable = false)
	private double maxPrice = 0.0;

	@Column(nullable = false)
	private double salesPrice = 0.0;

	@Column(nullable = false)
	private double costPrice = 0.0;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "productVariant", cascade = CascadeType.REMOVE)
	private List<ProductVariantOption> productVariantOptions;

	@OneToMany(mappedBy = "productVariant", cascade = CascadeType.REMOVE)
	private List<ProductImage> productImages;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public ProductVariant(ProductVariantDTO productVariantDTO) {
		this.sku = productVariantDTO.getSku();
		this.name = productVariantDTO.getName();
		this.maxPrice = productVariantDTO.getMaxPrice();
		this.salesPrice = productVariantDTO.getSalesPrice();
		this.costPrice = productVariantDTO.getCostPrice();
	}
}
