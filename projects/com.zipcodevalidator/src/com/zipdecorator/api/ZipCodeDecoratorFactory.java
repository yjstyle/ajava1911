package com.zipdecorator.api;

import com.zipdecorator.internal.ZipCodeDecoratorImpl;

public class ZipCodeDecoratorFactory {

	public static ZipCodeDecorator newDecorator() {
		return new ZipCodeDecoratorImpl();
	}

}
