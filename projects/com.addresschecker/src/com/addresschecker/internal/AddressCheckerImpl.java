package com.addresschecker.internal;

import com.addresschecker.api.AddressChecker;
import com.zipcodevalidator.api.ZipcodeValidator;
import com.zipcodevalidator.api.ZipcodeValidatorFactory;
import com.zipvalidator.model.api.ZipCodeValidationResult;

public class AddressCheckerImpl implements AddressChecker {
	public boolean checkZipCode(String value) {
//		return ZipCodeValidationResult.OK.equals(
//				ZipcodeValidatorFactory.getInstance().validateZipCode(value));
		ClassLoader classLoader = AddressCheckerImpl.class.getClassLoader();
		try {
			String apath = "com.zipcodevalidator.internal.ZipcodeValidatorImpl";
			Class aClass = classLoader.loadClass(apath);
			return ((ZipcodeValidator) aClass.newInstance())
					.validateZipCode(value) == ZipCodeValidationResult.OK;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
