package com.ws.forum.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImageVo implements Serializable{
	private static final long serialVersionUID = -1848780666776790739L;
	private Integer error = 1;//1：上传成功，0：上传失败
	private String url;		  //图片虚拟地址
	private Integer width;    //图片宽度
	private Integer height;   //图片高度
	//图片上传失败
	public ImageVo() {
		super();
		this.error = 0;
	}
	//图片上传成功，返回完整对象
	public ImageVo(String url, Integer width, Integer height) {
		super();
		this.url = url;
		this.width = width;
		this.height = height;
	}
	
	
}
