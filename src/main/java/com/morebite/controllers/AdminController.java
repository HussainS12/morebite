package com.morebite.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.morebite.dtos.ProductDTO;
import com.morebite.entities.Order;
import com.morebite.entities.Product;
import com.morebite.services.OrderService;
import com.morebite.services.ProductService;
import com.morebite.utils.FileUploadUtil;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class AdminController {
	
	private final ProductService productService;
	private final OrderService orderService;
	
	public AdminController(ProductService productService, OrderService orderService) {
		this.productService = productService;
		this.orderService = orderService;
	}
	
	@GetMapping("/admin")
	public ModelAndView getAdminPage(ModelAndView model, HttpSession session) {
		String username = (String)session.getAttribute("username");
		if(!"admin".equalsIgnoreCase(username)) {
			model.setViewName("404");
			return model;
		}
		model.setViewName("admin");
		return model;
	}
	
	@GetMapping("/admin/order")
	public ModelAndView getOrdersPage(ModelAndView model, HttpSession session) {
		String username = (String)session.getAttribute("username");
		if(!"admin".equalsIgnoreCase(username)) {
			model.setViewName("404");
			return model;
		}
		List<Order> orderList= orderService.getAllOrders();
		model.addObject("orders",orderList);
		model.setViewName("order");
		return model;
	}
	
	@GetMapping("/admin/product")
	public ModelAndView getProductsPage(ModelAndView model, HttpSession session) {
		ProductDTO productDto = new ProductDTO();
		List<Product> productList = productService.getAllProducts();
		model.addObject("products",productList);
		model.addObject("productDto",productDto);
		String username = (String)session.getAttribute("username");
		if(!"admin".equalsIgnoreCase(username)) {
			model.setViewName("404");
			return model;
		}
		model.addObject("productDto", productDto);
		model.setViewName("product");
		return model;
	}

	@PostMapping("/admin/product")
	public ModelAndView addProduct(ModelAndView model, HttpSession session,Product product, @RequestParam("pImage")MultipartFile multipartFile) throws IOException {
		if(!multipartFile.isEmpty()) {
			String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			product.setImage(filename);
			Product savedProduct = productService.addProduct(product);
			String uploadDir = "public/images/" + savedProduct.getId();
			FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
		}
		ProductDTO productDto = new ProductDTO();
		List<Product> productList = productService.getAllProducts();
		model = new ModelAndView(new RedirectView("product"));
		model.addObject("products",productList);
		model.addObject("productDto",productDto);
		model.setViewName("product");
		return model;
	}
	
	@GetMapping("admin/product/delete")
	public ModelAndView deleteProduct(ModelAndView model, @RequestParam Long id) {
		try {
			ProductDTO productDto = new ProductDTO();
			Product product = productService.getProductById(id);
			FileUploadUtil.deleteFile(product.getImage(),id);
			productService.deleteProduct(id);
			List<Product> productList = productService.getAllProducts();
			model = new ModelAndView(new RedirectView("product"));
			model.addObject("products",productList);
			model.addObject("productDto",productDto);
			model.setViewName("product");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
