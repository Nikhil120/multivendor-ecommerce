package com.faciotech.facio.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		business.setBusinessId(generateBusinessId());
		business.setBusinessName("My Business Name");
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
	}

	public Business getBusinessDetails(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return user.getBusiness();
	}

	public Business updateBusiness(String email, Business business) {
		User user = userRepository.findByEmail(email).orElseThrow();
		Address address = business.getAddress();
		Business myBusiness = user.getBusiness();

		myBusiness.setBusinessName(business.getBusinessName());
		myBusiness.setDescription(business.getDescription());
		myBusiness.setEmail(business.getEmail());
		myBusiness.setPhoneNumber1(business.getPhoneNumber1());
		myBusiness.setPhoneNumber2(business.getPhoneNumber2());
		
		Address myAddress = myBusiness.getAddress();
		myAddress.setAddressline(address.getAddressline());
		myAddress.setPinCode(address.getPinCode());
		myAddress.setCity(address.getCity());
		myAddress.setState(address.getState());
		myAddress.setCountry(address.getCountry());
		myAddress.setGoogleMapLink(address.getGoogleMapLink());

		addressRespository.save(myAddress);

		businessRespository.save(myBusiness);

		return business;
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
