package com.addresschecker.run;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.addresschecker.internal.AddressCheckerImpl;
import com.zipdecorator.api.ZipCodeDecorator;
import com.zipdecorator.api.ZipCodeDecoratorFactory;

public class Main {

	public static void main(String[] args) {
		decorateMessage();

		// addressCheck(); // tdd
	}

	private static void decorateMessage() {
		String value = "12345";
		ServiceLoader<ZipCodeDecorator> sl = ServiceLoader
				.load(ZipCodeDecorator.class);
		Iterator<ZipCodeDecorator> iter = sl.iterator();
		if (iter.hasNext()) {
			ZipCodeDecorator provider = iter.next();
			System.out.println(provider.appendBrace(value));
		}

//		String value = "12345";
//		ZipCodeDecorator deco = ZipCodeDecoratorFactory.newDecorator();
//		System.out.println(deco.appendBrace(value));
		// 12345 -> (12345)

	}

	private static void addressCheck() {
		String value = "12345";
		boolean ret = // ctrl + 1
				new AddressCheckerImpl().checkZipCode(value);

		if (ret) {
			System.out.println("Zipcode" + value + " is valid");
		} else {
			System.out.println("Zipcode" + value + " is invalid");
		}

	}

}
