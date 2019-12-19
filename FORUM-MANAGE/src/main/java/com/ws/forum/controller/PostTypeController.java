package com.ws.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ws.forum.vo.JsonResult;

@RequestMapping("/post_type/")
@RestController
public class PostTypeController {
	
	@Autowired
	private PostTypeService postTypeService;
	
	@GetMapping("doGetObjects")
	public JsonResult doGetObjects() {
		return JsonResult.successData(postTypeService.getObjects());
	}
}
