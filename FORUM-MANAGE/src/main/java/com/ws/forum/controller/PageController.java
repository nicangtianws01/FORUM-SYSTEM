package com.ws.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ws.forum.pojo.User;

@RequestMapping("/")
@Controller
public class PageController {
	
	@GetMapping({"/","index"})
	public String doIndexUI(Model model) {
		User user = new User();
		user.setId(1).setUsername("test");
		model.addAttribute("user", user);
		return "index";
	}
}
