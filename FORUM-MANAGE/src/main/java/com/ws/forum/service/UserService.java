package com.ws.forum.service;

import com.ws.forum.pojo.User;
import com.ws.forum.vo.PageObject;

public interface UserService {

	int saveUser(User user);

	PageObject<User> findPageObjects(String username, Integer pageCurrent);

}
