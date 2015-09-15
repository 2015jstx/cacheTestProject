/*  
 * @(#) StdService.java Create on 2015年9月15日 下午1:54:31   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.jeff.test.validator;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.jeff.test.cache.entity.HelloEntity;
import com.util.validator.hibernate.CrossGroup;
import com.util.validator.hibernate.CrossGroupFiled;
import com.util.validator.hibernate.ValidEnum;

/**
 * @StdService.java
 * @created at 2015年9月15日 下午1:54:31 by 253587517@qq.com
 *
 * @desc
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
@Service
@Validated
public class StdService {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public String saveModel(
			@NotNull 
			@CrossGroup(fileds={
					@CrossGroupFiled(key="name",message="名称不能为空",vParams="min=1,max=10",vType=ValidEnum.length)
					,@CrossGroupFiled(key="id",message="{age error}",vParams="min=1,max=130",vType=ValidEnum.range)
					,@CrossGroupFiled(key="idcard",message="{idcard error}",vType=ValidEnum.idCardNumber)
			})
			HelloEntity hello,String a){
		logger.debug("saveModel:{},{}",hello.toString(),a);
		return "ccc";
	}
}
