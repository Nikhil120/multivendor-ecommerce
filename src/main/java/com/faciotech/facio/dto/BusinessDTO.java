package com.faciotech.facio.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.faciotech.facio.entity.Business;
import com.faciotech.facio.enums.BusinessTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDTO {
	private UUID id;

	private String businessCode;

	private String name;

	private String email;

	private String phoneNumber1;

	private String phoneNumber2;

	private String logo;

	private String coverImage;

	private AddressDTO address;

	private BusinessTypeEnum businessType;

	private List<UserDTO> users;

	private List<CategoryDTO> categories;

	private List<ProductDTO> products;

	private String description;

	private Boolean isEmailVerified;

	private Boolean isPhone1Verified;

	private Boolean isPhone2Verified;

	private Boolean isActive;

	private Boolean isVerified;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public BusinessDTO(Business business) {
		this.id = business.getId();
		this.businessCode = business.getBusinessCode();
		this.name = business.getName();
		this.email = business.getEmail();
		this.phoneNumber1 = business.getPhoneNumber1();
		this.phoneNumber2 = business.getPhoneNumber2();
		this.logo = business.getLogo();
		this.coverImage = business.getCoverImage();
		this.address = new AddressDTO(business.getAddress());
		this.businessType = business.getBusinessType();
		this.description = business.getDescription();
		this.isEmailVerified = business.getIsEmailVerified();
		this.isPhone1Verified = business.getIsPhone1Verified();
		this.isPhone2Verified = business.getIsPhone2Verified();
		this.isActive = business.getIsActive();
		this.isVerified = business.getIsVerified();
		this.createdAt = business.getCreatedAt();
		this.updatedAt = business.getUpdatedAt();
	}
}
