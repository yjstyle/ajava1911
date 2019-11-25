package com.lge.adjava.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// ctrl+f5
class Main {
	// @Disabled
	@Test
	void testFunctionalPractice2() {
		Runnable task = () -> System.out.println("h");
		// task::run;
		Supplier<Integer> myWork = () -> 0;
		UnaryOperator<Integer> myOperand = (Integer e) -> e
				+ 1;
		DoubleBinaryOperator myBinary = (double l,
				double r) -> l * r;
		Consumer<String> myPinter = (String e) -> System.out
				.println(e);
		Predicate<String> criteria = (
				String e) -> e.length() > 5;
		Callable<Integer> myException = () -> {
			if (5 % 2 > 10) {
				return 0;
			}
			throw new Exception();
		};
	}

	@Test
	void testCompare() {
		Collections.sort(new LinkedList<String>(),
				myComparing(String::length));
	}

	Comparator<String> myComparing(
			Function<String, Integer> f) {
		return (l, r) -> f.apply(l).compareTo(f.apply(r));
	}

	@Disabled
	@Test
	void testFunctionalPractice() {
		Comparator<String> com = (o1, o2) -> Integer
				.compare(o1.length(), o2.length());

		ToIntBiFunction<String, String> tib = (o1,
				o2) -> Integer.compare(o1.length(),
						o2.length());

		LinkedList<String> lst = new LinkedList<>();
		Collections.sort(lst, com);
		Collections.sort(lst, tib::applyAsInt);

	}

	@Disabled
	@Test
	void testMr4Array() {
		List.of(1, 2).stream().toArray(Integer[]::new);
		List.of(1, 2).stream()
				.toArray((int size) -> new Integer[size]);
		myMethod((int size) -> new Integer[size]);
		// input : int 1개
		// output: <Object[]>
	}

	<T> void myMethod(IntFunction<T[]> object) {
		// TODO Auto-generated method stub

	}

	@Disabled
	@Test
	void testMr4Practice() {
		Arrays.stream(new String[] { "1", "2" })
				.map((i) -> Integer.parseInt(i));

		Arrays.stream(new String[] { "1", "2" })
				.map((i) -> new Integer(i));

		// ctrl + 1
		IntStream.range(0, 10).collect(LinkedList::new,
				LinkedList::add, LinkedList::addAll);

		IntStream.range(0, 10).collect(
				() -> new LinkedList<>(),
				(l, i) -> l.add(i), (l, i) -> l.addAll(i));
		// void accept​(T t, int value)
		// boolean add​(E e)

		// void accept​(T t, U u)
		// boolean addAll​(Collection<? extends E> c)
	}

	List<Integer> newSampleList() {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		return list;
	}

	List<Integer> newSampleList(List<Integer> list) {
		list.add(1);
		return list;
	}

	List<Integer> newSampleList(Supplier<List<Integer>> s) {
		List<Integer> list = s.get();
		list.add(1);
		return list;
	}

	@Disabled
	@Test
	void testMr41() {
		// Type-closed method
		// List<Integer> data = newSampleList();

		// Type-closed client
//		List<Integer> data = newSampleList(
//				new ArrayList<Integer>());

		List<Integer> data = newSampleList(
				ArrayList<Integer>::new);

	}

	@Disabled
	@Test
	void testMr4() {
		Arrays.stream(new String[] { "1", "2" })
				.collect(Collectors.toCollection(
						ArrayList<String>::new));

	}

	@Disabled
	@Test
	void testMr2Pratcie2() {
		List.of(1, 2).forEach(System.out::println);

		class Main2 {
			void runFor() {
			}
		}
		Main2 m = new Main2();
		Thread t = new Thread(m::runFor);

		Collections.sort(new LinkedList<String>(),
				String::compareToIgnoreCase);

	}

	@Disabled
	@Test
	void testMr2Pratcie() {
		Collections.sort(new LinkedList<String>(),
				myComparing2(String::length));

	}

	Comparator<String> myComparing2(
			Function<String, Integer> f) {

		return null;// return f.apply(t);
	}

	@Disabled
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
