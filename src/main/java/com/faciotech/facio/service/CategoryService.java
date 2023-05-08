package com.faciotech.facio.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faciotech.facio.entity.Business;
import com.faciotech.facio.entity.Category;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.repository.CategoryRepository;
import com.faciotech.facio.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {

	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;

	public void addCategory(String email, Category category) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();

		category.setCategoryId(generateCategoryId());
		category.setBusiness(business);

		categoryRepository.save(category);
	}

	public Category updateCategory(String email, int categoryId, Category newCategory) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		Optional<Category> optionalCategory = categoryRepository.findByBusinessAndCategory(business.getId(),
				categoryId);

		if (optionalCategory.isEmpty()) {
			return null;
		}

		Category category = optionalCategory.get();

		category.setName(newCategory.getName());
		category.setDescription(newCategory.getDescription());

		categoryRepository.save(category);

		return category;
	}

	public Category getCategoryDetails(String email, int categoryId) {
		System.out.println("Debugging");
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		Optional<Category> optionalCategory = categoryRepository.findByBusinessAndCategory(business.getId(),
				categoryId);

		if (optionalCategory.isEmpty()) {
			return null;
		}

		return optionalCategory.get();
	}

	public List<Category> getAllCategory(String email) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		List<Category> categoryList = categoryRepository.findAllCategoryForBusiness(business.getId());

		return categoryList;
	}

	protected String generateCategoryId() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder code = new StringBuilder();
		code.append("cat_");
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
