package com.ws.forum.vo;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * VO:Value Object
 * 通过此对象封装分页数据
 * @author Administrator
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class PageObject<T> implements Serializable{
	private static final long serialVersionUID = -5182062012834261998L;
	/**总记录数*/
	private Integer rowCount;
	/**当前页记录*/
	private List<T> records;
	/**总页数*/
	private Integer pageCount;
	/**当前页码值*/
	private Integer pageCurrent;
	/**页面大小*/
	private Integer pageSize=3;
	public PageObject() {
	}
	public PageObject(Integer rowCount, List<T> records,Integer pageCurrent, Integer pageSize) {
		this.rowCount = rowCount;
		this.records = records;
		this.pageCount = (rowCount-1)/pageSize+1;
		this.pageCurrent = pageCurrent;
		this.pageSize = pageSize;
	}

}







