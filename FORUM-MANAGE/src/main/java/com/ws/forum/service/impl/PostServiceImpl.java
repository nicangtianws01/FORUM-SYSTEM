package com.ws.forum.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ws.forum.dao.PostDao;
import com.ws.forum.exception.ServiceException;
import com.ws.forum.pojo.Post;
import com.ws.forum.service.PostService;
import com.ws.forum.util.ShiroUtils;
import com.ws.forum.vo.PageObject;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostDao postDao;
	
	@Override
	public PageObject<Post> findPageObjects(String keyword, Integer pageCurrent) {
		//1.验证当前页码值是否合法
		if(pageCurrent==null||pageCurrent<1) {
			throw new IllegalArgumentException("当前页码值不正确");
		}
		//2.基于用户名查询总记录数并进行校验
		QueryWrapper<Post> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("title", keyword);
		int rowCount=postDao.selectCount(queryWrapper);
		if(rowCount==0) {
			throw new ServiceException("记录不存在");
		}
		//3.查询当前页记录
		Integer pageSize=10;
		Integer startIndex=(pageCurrent-1)*pageSize;
		List<Post> records=
		postDao.findPageObjects(keyword,startIndex, pageSize);
		//4.封装查询结果并返回
		return new PageObject<Post>(rowCount, records,pageCurrent, pageSize);
	}

	@Override
	public int deleteObjects(Integer[] ids) {
		if(ids.length == 0) {
			throw new IllegalArgumentException("参数错误：ids="+ids);
		}
		List<Integer> idList = Arrays.asList(ids);
		
		return postDao.deleteBatchIds(idList);
	}

	@Override
	public int saveObject(Post entity) {
		if(StringUtils.isEmpty(entity.getTitle())) {
			throw new IllegalArgumentException("标题不能为空");
		}
		if(StringUtils.isEmpty(entity.getContent())) {
			throw new IllegalArgumentException("内容不能为空");
		}
		if(StringUtils.isEmpty(entity.getCover())) {
			entity.setCover("http://image.nicangtianws.com/post/default.jpg");
		}
		entity.setUserId(ShiroUtils.getLoginUser().getId())
			.setTypeId(1).setModifyId(ShiroUtils.getLoginUser().getId())
			.setCreatedTime(new Date())
			.setUpdatedTime(new Date());
		return postDao.insert(entity);
	}

	@Override
	public Post getObjectById(Integer id) {
		return postDao.selectById(id);
	}
	
}
