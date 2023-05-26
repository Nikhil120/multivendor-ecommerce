package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, unique = true, length = 30)
	private String productCode;

	@Column(nullable = false, length = 40)
	private String name;

	@Column(nullable = false)
	private double maxPrice = 0.0;

	@Column(nullable = false)
	private double salesPrice = 0.0;

	@Column(nullable = false)
	private double costPrice = 0.0;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Category category;

	@ManyToOne
	@JoinColumn(name = "business_id")
	@JsonIgnore
	private Business business;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<ProductOption> productOptions;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<ProductVariant> productVariants;

	@OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private List<ProductImage> productImages;

	@Column
	private boolean haveProductVariant = false;

	@Column
	private boolean isActive = true;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
