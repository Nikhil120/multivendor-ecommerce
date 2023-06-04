package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "product_option_value")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOptionValue {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, length = 40)
	private String name;

	@ManyToOne
	@JoinColumn(name = "product_option_id")
	private ProductOption productOption;

	@OneToMany(mappedBy = "productOptionValue")
	private List<ProductVariantOption> productVariantOptions;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
