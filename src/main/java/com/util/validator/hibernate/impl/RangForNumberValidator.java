/*  
 * @(#) RangValidatorForNumber.java Create on 2015年9月15日 下午2:42:49   
 *   
 * Copyright 2015 .ooyanjing
 */


package com.util.validator.hibernate.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Max;

/**
 * @RangValidatorForNumber.java
 * @created at 2015年9月15日 下午2:42:49 by 253587517@qq.com
 *
 * @desc
 *
 * @author  zhanghongliang@ooyanjing.com
 * @version $Revision$
 * @update: $Date$
 */
public class RangForNumberValidator implements ConstraintValidator<Max, Number> {

	private long max;
	private long min;

	public void initialize(Max maxValue) {
		this.max = maxValue.value();
	}

	public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
		//null values are valid
		if ( value == null ) {
			return true;
		}
		if ( value instanceof BigDecimal ) {
			return ( ( BigDecimal ) value ).compareTo( BigDecimal.valueOf( max ) ) != 1;
		}
		else if ( value instanceof BigInteger ) {
			return ( ( BigInteger ) value ).compareTo( BigInteger.valueOf( max ) ) != 1;
		}
		else {
			long longValue = value.longValue();
			return longValue <= max && min<=longValue;
		}
	}
}