package com.faciotech.facio.controller;

import java.util.Collection;
import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.entity.Category;
import com.faciotech.facio.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		categoryService.addCategory(email, category);
		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable("id") Integer id, @RequestBody Category category) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Category updatedCategory = categoryService.updateCategory(email, id, category);
		return new ResponseEntity<Category>(updatedCategory, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryDetails(@PathVariable("id") Integer id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Category category = categoryService.getCategoryDetails(email, id);

		if (category == null) {
			return new ResponseEntity<Category>(category, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Category>(category, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<HashMap<String, Object>> getAllCategory() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Category> categoryList = categoryService.getAllCategory(email);
		HashMap<String, Object> data = new HashMap<>();
//		System.out.println(data);
		data.put("count", categoryList.size());
		data.put("data", categoryList);
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}
}
