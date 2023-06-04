package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.faciotech.facio.dto.CategoryDTO;

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
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, unique = true, length = 30)
	private String categoryId;

	@Column(nullable = false, length = 40)
	private String name;

	@Column(nullable = true, length = 200)
	private String description;

	@Column(nullable = false)
	private int productCount = 0;

	@ManyToOne
	@JoinColumn(name = "business_id")
	private Business business;

	@OneToMany(mappedBy = "category")
	private List<Product> products;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Category(CategoryDTO categoryDTO) {
		this.name = categoryDTO.getName();
		this.description = categoryDTO.getName();
	}
}
