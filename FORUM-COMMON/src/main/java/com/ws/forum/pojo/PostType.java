package com.ws.forum.pojo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@TableName("post_type")
@Accessors(chain = true)
@Data
public class PostType implements Serializable{
	private static final long serialVersionUID = -7915254639516107133L;
	@TableId(type = IdType.AUTO)
	private Integer id;
	private String name;
	private Integer parentId;
	private Date createdTime;
	private Date updatedTime;
}
