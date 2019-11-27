package com.zipcodevalidator.api;

import com.zipvalidator.model.api.ZipCodeValidationResult;

public interface ZipcodeValidator {
	// ctrl+1
	ZipCodeValidationResult validateZipCode(String value);

}
