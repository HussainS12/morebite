package com.morebite.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.morebite.dtos.ProductRequestDTO;
import com.morebite.entities.Product;
import com.morebite.repositories.ProductRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList();
		products = productRepository.findAll();
		return products;
	}
	
	public Product addProduct(Product product) {
//		product.setDescription(product.getProductDesc());
//		product.setImage(product.getImage());
//		product.setName(product.getProductName());
		productRepository.save(product);
		return product;
	}

	public Product getProductById(Long id) {
		Product product = productRepository.findById(id).orElse(null);
		return product;
	}

	public void deleteProduct(Long id) {
		 productRepository.deleteById(id);
	}

}
