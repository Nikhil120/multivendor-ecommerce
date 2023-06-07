package com.faciotech.facio.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faciotech.facio.dto.ProductDTO;
import com.faciotech.facio.dto.ProductImageDTO;
import com.faciotech.facio.dto.ProductOptionDTO;
import com.faciotech.facio.dto.ProductOptionValueDTO;
import com.faciotech.facio.dto.ProductVariantDTO;
import com.faciotech.facio.dto.ProductVariantOptionDTO;
import com.faciotech.facio.entity.Business;
import com.faciotech.facio.entity.Category;
import com.faciotech.facio.entity.Product;
import com.faciotech.facio.entity.ProductImage;
import com.faciotech.facio.entity.ProductOption;
import com.faciotech.facio.entity.ProductOptionValue;
import com.faciotech.facio.entity.ProductVariant;
import com.faciotech.facio.entity.ProductVariantOption;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.repository.CategoryRepository;
import com.faciotech.facio.repository.ProductImageRepository;
import com.faciotech.facio.repository.ProductOptionRespository;
import com.faciotech.facio.repository.ProductOptionValueRespository;
import com.faciotech.facio.repository.ProductRespository;
import com.faciotech.facio.repository.ProductVariantOptionRespository;
import com.faciotech.facio.repository.ProductVariantRespository;
import com.faciotech.facio.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	private final UserRepository userRepository;
	private final CategoryRepository categoryRepository;
	private final ProductRespository productRespository;
	private final ProductOptionRespository productOptionRespository;
	private final ProductOptionValueRespository productOptionValueRespository;
	private final ProductVariantRespository productVariantRespository;
	private final ProductVariantOptionRespository productVariantOptionRespository;
	private final ProductImageRepository productImageRepository;

	public ProductDTO addProduct(String email, ProductDTO productDTO) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategory().getId());
		Category category = optionalCategory.get();

		if (optionalCategory.isEmpty()) {
			return null;
		}

		Product product = new Product(productDTO);
		product.setProductCode(generateProductId());
		product.setCategory(category);
		product.setBusiness(business);

		productRespository.save(product);

		int count = category.getProductCount();
		category.setProductCount(count + 1);

		categoryRepository.save(category);

		return new ProductDTO(product);
	}

	public Optional<Product> getProduct(String email, Integer productId) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		Optional<Product> optionalProduct = productRespository.findByBusinessAndProduct(business.getId(), productId);

		return optionalProduct;
	}

	public ProductDTO getProductDetails(String email, Integer productId) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return null;
		}
		Product product = optionalProduct.get();

		ProductDTO productDTO = new ProductDTO(product);
		productDTO.setProductOptions(product.getProductOptions());
		productDTO.setProductVariants(product.getProductVariants());
		productDTO.setProductImages(product.getProductImages());
		return productDTO;
	}

	public ProductDTO updateProduct(String email, Integer productId, ProductDTO productDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return null;
		}

		Optional<Category> optionalCategory = categoryRepository.findById(productDTO.getCategory().getId());
		Category category = optionalCategory.get();

		if (optionalCategory.isEmpty()) {
			return null;
		}

		Product product = optionalProduct.get();
		product.setName(productDTO.getName());
		product.setMaxPrice(productDTO.getMaxPrice());
		product.setSalesPrice(productDTO.getSalesPrice());
		product.setCostPrice(productDTO.getCostPrice());
		product.setCategory(category);

		productRespository.save(product);

		return new ProductDTO(product);
	}

	public Boolean deleteProduct(String email, Integer productId) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return false;
		}

		productRespository.delete(optionalProduct.get());
		return true;
	}

	public List<ProductDTO> getAllProducts(String email) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		List<Product> productList = productRespository.findAllProductForBusiness(business.getId());

		List<ProductDTO> productDTOList = new ArrayList<>();

		for (Product product : productList) {
			productDTOList.add(new ProductDTO(product));
		}

		return productDTOList;
	}

	public ProductOptionDTO addProductOptions(String email, Integer productId, ProductOptionDTO productOptionDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return null;
		}

		Product product = optionalProduct.get();

		ProductOption productOption = new ProductOption(productOptionDTO);

		productOption.setProduct(product);
		productOptionRespository.save(productOption);

		for (ProductOptionValueDTO productOptionValueDTO : productOptionDTO.getProductOptionValues()) {
			ProductOptionValue productOptionValue = new ProductOptionValue(productOption, productOptionValueDTO);
			productOptionValueRespository.save(productOptionValue);
		}

		for (ProductVariant productVariant : product.getProductVariants()) {
			ProductVariantOption productVariantOption = new ProductVariantOption();
			productVariantOption.setProductVariant(productVariant);
			productVariantOption.setProductOption(productOption);
			productVariantOption.setProductOptionValue(null);
			productVariantOptionRespository.save(productVariantOption);
		}

		return new ProductOptionDTO(productOption);
	}

	public Boolean updateProductOption(String email, Integer productId, Integer productOptionId,
			ProductOptionDTO productOptionDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return false;
		}
		Product product = optionalProduct.get();

		ProductOption myProductOption = null;

		for (ProductOption productOption : product.getProductOptions()) {
			if (productOption.getId().equals(productOptionId)) {
				myProductOption = productOption;
				break;
			}
		}

		myProductOption.setName(productOptionDTO.getName());
		productOptionRespository.save(myProductOption);

		Set<String> values = new HashSet<String>();

		for (ProductOptionValueDTO productOptionValueDTO : productOptionDTO.getProductOptionValues()) {
			values.add(productOptionValueDTO.getName());
		}

		for (ProductOptionValue productOptionValue : myProductOption.getProductOptionValues()) {
			if (values.contains(productOptionValue.getName())) {
				values.remove(productOptionValue.getName());
			} else {
				if (productOptionValue.getProductVariantOptions().size() == 0) {
					productOptionValueRespository.delete(productOptionValue);
				}
			}
		}

		for (String value : values) {
			ProductOptionValue productOptionValue = new ProductOptionValue();
			productOptionValue.setName(value);
			productOptionValue.setProductVariantOptions(null);
			productOptionValue.setProductOption(myProductOption);
			productOptionValueRespository.save(productOptionValue);
		}

		return true;
	}

	public void deleteProductOption(String email, Integer productId, Integer productOptionId) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		Product product = optionalProduct.get();

		for (ProductOption productOption : product.getProductOptions()) {
			if (productOption.getId().equals(productOptionId)) {
				productOptionRespository.delete(productOption);
				break;
			}
		}
	}

	public void addProductVariant(String email, Integer productId, ProductVariantDTO productVariantDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);
		Product product = optionalProduct.get();
		ProductVariant productVariant = new ProductVariant(productVariantDTO);

		productVariant.setProduct(product);
		productVariantRespository.save(productVariant);

		List<ProductVariantOptionDTO> options = productVariantDTO.getProductVariantOptions();

		for (ProductVariantOptionDTO productVariantOptionDTO : options) {
			ProductVariantOption productVariantOption = new ProductVariantOption();
			ProductOption productOption = productOptionRespository.findById(productVariantOptionDTO.getOptionId())
					.get();
			ProductOptionValue productOptionValue = productOptionValueRespository
					.findById(productVariantOptionDTO.getValueId()).get();
			productVariantOption.setProductVariant(productVariant);
			productVariantOption.setProductOption(productOption);
			productVariantOption.setProductOptionValue(productOptionValue);
			productVariantOptionRespository.save(productVariantOption);
		}
	}

	public void updateProductVariant(String email, Integer productId, Integer productVariantId,
			ProductVariantDTO productVariantDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return;
		}

		Product product = optionalProduct.get();

		ProductVariant productVariant = null;

		for (ProductVariant productVariant2 : product.getProductVariants()) {
			if (productVariant2.getId().equals(productVariantId)) {
				productVariant = productVariant2;
				break;
			}
		}

		productVariant.setName(productVariantDTO.getName());
		productVariant.setMaxPrice(productVariantDTO.getMaxPrice());
		productVariant.setSalesPrice(productVariantDTO.getSalesPrice());
		productVariant.setCostPrice(productVariantDTO.getCostPrice());
		productVariantRespository.save(productVariant);

		List<ProductVariantOptionDTO> options = productVariantDTO.getProductVariantOptions();

		int i = 0;
		for (ProductVariantOptionDTO productVariantOptionDTO : options) {
			ProductVariantOption productVariantOption = productVariant.getProductVariantOptions().get(i);
			ProductOption productOption = productOptionRespository.findById(productVariantOptionDTO.getOptionId())
					.get();
			ProductOptionValue productOptionValue = productOptionValueRespository
					.findById(productVariantOptionDTO.getValueId()).get();
			productVariantOption.setProductOption(productOption);
			productVariantOption.setProductOptionValue(productOptionValue);
			productVariantOptionRespository.save(productVariantOption);
			++i;
		}
	}

	public void deleteProductVariant(String email, Integer productId, Integer productVariantId) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return;
		}

		Product product = optionalProduct.get();

		for (ProductVariant productVariant : product.getProductVariants()) {
			if (productVariant.getId().equals(productVariantId)) {
				productVariantRespository.delete(productVariant);
				break;
			}
		}
	}

	public void addProductImage(String email, Integer productId, ProductImageDTO productImageDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return;
		}

		Product product = optionalProduct.get();

		ProductVariantDTO productVariantDTO = productImageDTO.getProductVariant();
		ProductVariant productVariant = null;

		if (productVariantDTO != null) {
			productVariant = productVariantRespository.findById(productVariantDTO.getId()).get();
		}

		ProductImage productImage = new ProductImage(productImageDTO);
		productImage.setProduct(product);
		productImage.setProductVariant(productVariant);

		productImageRepository.save(productImage);
	}

	public void updateProductImage(String email, Integer productId, Integer productImageId,
			ProductImageDTO productImageDTO) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return;
		}

		Product product = optionalProduct.get();

		List<ProductImage> productImages = product.getProductImages();

		for (ProductImage productImage : productImages) {
			if (productImage.getId().equals(productImageId)) {
				ProductVariant productVariant = null;
				if (productImageDTO.getProductVariant() != null) {
					productVariant = productVariantRespository.findById(productImageDTO.getProductVariant().getId())
							.get();
				}
				productImage.setUrl(productImageDTO.getUrl());
				productImage.setProductVariant(productVariant);
				productImageRepository.save(productImage);
				break;
			}
		}
	}

	public void deleteProductImage(String email, Integer productId, Integer productImageId) {
		Optional<Product> optionalProduct = getProduct(email, productId);

		if (optionalProduct.isEmpty()) {
			return;
		}

		Product product = optionalProduct.get();

		List<ProductImage> productImages = product.getProductImages();

		for (ProductImage productImage2 : productImages) {
			if (productImage2.getId().equals(productImageId)) {
				productImageRepository.delete(productImage2);
				break;
			}
		}
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
