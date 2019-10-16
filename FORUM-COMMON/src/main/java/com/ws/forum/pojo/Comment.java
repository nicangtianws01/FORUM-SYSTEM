package com.ws.forum.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("comment")
@Accessors(chain = true)
@Data
public class Comment implements Serializable{
	private static final long serialVersionUID = -3922570378612847213L;
	@TableId(type = IdType.AUTO)
	private Integer id;
	private Integer postId;
	private Integer userId;
	private String content;
	private Date createdTime;
}
