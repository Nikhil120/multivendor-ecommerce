package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@Column(nullable = false, unique = true, length = 30)
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
	@JsonIgnore
	private Product product;

	@OneToMany(mappedBy = "productVariant")
	@JsonIgnore
	private Set<ProductVariantOption> productVariantOptions;

	@OneToMany(mappedBy = "productVariant")
	@JsonIgnore
	private Set<ProductImage> productImages;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}