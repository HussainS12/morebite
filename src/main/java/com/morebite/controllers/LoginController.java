package com.morebite.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.morebite.dtos.LoginRequestDTO;
import com.morebite.dtos.LoginResponseDTO;
import com.morebite.services.LoginService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class LoginController {
	
	private final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	@GetMapping("/login")
	public ModelAndView getLoginPage(ModelAndView model, HttpSession session) {
		model.setViewName("login");
		return model;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO userLogin, HttpSession session){
		System.out.println("login request recieved" + userLogin.getUsername() + " " + userLogin.getPassword());
		LoginResponseDTO loginResponse = loginService.loginUser(userLogin);
		if(loginResponse.getCode() == 200) {
			session.setAttribute("username", loginResponse.getUsername());
		}
		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<LoginResponseDTO> logoutUser(HttpSession session){
		session.removeAttribute("username");
		LoginResponseDTO response = new LoginResponseDTO();
		response.setCode(200);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
