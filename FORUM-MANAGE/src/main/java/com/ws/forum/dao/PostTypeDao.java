package com.ws.forum.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.forum.pojo.PostType;

@Mapper
public interface PostTypeDao extends BaseMapper<PostType> {

}
