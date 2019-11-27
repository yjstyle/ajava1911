package com.zipcodevalidator.internal;

import java.util.Objects;

import com.zipcodevalidator.api.ZipcodeValidator;

public class ZipcodeValidatorImpl implements ZipcodeValidator {
	@Override
	public boolean validateZipCode(String value) {
		return Objects.isNull(value)?false
				: value.length() == 5;
	}

}
