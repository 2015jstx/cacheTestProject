/*  
 * @(#) HelloController.java Create on 2015年3月7日 下午6:09:31   
 *   
 * Copyright 2015 hiveview.
 */


package com.hiveview.zt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiveview.zt.entity.HelloEntity;
import com.hiveview.zt.service.HelloService;

/**
 * @HelloController.java
 * @created at 2015年3月7日 下午6:09:31 by zhanghongliang@hiveview.com
 *
 * @desc
 *
 * @author  zhanghongliang@hiveview.com
 * @version $Revision$
 * @update: $Date$
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
	@Autowired
	private HelloService helloService;
	
	@RequestMapping("find")
	@ResponseBody
	public Object find(int id){
		return this.helloService.findById(id);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public Object delete(HelloEntity entity){
		return this.helloService.delete(entity);
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public Object update(HelloEntity entity){
		return this.helloService.update(entity);
	}
}
