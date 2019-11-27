package com.addresschecker.run;

import com.addresschecker.internal.AddressCheckerImpl;

public class Main {

	public static void main(String[] args) {
		addressCheck(); // tdd
	}

	private static void addressCheck() {
		String value = "12345";
		boolean ret = // ctrl + 1
				new AddressCheckerImpl().checkZipCode(value);
		
		if(ret) {
			System.out.println("Zipcode" + value+ " is valid");
		}else {
			System.out.println("Zipcode" + value+ " is invalid");
		}
		
		
	}
	
	

	
	
	
	
	
	
}
