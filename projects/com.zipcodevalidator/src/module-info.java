module com.zipcodevalidator {
	exports com.zipcodevalidator.api to com.addresschecker;
	requires transitive com.zipvalidator.model;
	opens com.zipcodevalidator.internal;
	
	exports com.zipdecorator.api;
	provides com.zipdecorator.api.ZipCodeDecorator
		with com.zipdecorator.internal.ZipCodeDecoratorImpl;
}