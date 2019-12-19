package com.ws.forum.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ws.forum.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ws.forum.dao.UserDao;
import com.ws.forum.pojo.User;
import com.ws.forum.service.UserService;
import com.ws.forum.vo.PageObject;

@Transactional(rollbackFor = Throwable.class,
			propagation = Propagation.REQUIRED,
			readOnly = false)
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public int saveUser(User entity) {
		//1.判断信息是否完整
		if(entity==null) {
			throw new ServiceException("保存对象不能为空");
		}
		if(StringUtils.isEmpty(entity.getUsername())) {
			throw new ServiceException("用户名不能为空");
		}
		if(StringUtils.isEmpty(entity.getPassword())) {
			throw new ServiceException("密码不能为空");
		}
		if(entity.getGroupId()==null || entity.getGroupId()==0) {
			throw new ServiceException("至少要为用户分配角色");
		}
		//2.保存用户自身信息
		//2.1对密码进行加密
    	String source=entity.getPassword();
    	String salt=UUID.randomUUID().toString();
    	SimpleHash sh=new SimpleHash(//Shiro框架
    			"MD5",//algorithmName 算法
    			 source,//原密码
    			 salt, //盐值
    			 1);//hashIterations表示加密次数
    	entity.setSalt(salt);
    	entity.setPassword(sh.toHex());
    	//3.设置测试默认信息
		entity.setAvatar("http://image.nicangtianws.com/avatar/default.png")
		.setValid(1)
		.setCreatedTime(new Date()).setUpdatedTime(new Date());
		int rows=userDao.insert(entity);
		return rows;
	}

	@Transactional(readOnly = true)
	@Override
	public PageObject<User> findPageObjects(String keyword, Integer pageCurrent) {
		//1.验证当前页码值是否合法
		if(pageCurrent==null||pageCurrent<1) {
			throw new IllegalArgumentException("当前页码值不正确");
		}
		//2.基于用户名查询总记录数并进行校验
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("username", keyword);
		int rowCount=userDao.selectCount(queryWrapper);
		if(rowCount==0) {
			throw new ServiceException("记录不存在");
		}
		//3.查询当前页记录
		Integer pageSize=5;
		Integer startIndex = (pageCurrent-1)*pageSize;
		List<User> records = userDao.findPageObjects(keyword,startIndex, pageSize);
		//4.封装查询结果并返回
		return new PageObject<User>(rowCount, records,pageCurrent, pageSize);
	}

}
