package com.example.prakt3.web;

import com.example.prakt3.domain.Role;
import com.example.prakt3.service.RegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class AuthController {
	private final RegistrationService registrationService;

	public AuthController(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/register")
	public String registerForm() {
		return "register";
	}

	@PostMapping("/register")
	public String register(@RequestParam String username,
						 @RequestParam String password,
						 Model model) {
		try {
			registrationService.registerUser(username, password, Collections.singleton(Role.USER));
			return "redirect:/login?registered";
		} catch (IllegalArgumentException ex) {
			model.addAttribute("error", ex.getMessage());
			return "register";
		}
	}
}
