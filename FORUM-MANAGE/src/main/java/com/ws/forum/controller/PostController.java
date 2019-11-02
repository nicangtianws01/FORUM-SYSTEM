package com.ws.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ws.forum.pojo.Post;
import com.ws.forum.service.PostService;
import com.ws.forum.vo.JsonResult;

@RequestMapping("/post")
@RestController
public class PostController {
	@Autowired
	private PostService postService;
	
	@GetMapping("/doFindPageObjects")
	public JsonResult doFindPageObjects(String keyword,Integer pageCurrent) {
		return JsonResult.successData(postService.findPageObjects(keyword, pageCurrent));
	}
	
	@PostMapping("/doSaveObject")
	public JsonResult doSaveObject(Post entity) {
		postService.saveObject(entity);
		return JsonResult.successMsg("添加成功！");
	}
	
	@PostMapping("/doDeleteObjects")
	public JsonResult doDeleteObjects(Integer... ids) {
		postService.deleteObjects(ids);
		return JsonResult.successMsg("删除成功！");
	}
	@GetMapping("/doGetObjectById")
	public JsonResult doGetObjectById(Integer id) {
		return JsonResult.successData(postService.getObjectById(id));
	}
}
