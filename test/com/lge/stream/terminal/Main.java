package com.lge.stream.terminal;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collector.Characteristics;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	//@Disabled
	@Test
	void testCollectorHelperPractice3() {
		Stream<Integer> numbers = newIntegerStream();
		newIntStream().average();
		OptionalDouble od = numbers.collect(
				Collector.of(() -> new long[2], (a, i) -> {
					a[0] += i;
					a[1]++;
				}, (a1, a2) -> {
					a1[0] += a2[0];
					a1[1] += a2[1];
					return a1;
				}, (l) -> {
					return l[1] == 0 ? OptionalDouble.empty()
							: OptionalDouble.of(l[0] / l[1]);
				}));
		
		od.ifPresent(System.out::println);
		/*
		 * 
		 *  (a, i)->{
		a[0]+=i;
		a[1]++;
	}, (a1, a2)->{
		a1[0]+=a2[0];
		a1[1]+=a2[1];
		return a1;
	}, (l) -> l[1]==0? OptionalDouble.empty(): l[0]/l[1],
			Characteristics.CONCURRENT))
		 */

		od.ifPresent(System.out::println);

	}

	@Disabled
	@Test
	void testCollectorHelperPractice2() {
//		Memory Pool로 현재 시스템의 메모리사용량에 대해(getUsed) 
//		DoubleSummaryStatistics 을 구하세요

		List<MemoryPoolMXBean> beans = ManagementFactory
				.getMemoryPoolMXBeans();

		DoubleSummaryStatistics dss = beans.stream()
				.map((MemoryPoolMXBean bean) -> bean.getUsage())
				.collect(Collectors.summarizingDouble(
						(MemoryUsage t) -> t.getUsed()));
		System.out.println(dss);
		MemoryUsage usage = beans.get(0).getUsage();
		long youngUsedMemory = usage.getUsed();

//		{count=8, sum=34289560.000000, min=0.000000,
//		average=4286195.000000, max=22020096.000000}

	}

	@Disabled
	@Test
	void testCollectorHelperPractice() {
		class Customer {
			String name;
			int points;

			Customer(String name, int points) {
				this.name = name;
				this.points = points;
			}

			int getPoints() {
				return this.points;
			}

			String getName() {
				return this.name;
			}
		}
		List<Customer> customers = List.of(
				new Customer("John P.", 15),
				new Customer("Sarah M.", 200),
				new Customer("Charles B.", 150),
				new Customer("Mary T.", 1));
//		John P.Sarah M.Charles B.Mary T.
//		366

		String seq = customers.stream().map(c -> c.getName())
				.collect(Collectors.joining());
		System.out.println(seq);
		Integer sum = customers.stream().map(c -> c.getPoints())
				.reduce(0, (l, r) -> l + r);

	}

	@Disabled
	@Test
	void testCollectorHelper() {
		List<Integer> lst = newIntegerStream().collect(
				Collectors.toCollection(LinkedList<Integer>::new));
		newIntegerStream().collect(Collectors.toUnmodifiableList());

		Collections.unmodifiableList(
				(newIntegerStream().collect(Collectors.toList())));

		String str = newIntegerStream().map(String::valueOf)
				.collect(Collectors.joining());
		System.out.println(str);

		DoubleSummaryStatistics s = newIntegerStream().collect(
				Collectors.summarizingDouble(Integer::intValue));
		System.out.println(s);

	}

	@Disabled
	@Test
	void testCollectOf() {
		Stream<Integer> s = newIntegerStream();
		ArrayList<Integer> lst = s.collect(newListColletor());
		// ArrayList<Integer>::new,
		// ArrayList::add, ArrayList::addAll
	}

	static Collector<Integer, ArrayList<Integer>, ArrayList<Integer>> newListColletor() {
		return Collector.of(ArrayList<Integer>::new, ArrayList::add,
				(l, r) -> {
					l.addAll(r);
					return l;
				});
	}

	@Disabled
	@Test
	void testCollect2() {
		// ctrl 1
		List<Integer> lst = newIntegerStream().collect(
				new Collector<Integer, List<Integer>, List<Integer>>() {
					@Override
					public Supplier<List<Integer>> supplier() {
						return LinkedList<Integer>::new;
					}

					@Override
					public BiConsumer<List<Integer>, Integer> accumulator() {
						return (lst, t) -> lst.add(t);
					}

					@Override
					public BinaryOperator<List<Integer>> combiner() {
						return (l, r) -> {
							l.addAll(r);
							return l;
						};
					}

					@Override
					public Function<List<Integer>, List<Integer>> finisher() {
						return Function.identity();
					}

					@Override
					public Set<Characteristics> characteristics() {

						return Collections.unmodifiableSet(EnumSet.of(
								Characteristics.CONCURRENT,
								Characteristics.UNORDERED,
								Characteristics.IDENTITY_FINISH)

				);
					}
				});

	}

	@Disabled
	@Test
	void testCollectPractice() {
		// 다음 출력결과를 참고하여 스트림을 HashMap으로 변형하세요

		Stream<Integer> numbers = newIntegerStream();
		HashMap<Integer, String> map = numbers
				.collect(HashMap<Integer, String>::new, (m, t) -> {
					m.put(t, "my-value" + t);
				}, (m, m2) -> {
					m.putAll(m2);
				});
		System.out.println(map);
		// {1:"my-value=1", 2:"my-value=2", 3:"my-value=3"}

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
