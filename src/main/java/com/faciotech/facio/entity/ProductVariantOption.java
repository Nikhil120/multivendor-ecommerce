package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = "product_variant_option")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariantOption {
	@Id
	@GeneratedValue
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	@ManyToOne
	@JoinColumn(name = "product_variant_id")
	@JsonIgnore
	private ProductVariant productVariant;

	@ManyToOne
	@JoinColumn(name = "product_option_id")
	@JsonIgnore
	private ProductOption productOption;

	@ManyToOne
	@JoinColumn(name = "product_option_value_id")
	@JsonIgnore
	private ProductOptionValue productOptionValue;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}