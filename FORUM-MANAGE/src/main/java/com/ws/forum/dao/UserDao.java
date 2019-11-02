package com.ws.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.forum.pojo.User;

@Mapper
public interface UserDao extends BaseMapper<User>{

	List<User> findPageObjects(
			@Param("keyword")String keyword, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);

}
