package com.lge.stream.terminal;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	// @Disabled
	@Test
	void testCollectPractice() {
		//다음 출력결과를 참고하여 스트림을 HashMap으로 변형하세요

		Stream<Integer> numbers = newIntegerStream();
		HashMap<Integer, String> map =
				numbers.collect(HashMap<Integer, String>::new,
						(m, t)->{m.put(t,"my-value"+t);}, 
						(m, m2)->{m.putAll(m2);});
		System.out.println(map);
		//{1:"my-value=1", 2:"my-value=2", 3:"my-value=3"}
		
		List<Integer> lst = newIntegerStream()
				.collect(Collectors.toList());

	}

	@Disabled
	@Test
	void testCollect() {
		Stream<Integer> s = newIntegerStream();
		ArrayList<Integer> lst = s.collect(ArrayList<Integer>::new,
				ArrayList::add, ArrayList::addAll);
	}

	@Disabled
	@Test
	void testReducePractice2() {
		// ctrl+ shift+o
		List<MemoryPoolMXBean> beans = ManagementFactory
				.getMemoryPoolMXBeans();
//		Stream<MemoryUsage> m =
//				beans.stream().map(bean->bean.getUsage());
		long sum = beans.stream().map(bean -> bean.getUsage())
				.map(usage -> usage.getUsed()).reduce(0L, Long::sum);

		long sum2 = beans.stream().map(bean -> bean.getUsage())
				.mapToLong(usage -> usage.getUsed()).sum();

		long sum3 = beans.stream().map(MemoryPoolMXBean::getUsage)
				.mapToLong(MemoryUsage::getUsed).sum();

//		for (MemoryPoolMXBean bean : beans) {
//			MemoryUsage usage = bean.getUsage();
//			long youngUsedMemory = usage.getUsed();
//			sum += youngUsedMemory;
//		}
		System.out.println(sum);

	}

	@Disabled
	@Test
	void testReducePractice() {
//		IntStream numbers = newIntStream();
//		int sum = numbers.sum();
//		System.out.println(sum);

		IntStream numbers = newIntStream();
		Integer sum = numbers.reduce(0, Integer::sum);
		System.out.println(sum);

	}

	@Disabled
	@Test
	void testReduce1() {
		Stream<Integer> s2 = newIntegerStream();
//		Optional<Integer> ret = s2.reduce(Integer::sum);
//		Integer ret2 = s2.reduce(0, Integer::sum);
//		Integer ret3 = s2.reduce(0, Integer::sum, 
//				(l, r) -> l + r);
		Double ret4 = s2.parallel().reduce(0.0d, (d, i) -> {
			System.out.println("a:" + d + "+" + i);
			return d + i;
		}, (d1, d2) -> {
			System.out.println("c:" + d1 + "+" + d2);
			return d1 + d2;
		});
		System.out.println(ret4);
	}

	OptionalDouble divide(double d) {
		return d == 0 ? OptionalDouble.empty()
				: OptionalDouble.of(1 / d);
	}

	@Disabled
	@Test
	void testTerminalPractice2() {
		divide(4).ifPresent(System.out::println);

	}

	@Disabled
	@Test
	void testTerminalPractice() {
		// ctrl + shift + o

		// 다음 주어진 Stream 에서 >0.2인 평균을 구하고, 화면에 출력하세요.
		// 단, 데이터가 존재하지 않는 경우 "None" 메시지를 화면에 출력하세요
		Random r = new Random();
		DoubleStream ds = DoubleStream.generate(r::nextDouble)
				.limit(100);

		ds.filter(d -> d > 0.2).average().ifPresentOrElse(
				System.out::println,
				() -> System.out.println("None"));

		DoubleStream ds2 = DoubleStream.generate(r::nextDouble)
				.limit(0);
		ds2.filter(d -> d > 0.2).average().ifPresentOrElse(
				System.out::println,
				() -> System.out.println("None"));

		// 다음 주어진 Stream<Integer> 에서 최대값을 구하세요
		// 단, 그 결과 값이 > 23이라면 그 값에 *100을 하여 화면에 출력하세요

		// Random r = new Random();
		Stream<Integer> s = IntStream.generate(r::nextInt).limit(100)
				.boxed();
		Optional<Integer> ret2 = s.max(Comparator.naturalOrder());
		ret2.filter(i -> i > 23).map(i -> i * 100)
				.ifPresent(System.out::println);

	}

	@Disabled
	@Test
	void testTerminal2() {
		OptionalInt r = newIntStream().findAny();
		boolean b = newIntStream().allMatch(i -> i > 0);
	}

	@Disabled
	@Test
	void testOptional() {
		Optional<Integer> min = newIntegerStream()
				.min(Comparator.reverseOrder());
		if (min.isPresent()) {
			System.out.println(min.get());
		}
		min.ifPresent(System.out::println);
		min.ifPresentOrElse(System.out::println, System.out::println);
		min.filter(i -> i > 1).map(String::valueOf)
				.ifPresent(System.out::println);
		Optional<Integer> op2 = min
				.or(() -> Optional.ofNullable(null));
		Integer value2 = min.orElse(5);
		Integer value = min.orElseGet(() -> 5);
		min.orElseThrow(IllegalStateException::new);
	}

	@Disabled
	@Test
	void test() {
		long cnt = newIntegerStream().count();
		Optional<Integer> max = newIntegerStream()
				.max(Comparator.naturalOrder());
		Optional<Integer> min = newIntegerStream()
				.min(Comparator.reverseOrder());

		int sum = newIntStream().sum();
		OptionalDouble avg = newIntStream().average();
		OptionalInt max2 = newIntStream().max();
		OptionalInt min2 = newIntStream().min();
	}

	// CTRL+1
	// ctrl+shift+o
	IntStream newIntStream() {
		return IntStream.of(1, 2, 3);
	};

	Stream<Integer> newIntegerStream() {
		return newIntStream().boxed();
	};

}
