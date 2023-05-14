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
@Table(name = "product_option")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOption {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, length = 40)
	private String name;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	@OneToMany(mappedBy = "productOption")
	@JsonIgnore
	private Set<ProductVariantOption> productVariantOptions;

	@OneToMany(mappedBy = "productOption")
	@JsonIgnore
	private Set<ProductOptionValue> productOptionValues;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
