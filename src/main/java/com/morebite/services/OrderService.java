package com.morebite.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.morebite.dtos.OrderDto;
import com.morebite.dtos.OrderResponseDTO;
import com.morebite.entities.Order;
import com.morebite.repositories.OrderRepository;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	public Map<String,String> governates = new HashMap<>();

	public OrderResponseDTO placeOrder(OrderDto request) {
		declareGovernates();
		Order order = new Order();
		order.setCustomerAddress1(request.getAddress1());
		order.setCustomerAddress2(request.getAddress2());
		order.setCustomerEmail(request.getEmail());
		order.setCustomerFName(request.getFname());
		order.setCustomerLName(request.getLname());
		order.setDeliveryCharges(request.getDeliveryCharge());
		order.setGovernate(governates.get(request.getGovernate()));
		order.setMop(request.getMop());
		order.setTotalAmount(request.getTotalAmount());
		order.setStatus("P");
		order.setProducts(request.getProducts());
		
		Order orders = orderRepository.save(order);
		OrderResponseDTO response = new OrderResponseDTO();
		response.setCode("200");
		response.setMessage("Order Placed Successfully");
		response.setId(String.valueOf(order.getId()));
		
		
		return response;
	}

	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders;
	}
	
	public void declareGovernates()
	{
		governates.put("1", "Al Asimah");
		governates.put("2", "Hawalli");
		governates.put("3", "Farwaniya");
		governates.put("4", "Mubarak Al-Kabeer");
		governates.put("5", "Ahmadi");
		governates.put("6", "Jahra");
		
		
	}
}
