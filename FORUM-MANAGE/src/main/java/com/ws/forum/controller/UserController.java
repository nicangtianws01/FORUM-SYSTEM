package com.ws.forum.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ws.forum.pojo.User;
import com.ws.forum.service.GroupService;
import com.ws.forum.service.UserService;
import com.ws.forum.util.ShiroUtils;
import com.ws.forum.vo.CheckBox;
import com.ws.forum.vo.JsonResult;

@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private GroupService groupService;
	
	@GetMapping("/doGetGroupItems")
	public JsonResult doGetGroupItems() {
		List<CheckBox> ckList = groupService.getGroupItems();
		System.out.println(ckList);
		return JsonResult.successData(ckList);
	}
	
	@PostMapping("/doSaveUser")
	public JsonResult saveUser(User user) {
		userService.saveUser(user);
		return JsonResult.successMsg("添加用户成功！");
	}
	
	@PostMapping("/doLogin")
	public JsonResult soLogin(String username,String password,boolean isRememberMe) {
		 //1.封装用户信息
		 UsernamePasswordToken token=new UsernamePasswordToken();
		 token.setUsername(username);
		 token.setPassword(password.toCharArray());
		 //2.提交用户信息(借助Subject对象)
		 //获取Subject对象
		 Subject subject=SecurityUtils.getSubject();
		 //提交用户信息进行认证
		 if(isRememberMe)token.setRememberMe(true);
		 subject.login(token);
		 return JsonResult.successMsg("登录成功！");
	}
	
	@GetMapping("/doGetLoginUser")
	public JsonResult doGetLoginUser() {
		return JsonResult.successData(ShiroUtils.getLoginUser());
	}
	
	@GetMapping("/doFindPageObjects")
	public JsonResult doFindPageObjects(String keyword,Integer pageCurrent) {
		return JsonResult.successData(userService.findPageObjects(keyword, pageCurrent));
	}
	
	
}
