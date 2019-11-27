package com.zipdecorator.internal;

import com.zipdecorator.api.ZipCodeDecorator;

public class ZipCodeDecoratorImpl implements ZipCodeDecorator {

	@Override
	public String appendBrace(String value) {
		return "(" + value+ ")";
	}

}
