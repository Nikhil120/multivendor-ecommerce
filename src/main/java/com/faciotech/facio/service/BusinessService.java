package com.faciotech.facio.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faciotech.facio.entity.Business;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.repository.BusinessRespository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BusinessService {
	private final BusinessRespository businessRespository;

	public Business addBusiness(String email, Business business) {
		log.info("Adding business {} for user {}", business.getBusinessName(), email);
		return businessRespository.save(business);
	}

	
	public Business updateBusiness(Business business) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteBusiness(Long id) {
		// TODO Auto-generated method stub

	}

	public boolean activateBusiness(Long Id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean deactivateBusiness(Long Id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void addUserToBusiness(Long businessId, User user) {
		// TODO Auto-generated method stub

	}

	public void removeUserFromBusiness(Long businessId, String email) {
		// TODO Auto-generated method stub

	}

}
