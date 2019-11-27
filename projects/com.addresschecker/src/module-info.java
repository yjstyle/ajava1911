module com.addresschecker {
	exports com.addresschecker.api;
	requires com.zipcodevalidator;
	
	uses com.zipdecorator.api.ZipCodeDecorator;
}