/*  
 * @(#) FastJSONTest.java Create on 2015年9月15日 下午4:37:51   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.jeff.test.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.jeff.test.cache.entity.HelloEntity;

/**
 * @FastJSONTest.java
 * @created at 2015年9月15日 下午4:37:51 by 253587517@qq.com
 *
 * @desc
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
public class FastJSONTest {
	@Test
	public void test_fastJSON() throws Exception {
		HelloEntity e = new HelloEntity(1, "a", new Date());
		String str = JSONObject.toJSONString(e);

		SerializeConfig mapping = new SerializeConfig();  
//		mapping.put(Date.class, new ObjectSerializer() {
//			private FastDateFormat fdf = FastDateFormat.getInstance("yyyyMMdd");
//			@Override
//			public void write(JSONSerializer serializer, Object object,
//					Object fieldName, Type fieldType) throws IOException {
//				fdf.format(object);
//			}
//		});
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyyMMdd"));
		System.out.println(str);
		
		System.out.println("2:" + JSONObject.toJSONString(e, mapping ));
		str = JSONObject.toJSONStringWithDateFormat(e, "yyyy-MM-dd HH:mm:ss");
		System.out.println("3:" + str);
		
		HelloEntity t = JSONObject.parseObject(str, HelloEntity.class);
		
		System.out.println("4:" + t);
	}
	
	@Test
	public void test_fastjsonList() throws Exception {
		List<HelloEntity> list =new ArrayList<HelloEntity>();
		list.add(new HelloEntity(1, "a", new Date()));
		list.add(new HelloEntity(2, "b", new Date()));
		
		String lista_str = JSONArray.toJSONStringWithDateFormat(list, "yyyy-MM-dd");
		System.out.println(lista_str);
		
		List<HelloEntity> l2 = JSON.parseArray(lista_str, HelloEntity.class);
		System.out.println(l2);
	}
}
