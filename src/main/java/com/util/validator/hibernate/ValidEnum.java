/*  
 * @(#) aa.java Create on 2015年8月5日 上午11:38:19   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.util.validator.hibernate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.impl.CreditCardNumberValidator;
import org.hibernate.validator.constraints.impl.EmailValidator;
import org.hibernate.validator.constraints.impl.LengthValidator;
import org.hibernate.validator.constraints.impl.NotBlankValidator;
import org.hibernate.validator.constraints.impl.NotNullValidator;

import com.util.validator.hibernate.impl.IdCardNumberValidator;
import com.util.validator.hibernate.impl.RangForNumberValidator;

/**
 * @ValidEnum.java
 * @created at 2015年8月5日 上午11:38:19 by zhanghongliang@ooyanjing.com
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
@SuppressWarnings("unchecked")
public enum ValidEnum {
	notNull{
		@Override
		public ConstraintValidator<NotNull,Object> getValidator(
				String params) {
			ConstraintValidator<NotNull, Object> result = new NotNullValidator();
			return result;
		}
	},
	notBlank {
		@Override
		public ConstraintValidator<NotBlank, String> getValidator(
				String params) {
			ConstraintValidator<NotBlank, String> result = new NotBlankValidator();
			initConstrainValidator(result, params);
			return result;
		}
	},
	length {
		@Override
		public ConstraintValidator<Length,String> getValidator(
				String params) {
			ConstraintValidator<Length, String> result = new LengthValidator();
			initConstrainValidator(result, params);
			return result;
		}
	},
	range {
		@Override
		public ConstraintValidator<Max,Number> getValidator(
				String params) {
			ConstraintValidator<Max, Number> result = new RangForNumberValidator();
			initConstrainValidator(result, params);
			return result;
		}
	},
	email{
		@Override
		public ConstraintValidator<Email, String> getValidator(
				String params) {
			ConstraintValidator<Email, String> result = new EmailValidator();
			//initConstrainValidator(result, params);
			return result;
		}
	},
	creditCardNumber{
		@Override
		public ConstraintValidator<CreditCardNumber, String> getValidator(
				String params) {
			ConstraintValidator<CreditCardNumber, String> result = new CreditCardNumberValidator();
			//initConstrainValidator(result, params);
			return result;
		}
	},
	idCardNumber{
		@Override
		public ConstraintValidator<IdCardNumber, String> getValidator(
				String params) {
			ConstraintValidator<IdCardNumber, String> result = new IdCardNumberValidator();
			//initConstrainValidator(result, params);
			return result;
		}
	};
	

	public abstract <T extends ConstraintValidator<?, Object>> T getValidator(
			String params);

	void initConstrainValidator(ConstraintValidator<?, ?> instance,
			String params) {
		if (instance != null && StringUtils.isNotBlank(params)) {
			Map<String, String> map = new HashMap<String, String>();
			try {
				String[] fileds = StringUtils.split(params, ",");
				for (String filed : fileds) {
					String[] aa = StringUtils.split(filed, "=");
					map.put(aa[0], aa[1]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (Map.Entry<String, String> entry : map.entrySet()) {
				try {
					Field filed = instance.getClass().getDeclaredField(entry.getKey());
					if(filed!=null){
						filed.setAccessible(true);
						Class<?> c = filed.getType();
						
						Object value = entry.getValue();
						String classType = c.toString().toLowerCase();
						if(classType.equals("integer")||c.toString().equals("int")){
							value = NumberUtils.toInt((String)value, 0);
						}else if(c.toString().equals("long")){
							value = NumberUtils.toLong((String)value, 0);
						}
						filed.set(instance, value);
					}
					
//					BeanUtils.setProperty(instance, entry.getKey(),
//							entry.getValue());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
