package com.tourist.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tourist.Entity.CustomUserDetails;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@RequestMapping("/welcome")
	public String adminPage(Model model) {
		model.addAttribute("mess", "Welcome to admin page");

		CustomUserDetails user =  (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("user", user);
		return "admin";
	}

}
