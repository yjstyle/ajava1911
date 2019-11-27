package com.lge.lvt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	// ctrl + 1
	// ctrl + shift + o
	// ctrl + shift + f 예쁘게
	String readLine(Socket socket, String cn) {
		try (
				var is = socket.getInputStream();
				var isr = new InputStreamReader(is, cn);
				var buf = new BufferedReader(isr)
		) {
			return buf.readLine();
		} catch (IOException | IllegalStateException e) {
		}
		return "";
	}

	void removeMatches(Map<? extends String, ? extends Number> map, int max) {
		var iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			var entry = iterator.next();
		}
	}

	@Disabled
	@Test
	void testLiteral() {
		var creditCardnumber = 1111_2222____________3L;
		var binary = (int) 0b0000_1111_1110_1111L;
		var hex = (int) 0x0000_1111;

		double d = 5.0D;
		float f = 6.0f;

		byte flags = 0;
		short mask = 0x7fff;
		int int_ = 5;

		var flags2 = (byte) 0;
		var mask2 = (short) 0x7fff;
		var int_2 = (int) 5;

		long long_ = 1l;

		boolean isRead = false;
		char c = '\uffff';
		String s = "string";

		var isRead2 = false;
		var c2 = '\uffff';
		var s2 = "string";
	}

	@Disabled
	@Test
	void testInterfaceProgramming() {
		List<Integer> list = new ArrayList<Integer>();

		var list2 = new ArrayList<Integer>();
		list2.trimToSize();
		list2.ensureCapacity(10);

		List<Integer> list3 = new ArrayList<Integer>();
		List<Integer> list4 = new ArrayList<>();
		var list5 = new ArrayList<Integer>();
		var var = 6; // 3곳, 변수초기화, for-loop, loop변수
	}

	@Disabled
	@Test
	void testSplitCommand() {
		var map = newStringList().stream()
				.collect(Collectors.groupingBy(s -> s, Collectors.counting()));

		var max = map.entrySet().stream().max(Map.Entry.comparingByValue());

		max.map(Map.Entry::getKey).ifPresent(System.out::println);

	}

	List<String> newStringList() {
		return Arrays.asList(("It felt in the beginning like things "
				+ "could get messier: hundreds of angry "
				+ "students had gathered outside "
				+ "the gate of the venue of the graduation "
				+ "ceremony in south Delhi and were demanding a "
				+ "meeting with the vice-chancellor who they alleged "
				+ "had been avoiding them.").split(" "));
	}

	OutputStream newSystemOutstreamForDataread() {
		return System.out;
	}

	@Test
	void testGuidelines() {
		List<Integer> l = List.of(1, 2, 3);
		var productLst = List.of(1, 2, 3);
		// ByteArrayOutputStream o = new ByteArrayOutputStream(System.out);
		var systemOutput = newSystemOutstreamForDataread();
	}

	@Disabled
	@Test
	void testNo() {
		// var i;
		// var i =1, i2 =2, i3 = 3;
		// var array = {1,2,3};

		var array = new int[] { 1, 2, 3 };
		var array2 = array;

		// var run = () ->{}; labmda안되요
		Runnable run = () -> {
		};
		// var run2 = run::run;
		Consumer<Integer> c = (var i) -> System.out.println(i);
	}

	@Disabled
	@Test
	void test() {
		var name = 1; // 1
		for (var i = 0; i < 5; i++) { // 2
		}
		// IntStream.range(0, 10)
		for (var i2 : List.of(1, 2, 3)) {

		}
		while (true) { // 3
			var name2 = 5;
		}
	}

}
