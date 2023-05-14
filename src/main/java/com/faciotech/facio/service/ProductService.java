package com.faciotech.facio.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faciotech.facio.entity.Address;
import com.faciotech.facio.entity.Business;
import com.faciotech.facio.entity.Category;
import com.faciotech.facio.entity.Product;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.enums.BusinessTypeEnum;
import com.faciotech.facio.repository.AddressRespository;
import com.faciotech.facio.repository.BusinessRespository;
import com.faciotech.facio.repository.ProductRespository;
import com.faciotech.facio.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
	private final UserRepository userRepository;
	private final ProductRespository productRespository;

	public void addProduct(String email, Product product) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();

		product.setProductCode(generateProductId());
		product.setBusiness(business);

		productRespository.save(product);
	}

	public Product getProductDetails(String email, int productId) {
		System.out.println("Debugging");
//		User user = userRepository.findByEmail(email).get();
//		Business business = user.getBusiness();
		Optional<Product> optionalProduct = productRespository.findByBusinessAndProduct(1, productId);

		if (optionalProduct.isEmpty()) {
			return null;
		}
		
		System.out.println(optionalProduct.get().getCategory().getName());
		
		return optionalProduct.get();
	}

	public List<Product> getAllProducts(String email) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		List<Product> categoryList = productRespository.findAllProductForBusiness(business.getId());

		return categoryList;
	}

	protected String generateProductId() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder code = new StringBuilder();
		code.append("prd_");
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
