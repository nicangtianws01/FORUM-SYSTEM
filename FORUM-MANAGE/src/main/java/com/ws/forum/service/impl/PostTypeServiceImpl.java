package com.ws.forum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ws.forum.controller.PostTypeService;
import com.ws.forum.dao.PostTypeDao;
import com.ws.forum.pojo.PostType;

@Service
public class PostTypeServiceImpl implements PostTypeService{

	@Autowired
	private PostTypeDao postTypeDao;
	
	@Override
	public List<PostType> getObjects() {
		return postTypeDao.selectList(null);
	}

}
