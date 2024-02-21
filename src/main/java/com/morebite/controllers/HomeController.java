package com.morebite.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.morebite.dtos.ProductResponseDTO;
import com.morebite.entities.Product;
import com.morebite.services.ProductService;

@RestController
@RequestMapping("/")
@Slf4j
public class HomeController {
	
	private final ProductService productService;
	
	public HomeController(ProductService productService) {
		this.productService = productService;
	}

    @GetMapping("/")
    public ModelAndView getHomePage(HttpSession session){
        ModelAndView model = new ModelAndView();
        List<Product> productList = productService.getAllProducts();
        model.addObject("products", productList);
        model.setViewName("home");
        return model;
    }
    
    @PostMapping("/addProduct")
    public ResponseEntity<List<ProductResponseDTO>> addProduct(@RequestParam Long id, HttpSession session){
    	List<ProductResponseDTO> products = (ArrayList<ProductResponseDTO>) session.getAttribute("products");
    	if(products == null) {
    		products = new ArrayList<ProductResponseDTO>();
    		Product product = productService.getProductById(id);
        	ProductResponseDTO response = new ProductResponseDTO();
        	response.setAmount(product.getPrice());
    		response.setName(product.getName());
        	products.add(response);
    	}else {
    		Product product = productService.getProductById(id);
        	ProductResponseDTO response = new ProductResponseDTO();
        	response.setAmount(product.getPrice());
    		response.setName(product.getName());
        	products.add(response);
    	}
    	session.setAttribute("products", products);
    	return new ResponseEntity<>(products, HttpStatus.OK);
    }

}
