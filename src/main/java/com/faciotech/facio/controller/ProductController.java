package com.faciotech.facio.controller;

import java.util.HashMap;
import java.util.List;

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

import com.faciotech.facio.dto.ProductDTO;
import com.faciotech.facio.dto.ProductOptionDTO;
import com.faciotech.facio.dto.ProductVariantDTO;
import com.faciotech.facio.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

	@PostMapping
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		ProductDTO productDTO2 = productService.addProduct(email, productDTO);
		return new ResponseEntity<ProductDTO>(productDTO2, HttpStatus.OK);
	}

	@GetMapping("/{product_id}")
	public ResponseEntity<ProductDTO> getProductDetails(@PathVariable("product_id") Integer productId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		ProductDTO productDTO = productService.getProductDetails(email, productId);

		if (productDTO == null) {
			return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}

	@PostMapping("/{product_id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("product_id") Integer productId,
			@RequestBody ProductDTO productDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.updateProduct(email, productId, productDTO);
		return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}

	@DeleteMapping("/{product_id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("product_id") Integer productId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.deleteProduct(email, productId);
		return ResponseEntity.ok("Product deleted successfully.");
	}

	@GetMapping("/all")
	public ResponseEntity<HashMap<String, Object>> getAllProduct() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		List<ProductDTO> productDTOList = productService.getAllProducts(email);
		HashMap<String, Object> data = new HashMap<>();
		data.put("count", productDTOList.size());
		data.put("data", productDTOList);
		return new ResponseEntity<HashMap<String, Object>>(data, HttpStatus.OK);
	}

	@PostMapping("{product_id}/options")
	public ResponseEntity<String> addProductOption(@PathVariable("product_id") Integer productId,
			@RequestBody ProductOptionDTO productOptionDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.addProductOptions(email, productId, productOptionDTO);
		return ResponseEntity.ok("Product option added successfully.");
	}

	@PostMapping("{product_id}/options/{option_id}")
	public ResponseEntity<String> updateProductOption(@PathVariable("product_id") Integer productId,
			@PathVariable("option_id") Integer optionId, @RequestBody ProductOptionDTO productOptionDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.updateProductOption(email, productId, optionId, productOptionDTO);
		return ResponseEntity.ok("Product option updated successfully.");
	}

	@DeleteMapping("{product_id}/options/{option_id}")
	public ResponseEntity<String> deleteProductOption(@PathVariable("product_id") Integer productId,
			@PathVariable("option_id") Integer optionId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.deleteProductOption(email, productId, optionId);
		return ResponseEntity.ok("Product option deleted successfully.");
	}

	@PostMapping("{product_id}/variants")
	public ResponseEntity<String> addProductVariant(@PathVariable("product_id") Integer productId,
			@RequestBody ProductVariantDTO productVariantDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.addProductVariant(email, productId, productVariantDTO);
		return ResponseEntity.ok("Product variant added successfully.");
	}

	@PostMapping("{product_id}/variants/{variant_id}")
	public ResponseEntity<String> updateProductVariant(@PathVariable("product_id") Integer productId,
			@PathVariable("variant_id") Integer variantId, @RequestBody ProductVariantDTO productVariantDTO) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.updateProductVariant(email, productId, variantId, productVariantDTO);
		return ResponseEntity.ok("Product variant updated successfully.");
	}

	@DeleteMapping("{product_id}/variants/{variant_id}")
	public ResponseEntity<String> deleteProductVariant(@PathVariable("product_id") Integer productId,
			@PathVariable("variant_id") Integer variantId) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		productService.deleteProductVariant(email, productId, variantId);
		return ResponseEntity.ok("Product variant deleted successfully.");
	}
}
