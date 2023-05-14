package com.faciotech.facio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import com.faciotech.facio.entity.Product;
import com.faciotech.facio.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping("/add-product")
	public ResponseEntity<Product> addCategory(@RequestBody Product product) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.addProduct(email, product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductDetails(@PathVariable("id") Integer id) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Product product = productService.getProductDetails(email, id);

		if (product == null) {
			return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<HashMap<String, Object>> getAllProduct() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Product> productList = productService.getAllProducts(email);
		HashMap<String, Object> data = new HashMap<>();
//		System.out.println(data);
		data.put("count", productList.size());
		data.put("data", productList);
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}
}
