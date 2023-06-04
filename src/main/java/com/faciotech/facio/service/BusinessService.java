package com.faciotech.facio.service;

import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faciotech.facio.dto.AddressDTO;
import com.faciotech.facio.dto.BusinessDTO;
import com.faciotech.facio.entity.Address;
import com.faciotech.facio.entity.Business;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.enums.BusinessTypeEnum;
import com.faciotech.facio.repository.AddressRespository;
import com.faciotech.facio.repository.BusinessRespository;
import com.faciotech.facio.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BusinessService {
	private final UserRepository userRepository;
	private final BusinessRespository businessRespository;
	private final AddressRespository addressRespository;

	public void addDefaultBusiness(String email) {

		User user = userRepository.findByEmail(email).orElseThrow();

		Business business = new Business();
		business.setBusinessCode(generateBusinessId());
		business.setName("My Business Name");
		business.setDescription("About my business!!!");
		business.setBusinessType(BusinessTypeEnum.DISTRIBUTOR);
		business.setEmail("mybusiness@mail.com");
		business.setPhoneNumber1("+0000000000");
		business.setPhoneNumber2("+0000000000");
		Address address = new Address();
		address.setAddressline("My Business Address");
		address.setPinCode(111111);
		address.setCity("City");
		address.setState("State");
		address.setCountry("Country");
		address.setGoogleMapLink("https://www.google.com/maps");
		business.setAddress(address);
		user.setBusiness(business);

		addressRespository.save(address);

		businessRespository.save(business);
		userRepository.save(user);
	}

	public BusinessDTO getBusinessDetails(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();

		BusinessDTO businessDTO = new BusinessDTO(user.getBusiness());

		return businessDTO;
	}

	public BusinessDTO updateBusiness(String email, BusinessDTO businessDTO) {
		User user = userRepository.findByEmail(email).orElseThrow();
		AddressDTO addressDTO = businessDTO.getAddress();
		Business business = user.getBusiness();

		business.setName(businessDTO.getName());
		business.setDescription(businessDTO.getDescription());
		business.setEmail(businessDTO.getEmail());
		business.setPhoneNumber1(businessDTO.getPhoneNumber1());
		business.setPhoneNumber2(businessDTO.getPhoneNumber2());
		business.setLogo(businessDTO.getLogo());
		business.setCoverImage(businessDTO.getCoverImage());
		
		Address address = business.getAddress();
		address.setAddressline(addressDTO.getAddressline());
		
		System.out.println(addressDTO.getPinCode());
		
		address.setPinCode(addressDTO.getPinCode());
		address.setCity(addressDTO.getCity());
		address.setState(addressDTO.getState());
		address.setCountry(addressDTO.getCountry());
		address.setGoogleMapLink(addressDTO.getGoogleMapLink());

		addressRespository.save(address);

		businessRespository.save(business);

		return new BusinessDTO(business);
	}

	public void addUserToBusiness(Long businessId, User user) {
		// TODO Auto-generated method stub

	}

	public void removeUserFromBusiness(Long businessId, String email) {
		// TODO Auto-generated method stub

	}

	protected String generateBusinessId() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder code = new StringBuilder();
		code.append("bus_");
		Random rnd = new Random();
		while (code.length() < 8) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			code.append(SALTCHARS.charAt(index));
		}
		code.append("_");
		code.append(System.currentTimeMillis());
		return code.toString();
	}
}
