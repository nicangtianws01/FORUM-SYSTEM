package com.ws.forum.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_user")
@Accessors(chain = true)
@Data
public class User implements Serializable{
	private static final long serialVersionUID = -8000259551606832726L;
	@TableId(type = IdType.AUTO)
	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String introduce;
	private String email;
	private String mobile;
	private String avatar;
	private Integer valid;
	private Integer groupId;
	private Date createdTime;
	private Date updatedTime;
}
