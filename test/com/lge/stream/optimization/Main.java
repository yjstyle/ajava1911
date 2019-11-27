package com.lge.stream.optimization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {

	<T> Supplier<T> unchecked(Callable<T> c) {
		return () -> {
			try {
				return c.call();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

//	@Disabled
	@Test
	void testException() {
		final Path P = Paths.get("setupact.log");
		Supplier<String> s = unchecked(() -> {
			return new String(Files.readAllBytes(P), StandardCharsets.UTF_8);
		});

	}

	@Disabled
	@Test
	void testState() {
		Stream<Integer> s1 = Stream.of(1, 2, 3);
		Stream<Integer> s2 = Stream.of(4, 5, 6).parallel();
		Stream<Integer> s3 = Stream.concat(s1, s2); // flatmap

//		s1.peek(action)
		s1.forEachOrdered(System.out::println);

	}

	@Disabled
	@Test
	void testPrimitiveStream() {
		// Stream.of(1,2,3).mapToInt(wi->wi.intValue());
		IntStream i = Stream.of(1, 2, 3).mapToInt(Integer::intValue);
		Stream<Integer> si = i.boxed();

		// IntStream.iter
		IntStream.rangeClosed(0, 10).forEach((int ii) -> {
			//
		});
	}

	@Disabled
	@Test
	void test2() {
		// 3354
		// 3403

		long now = System.currentTimeMillis();
		IntStream.iterate(1, i -> i + 1).limit(Integer.MAX_VALUE)
				.forEach((i) -> {
				});
		long e = System.currentTimeMillis() - now;
		System.out.println(e);

	}

	@Disabled
	@Test
	void test() {
		int i = 1;
		// 선언할떄
		Integer i2 = 2;// primitive -> wrapper
		// boxing, autoboxing

		// 변수할당할떄
		i2 = i;

		// 메소드호출할때
		List<Integer> lst = List.of(1);
		lst.add(1);

	}

	// ctrl+1
	IntStream newIntStream() {
		return IntStream.rangeClosed(0, 100);
	}

	Stream<Integer> newObjStream() {
		return Stream.iterate(0, i -> i + 1).limit(100);
	}

}
