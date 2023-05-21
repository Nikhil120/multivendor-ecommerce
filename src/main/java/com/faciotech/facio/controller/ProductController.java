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
import com.faciotech.facio.entity.ProductOption;
import com.faciotech.facio.entity.ProductVariant;
import com.faciotech.facio.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<Product> addProduct(@RequestBody Product product) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.addProduct(email, product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/{product_id}")
	public ResponseEntity<Product> getProductDetails(@PathVariable("product_id") Integer productId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Product product = productService.getProductDetails(email, productId);

		if (product == null) {
			return new ResponseEntity<Product>(product, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@PostMapping("/{product_id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("product_id") Integer productId,
			@RequestBody Product product) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.updateProduct(email, productId, product);
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<HashMap<String, Object>> getAllProduct() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<Product> productList = productService.getAllProducts(email);
		HashMap<String, Object> data = new HashMap<>();
//		System.out.println(data);
		data.put("count", productList.size());
		data.put("data", productList);
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}

	@PostMapping("{product_id}/options")
	public ResponseEntity<String> addProductOption(@PathVariable("product_id") Integer productId,
			@RequestBody ProductOption productOption) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.addProductOptions(email, productId, productOption);
		return ResponseEntity.ok("Product option added successfully.");
	}

	@PostMapping("{product_id}/options/{option_id}")
	public ResponseEntity<String> updateProductOption(@PathVariable("product_id") Integer productId,
			@PathVariable("option_id") Integer optionId, @RequestBody ProductOption productOption) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.updateProductOption(email, productId, optionId, productOption);
		return ResponseEntity.ok("Product option updated successfully.");
	}

	@PostMapping("{product_id}/variants")
	public ResponseEntity<String> addProductVariant(@PathVariable("product_id") Integer productId,
			@RequestBody ProductVariant productVariant) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.addProductVariant(email, productId, productVariant);
		return ResponseEntity.ok("Product option added successfully.");
	}

	@PostMapping("{product_id}/variants/{variant_id}")
	public ResponseEntity<String> updateProductVariant(@PathVariable("product_id") Integer productId,
			@PathVariable("variant_id") Integer variantId, @RequestBody ProductVariant productVariant) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.updateProductVariant(email, productId, variantId, productVariant);
		return ResponseEntity.ok("Product option updated successfully.");
	}
}
