package com.faciotech.facio.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(nullable = false, length = 50)
	private String addressline;

	@Column(nullable = false, length = 6)
	private Integer pinCode;

	@Column(nullable = false, length = 20)
	private String city;

	@Column(nullable = false, length = 20)
	private String state;

	@Column(nullable = false, length = 20)
	private String country;

	@Column
	private String googleMapLink;

	@OneToOne(mappedBy = "address")
	private Business business;
}