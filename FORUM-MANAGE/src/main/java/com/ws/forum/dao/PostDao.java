package com.ws.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.forum.pojo.Post;

@Mapper
public interface PostDao extends BaseMapper<Post>{

	int getRowCount(String keyword);

	List<Post> findPageObjects(
			@Param("keyword")String keyword, 
			@Param("startIndex")Integer startIndex, 
			@Param("pageSize")Integer pageSize);

}
