package com.ws.forum.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("tb_post")
@Accessors(chain = true)
@Data
public class Post implements Serializable{
	private static final long serialVersionUID = -8383013098125162748L;
	@TableId(type = IdType.AUTO)
	private Integer id;
	private String title;
	private String content;
	private String cover;
	private Integer typeId;
	private Integer userId;
	private Integer modifyId;
	private Date createdTime;
	private Date updatedTime;
	
}
