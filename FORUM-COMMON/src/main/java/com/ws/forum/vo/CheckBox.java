package com.ws.forum.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class CheckBox implements Serializable{
	private static final long serialVersionUID = -5183275894740969974L;
	private Integer id;
	private String name;
	public CheckBox(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
}
