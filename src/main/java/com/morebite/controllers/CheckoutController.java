package com.morebite.controllers;

import java.util.ArrayList;
import java.util.List;import org.hibernate.Length;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.morebite.dtos.OrderDto;
import com.morebite.dtos.OrderResponseDTO;
import com.morebite.dtos.ProductResponseDTO;
import com.morebite.services.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class CheckoutController {
	
	private final OrderService orderService;
	
	public CheckoutController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/checkout")
	public ModelAndView getCheckoutPage(ModelAndView model, HttpSession session) {
		List<ProductResponseDTO> products = (ArrayList<ProductResponseDTO>) session.getAttribute("products");
		model.addObject("products", products);
		model.setViewName("checkout");
		return model;
	}
	
	@GetMapping("/getTotal")
	public String getTotalAmount(ModelAndView model, HttpSession session) {
		List<ProductResponseDTO> products = (ArrayList<ProductResponseDTO>) session.getAttribute("products");
		Double sum = 0.0;
		if(products !=null) {
			for(ProductResponseDTO product : products) {
				sum = sum + Double.parseDouble(product.getAmount());
			}
		}
		model.addObject("products", products);
		model.setViewName("checkout");
		return sum.toString();
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderDto request, HttpSession session){
		List<ProductResponseDTO> products = (ArrayList<ProductResponseDTO>) session.getAttribute("products");
		String productNames = "";
		if(products !=null) {
			for(ProductResponseDTO product : products) {
				productNames = productNames + product.getName() + ",";
			}
			productNames.substring(0,productNames.length()-2);
		}
		Double sum = 0.0;
		if(products !=null) {
			for(ProductResponseDTO product : products) {
				sum = sum + Double.parseDouble(product.getAmount());
			}
		}
		Double dc = Double.parseDouble(request.getDeliveryCharge());
		Double total = sum + dc;
		request.setProducts(productNames);
		request.setTotalAmount(total.toString());
		OrderResponseDTO response = orderService.placeOrder(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/order")
	public ModelAndView orderPlaced(ModelAndView model, @RequestParam String id) {
		model.addObject("orderId", id);
		model.setViewName("orderPlaced");
		return model;
	}

}
