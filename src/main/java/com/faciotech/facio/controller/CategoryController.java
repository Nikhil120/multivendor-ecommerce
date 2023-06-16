package com.faciotech.facio.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faciotech.facio.dto.CategoryDTO;
import com.faciotech.facio.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
	private final CategoryService categoryService;

	@PostMapping
	public ResponseEntity<CategoryDTO> addCategory(@RequestBody CategoryDTO categoryDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		CategoryDTO newCategoryDTO = categoryService.addCategory(email, categoryDTO);
		return new ResponseEntity<CategoryDTO>(newCategoryDTO, HttpStatus.OK);
	}

	@PostMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") UUID id,
			@RequestBody CategoryDTO categoryDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		CategoryDTO updatedCategoryDTO = categoryService.updateCategory(email, id, categoryDTO);
		return new ResponseEntity<CategoryDTO>(updatedCategoryDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") UUID id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		categoryService.deleteCategory(email, id);
		return ResponseEntity.ok("Category Deleted successfully");
	}

	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryDetails(@PathVariable("id") UUID id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		CategoryDTO categoryDTO = categoryService.getCategoryDetails(email, id);

		if (categoryDTO == null) {
			return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<HashMap<String, Object>> getAllCategory() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<CategoryDTO> categoryDTOList = categoryService.getAllCategory(email);
		HashMap<String, Object> data = new HashMap<>();
		data.put("count", categoryDTOList.size());
		data.put("data", categoryDTOList);
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}
}
