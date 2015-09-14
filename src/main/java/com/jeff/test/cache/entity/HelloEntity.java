/*  
 * @(#) HelloEntity.java Create on 2015年3月7日 下午6:11:55   
 *   
 * Copyright 2015 hiveview.
 */


package com.jeff.test.cache.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @HelloEntity.java
 * @created at 2015年3月7日 下午6:11:55 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
public class HelloEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Date dt;
	
	@Override
	public String toString() {
		return "HelloEntity [id=" + id + ", name=" + name + ", dt=" + dt + "]";
	}
	public HelloEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public HelloEntity(int id, String name, Date dt) {
		super();
		this.id = id;
		this.name = name;
		this.dt = dt;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDt() {
		return dt;
	}
	public void setDt(Date dt) {
		this.dt = dt;
	}
}
