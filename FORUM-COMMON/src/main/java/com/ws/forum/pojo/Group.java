package com.ws.forum.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("tb_group")
public class Group implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4651728199536990450L;
	@TableId
	private Integer id;
	private String name;
	private Date createdTime;
	private Date updatedTime;
}
