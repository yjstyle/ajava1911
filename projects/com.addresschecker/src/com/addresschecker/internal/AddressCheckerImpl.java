package com.addresschecker.internal;

import java.util.Objects;

import com.addresschecker.api.AddressChecker;
import com.zipcodevalidator.api.ZipcodeValidatorFactory;

public class AddressCheckerImpl implements AddressChecker {
	public boolean checkZipCode(String value) {
		return ZipcodeValidatorFactory.getInstance()
				.validateZipCode(value);
	}

}










