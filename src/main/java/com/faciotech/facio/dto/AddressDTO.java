package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.faciotech.facio.entity.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
	private UUID id;

	private String addressline;

	private Integer pinCode;

	private String city;

	private String state;

	private String country;

	private String googleMapLink;

	private BusinessDTO business;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public AddressDTO(Address address) {
		this.id = address.getId();
		this.addressline = address.getAddressline();
		this.pinCode = address.getPinCode();
		this.city = address.getCity();
		this.state = address.getState();
		this.country = address.getCountry();
		this.googleMapLink = address.getGoogleMapLink();
		this.createdAt = address.getCreatedAt();
		this.updatedAt = address.getUpdatedAt();
	}
}
