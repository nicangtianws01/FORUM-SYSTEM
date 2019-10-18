package com.ws.forum.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ws.forum.pojo.User;

@Mapper
public interface UserDao extends BaseMapper<User>{

}
