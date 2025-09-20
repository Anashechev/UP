package com.example.prakt3.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping({"/", "/home"})
	public String home(Authentication auth, Model model) {
		model.addAttribute("username", auth != null ? auth.getName() : "guest");
		return "home";
	}

	@GetMapping("/admin")
	public String admin() { return "admin"; }

	@GetMapping("/manager")
	public String manager() { return "manager"; }

	@GetMapping("/user")
	public String user() { return "user"; }

	@GetMapping("/books/link")
	public String booksLink() { return "redirect:/books"; }

	@GetMapping("/meta/link")
	public String metaLink() { return "redirect:/meta"; }
}


