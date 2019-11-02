package com.ws.forum.util;
import org.apache.shiro.SecurityUtils;

import com.ws.forum.pojo.User;
public class ShiroUtils {
	 /**获取登录用户*/
	 public static String getUsername() {
    	 User user=(User)
    	 SecurityUtils.getSubject().getPrincipal();
    	 return user.getUsername();
	 }

	 public static User getLoginUser() {
		 User user=(User)
				 SecurityUtils.getSubject().getPrincipal();
		 return user;
	 }
}





