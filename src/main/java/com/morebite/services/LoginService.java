package com.morebite.services;

import org.springframework.stereotype.Service;

import com.morebite.dtos.LoginRequestDTO;
import com.morebite.dtos.LoginResponseDTO;
import com.morebite.entities.User;
import com.morebite.repositories.UserRepository;

@Service
public class LoginService {

	private final UserRepository userRepository;
	
	public LoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public LoginResponseDTO loginUser(LoginRequestDTO userLogin) {
		LoginResponseDTO response = new LoginResponseDTO();
		User user = userRepository.findByUsername(userLogin.getUsername()).orElse(null);
		if("admin".equalsIgnoreCase(userLogin.getPassword()) && "admin".equalsIgnoreCase(userLogin.getUsername())) {
			response.setUsername(userLogin.getUsername());
			response.setCode(200);
			response.setMessage("User logged in successfully");
		}
		return response;
	}

}
