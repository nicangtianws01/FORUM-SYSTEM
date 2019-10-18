package com.ws.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ws.forum.service.PostService;
import com.ws.forum.vo.JsonResult;

@RequestMapping("/post/")
@RestController
public class PostController {
	@Autowired
	private PostService postService;
	
	@GetMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String keyword,Integer pageCurrent) {
		return new JsonResult(postService.findPageObjects(keyword,pageCurrent));
	}
	@PostMapping("doDeleteObjects")
	public JsonResult doDeleteObjects(Integer... ids) {
		postService.deleteObjects(ids);
		return new JsonResult("删除成功！");
	}
}
