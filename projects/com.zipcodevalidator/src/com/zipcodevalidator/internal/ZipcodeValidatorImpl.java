package com.zipcodevalidator.internal;

import java.util.Objects;

import com.zipcodevalidator.api.ZipcodeValidator;
import com.zipvalidator.model.api.ZipCodeValidationResult;

// ctrl + shift + f
public class ZipcodeValidatorImpl implements ZipcodeValidator {
	@Override
	public ZipCodeValidationResult validateZipCode(String value) {
		if (Objects.isNull(value)) {
			return ZipCodeValidationResult.ZIPCODE_NULL;
		} else if (value.length() > 5) {
			return ZipCodeValidationResult.ZIPCODE_LONG;
		} else if (value.length() < 5) {
			return ZipCodeValidationResult.ZIPCODE_SHORT;
		} else {
			return ZipCodeValidationResult.OK;
		}
	}

}
