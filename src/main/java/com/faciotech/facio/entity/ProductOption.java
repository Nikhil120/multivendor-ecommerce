package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.faciotech.facio.dto.ProductOptionDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 40)
	private String name;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@OneToMany(mappedBy = "productOption", cascade = CascadeType.REMOVE)
	private List<ProductVariantOption> productVariantOptions;

	@OneToMany(mappedBy = "productOption", cascade = CascadeType.REMOVE)
	private List<ProductOptionValue> productOptionValues;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public ProductOption(ProductOptionDTO productOptionDTO) {
		this.name = productOptionDTO.getName();
	}
}
