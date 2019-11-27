package com.zipcodevalidator.api;

import com.zipcodevalidator.internal.ZipcodeValidatorImpl;

public class ZipcodeValidatorFactory {

	public static ZipcodeValidator getInstance() {
		return new ZipcodeValidatorImpl();
	}

}









