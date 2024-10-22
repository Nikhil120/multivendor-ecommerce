package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.faciotech.facio.enums.BusinessTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "business")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, unique = true, length = 30)
	private String businessCode;

	@Column(nullable = false, length = 40)
	private String name;

	@Column(nullable = false, length = 45)
	private String email;

	@Column
	private String phoneNumber1;

	@Column
	private String phoneNumber2;

	@Column
	private String logo;

	@Column
	private String coverImage;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@Enumerated(EnumType.STRING)
	private BusinessTypeEnum businessType;

	@OneToMany(mappedBy = "business")
	@JsonIgnore
	private List<User> users;

	@OneToMany(mappedBy = "business")
	@JsonIgnore
	private List<Category> categories;

	@OneToMany(mappedBy = "business")
	@JsonIgnore
	private List<Product> products;

	@Column
	private String description;

	@Column(nullable = false)
	private Boolean isEmailVerified = false;

	@Column(nullable = false)
	private Boolean isPhone1Verified = false;

	@Column(nullable = false)
	private Boolean isPhone2Verified = false;

	@Column(nullable = false)
	private Boolean isActive = true;

	@Column(nullable = false)
	private Boolean isVerified = false;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;
}
