package com.faciotech.facio.service;

import java.util.HashSet;
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
import com.faciotech.facio.entity.ProductImage;
import com.faciotech.facio.entity.ProductOption;
import com.faciotech.facio.entity.ProductOptionValue;
import com.faciotech.facio.entity.ProductVariant;
import com.faciotech.facio.entity.ProductVariantOption;
import com.faciotech.facio.entity.User;
import com.faciotech.facio.enums.BusinessTypeEnum;
import com.faciotech.facio.repository.AddressRespository;
import com.faciotech.facio.repository.BusinessRespository;
import com.faciotech.facio.repository.ProductImageRepository;
import com.faciotech.facio.repository.ProductOptionRespository;
import com.faciotech.facio.repository.ProductOptionValueRespository;
import com.faciotech.facio.repository.ProductRespository;
import com.faciotech.facio.repository.ProductVariantOptionRespository;
import com.faciotech.facio.repository.ProductVariantRespository;
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
	private final ProductOptionRespository productOptionRespository;
	private final ProductOptionValueRespository productOptionValueRespository;
	private final ProductVariantRespository productVariantRespository;
	private final ProductVariantOptionRespository productVariantOptionRespository;
	private final ProductImageRepository productImageRepository;

	public void addProduct(String email, Product product) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();

		product.setProductCode(generateProductId());
		product.setBusiness(business);

		productRespository.save(product);
	}

	public Product getProductDetails(String email, Integer productId) {
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

	public void updateProduct(String email, Integer productId, Product product) {
		Product myProduct = getProductDetails(email, productId);

		product.setId(productId);
		product.setProductCode(myProduct.getProductCode());
		product.setBusiness(myProduct.getBusiness());

		productRespository.save(product);
	}

	public List<Product> getAllProducts(String email) {
		User user = userRepository.findByEmail(email).get();
		Business business = user.getBusiness();
		List<Product> categoryList = productRespository.findAllProductForBusiness(business.getId());

		return categoryList;
	}

	public void addProductOptions(String email, Integer productId, ProductOption productOption) {
		Product product = getProductDetails(email, productId);

		if (product != null) {
			productOption.setProduct(product);
			productOptionRespository.save(productOption);

			for (ProductOptionValue productOptionValue : productOption.getProductOptionValues()) {
				productOptionValue.setProductOption(productOption);
				productOptionValueRespository.save(productOptionValue);
			}
		}
	}

	public void updateProductOption(String email, Integer productId, Integer productOptionId,
			ProductOption productOption) {
		Product product = getProductDetails(email, productId);

		ProductOption myProductOption = null;

		for (ProductOption productOption2 : product.getProductOptions()) {
			if (productOption2.getId() == productOptionId) {
				myProductOption = productOption2;
				break;
			}
		}

		myProductOption.setName(productOption.getName());
		productOptionRespository.save(myProductOption);

		Set<String> values = new HashSet<String>();

		for (ProductOptionValue productOptionValue : productOption.getProductOptionValues()) {
			values.add(productOptionValue.getName());
		}

		for (ProductOptionValue productOptionValue : myProductOption.getProductOptionValues()) {
			if (values.contains(productOptionValue.getName())) {
				values.remove(productOptionValue.getName());
			} else {
				productOptionValueRespository.delete(productOptionValue);
			}
		}

		for (String value : values) {
			ProductOptionValue productOptionValue = new ProductOptionValue();
			productOptionValue.setName(value);
			productOptionValue.setProductOption(myProductOption);
			productOptionValueRespository.save(productOptionValue);
		}

	}

	public void deleteProductOption(String email, Integer productId, Integer productOptionId) {
		Product product = getProductDetails(email, productId);

		for (ProductOption productOption : product.getProductOptions()) {
			if (productOption.getId() == productOptionId) {
				productOptionRespository.delete(productOption);
				break;
			}
		}
	}

	public void addProductVariant(String email, Integer productId, ProductVariant productVariant) {
		Product product = getProductDetails(email, productId);
		productVariant.setProduct(product);
		List<ProductVariantOption> options = productVariant.getProductVariantOptions();
		productVariantRespository.save(productVariant);

		System.out.println(options.size());

		for (ProductVariantOption productVariantOption : options) {
			System.out.println("Test");
			productVariantOption.setProductVariant(productVariant);
			productVariantOptionRespository.save(productVariantOption);
		}
	}

	public void updateProductVariant(String email, Integer productId, Integer productVariantId,
			ProductVariant productVariant) {
		Product product = getProductDetails(email, productId);
		System.out.println("productVariantId: " + productVariantId);
		for (ProductVariant productVariant2 : product.getProductVariants()) {
			System.out.println("Del: " + productVariant2.getId());
			if (productVariant2.getId().equals(productVariantId)) {
				productVariantRespository.delete(productVariant2);
				System.out.println("Deleted");
				break;
			}
		}

		productVariant.setProduct(product);
		productVariantRespository.save(productVariant);

		for (ProductVariantOption productVariantOption : productVariant.getProductVariantOptions()) {
			productVariantOption.setProductVariant(productVariant);
			productVariantOptionRespository.save(productVariantOption);
		}
	}

	public void deleteProductVariant(String email, Integer productId, Integer productVariantId) {
		Product product = getProductDetails(email, productId);

		for (ProductVariant productVariant2 : product.getProductVariants()) {
			if (productVariant2.getId() == productVariantId) {
				productVariantRespository.delete(productVariant2);
				break;
			}
		}
	}

	public void addProductImage(String email, Integer productId, ProductImage productImage) {
		Product product = getProductDetails(email, productId);

		productImage.setProduct(product);

		productImageRepository.save(productImage);
	}

	public void updateProductImage(String email, Integer productId, Integer productImageId, ProductImage productImage) {
		Product product = getProductDetails(email, productId);
		List<ProductImage> productImages = product.getProductImages();

		for (ProductImage productImage2 : productImages) {
			if (productImage2.getId().equals(productImageId)) {
				productImage2.setProductVariant(productImage.getProductVariant());
				productImageRepository.save(productImage2);
				break;
			}
		}
	}
	
	public void deleteProductImage(String email, Integer productId, Integer productImageId) {
		Product product = getProductDetails(email, productId);
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
