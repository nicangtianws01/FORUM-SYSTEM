package com.ws.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ws.forum.pojo.Post;
import com.ws.forum.pojo.User;
import com.ws.forum.service.PostService;
import com.ws.forum.vo.PageObject;

@RequestMapping("/")
@Controller
public class PageController {
	@Autowired
	private PostService postService;
	@RequestMapping("{model}/{ui}")
	public String doIndexUI(@PathVariable String model,@PathVariable String ui) {
		return model+"/"+ui;
	}
	@GetMapping({"/","index"})
	public String doIndexUI(Model model) {
		User user = new User();
		user.setId(1)
			.setUsername("test");
		model.addAttribute("user", user);
		return "index";
	}
}
