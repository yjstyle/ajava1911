package com.lge.adjava.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// ctrl+f5
class Main {
	@Test
	void testMR2() {
		class ComparisonProvider {
			int compareByName(String a, String b) {
				return a.compareTo(b);
			}
		}
		String[] stringArray = { "Barbara", "James" };
		
		ComparisonProvider cp = new ComparisonProvider();
		Arrays.sort(stringArray, cp::compareByName);

	}

	@Disabled
	@Test
	void testMR1() {
		Collections.sort(new LinkedList<Integer>(),
				Integer::compare);

	}

	@Disabled
	@Test
	void testPractice3() {
		Arrays.asList(5, 2, 6).stream()
				.forEach((t) -> System.out.println(t));
		List<String> cos = List.of(Locale.getISOCountries())
				.stream()

				.map(String::toLowerCase)

				.collect(Collectors.toList());

	}

	@Disabled
	@Test
	void testPractice2() {
		// ctrl+1
		Callable<List<String>> c = () -> List.of();

	}

	@Disabled
	@Test
	void testPractice() {
		// ctrl + 1
		// ctrl + shift+ f
		Collections.sort(new LinkedList<String>(),
				(o1, o2) -> Integer.compare(o1.length(),
						o2.length())

		);

	}

	@Disabled
	@Test
	void test() {
		// ctrl + f11
		Thread t = new Thread(() -> {
			System.out.println("Running");
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // ctrl+1
	}

}
