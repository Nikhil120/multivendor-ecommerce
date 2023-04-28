package com.faciotech.facio.entity;

import java.time.LocalDateTime;
import java.util.Set;

import com.faciotech.facio.enums.BusinessTypeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@jakarta.persistence.Entity
@Table(name = "business")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Business {
	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(nullable = false, unique = true, length = 30)
	private String businessId;

	@Column(nullable = false, length = 40)
	private String businessName;

	@Column(nullable = false, unique = true, length = 45)
	private String email;
	
	@Column
	private String phoneNumber1;

	@Column
	private String phoneNumber2;

	@Column
	private String logo;

	@Column
	private String coverImage;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id")
	private Address address;

	@Enumerated(EnumType.STRING)
	private BusinessTypeEnum businessType;

	@OneToMany(mappedBy = "business")
	private Set<User> users;

	@Column(nullable = false)
	private Boolean isEmailVerified;

	@Column(nullable = false)
	private Boolean isPhone1Verified;

	@Column(nullable = false)
	private Boolean isPhone2Verified;

	@Column(nullable = false)
	private Boolean isActive;

	@Column(nullable = false)
	private Boolean isVerified;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;
}
