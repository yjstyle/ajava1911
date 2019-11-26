package com.lge.stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	@Test
	//@Disabled
	void testFlatmapPractice2() {
		List<String> strs = List.of(
		"Imagine driving down the highway at 70 miles ",
		"per hour, when suddenly the wheel turns hard right. ",
		"You crash. And it was because someone hacked your car.");

		Stream<String> words = strs.stream().flatMap((line)
					->Arrays.stream(line.split(" ")));
		List<String> wordsList = words.collect(Collectors.toList());
		System.out.format("%s%n", wordsList.toString());

	}

	@Test
	@Disabled
	void testFlatmapPractice() {
		class Customer {
			String name;
			List<String> wlst;

			Customer(String name, List<String> wlst) {
				this.name = name;
				this.wlst = wlst;
			}
		}

		List<Customer> clst = List.of(
				new Customer("Jack", List.of("Car", "Home")),
				new Customer("Ellin", List.of("Pen", "Desk")),
				new Customer("Nilson", List.of("Bag", "Phone")));
		Stream<String> streamWs = clst.stream()
				.flatMap(c -> c.wlst.stream());
		List<String> allWlst = streamWs.collect(Collectors.toList());

	}

	@Test
	@Disabled
	void testFlatmap() {
		Stream<List<Integer>> s = Stream.of(List.of(1, 2),
				List.of(3, 4));
		Stream<Integer> s2 = s.flatMap(i -> i.stream());
//		s.flatMap(i->Stream.generate(()->"echo"));

		Integer[][] array2d = new Integer[][] { { 1, 2, 3 },
				{ 4, 5, 6 } };
		Stream<Integer[]> s3 = Arrays.stream(array2d);
		// Stream<Integer> p = s3.flatMap(i->Arrays.stream(i));
		Integer[] array1d = s3.flatMap(i -> Arrays.stream(i))
				.toArray(Integer[]::new);
	}

	@Test
	@Disabled
	void testMapPractice() {
		// 다음 리스트 cos의 각 문자열을 첫 글자만 대문자하고
		// 나머지 글자는 모두 소문자로 변환하여 화면에 모두 출력하세요
		List<String> cos = List.of(Locale.getISOCountries());
		cos.stream()
				.map(s -> s.substring(0, 1).toUpperCase()
						+ s.substring(1).toLowerCase())
				.forEach(System.out::println);

		// 다음 리스트 d에서 짝수 만 x 3을 한 후 화면에 출력하세요
		List<Integer> d = Arrays.asList(3, 6, 9, 12, 15);
		d.stream().filter(i -> i % 2 == 0).map(i -> i * 3)
				.forEach(System.out::println);

		// 다음 리스트 s의 문자열을 정수로 변환하여,
		// 0부터 그 정수까지 난수를 발생시키고,
		// 그 총 합계를 화면에 출력하세요
		List<String> s = List.of("3", "6", "9");
		Random r = new Random(); // effective final
		int sum = s.stream().map(Integer::parseInt)
				.mapToInt(r::nextInt).sum();
		// .mapToInt((i) -> r.nextInt(i)).sum();

	}

	@Test
	@Disabled
	void testMap() {
		long cnt = Stream.of("1", "2", "3").map(Integer::parseInt)
				.count();

		Stream.of("1", "2", "3").map(Function.identity());
		Function<String, String> map1 = s -> "(" + s + ")";
		Stream.of("1", "2", "3").map(map1.compose(s -> "+" + s))
				.forEach(System.out::println);
		Stream.of("1", "2", "3").map(map1.andThen(s -> "-" + s))
				.forEach(System.out::println);
	}

	@Test
	@Disabled
	void testFilterPractice() {
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

		long cnt = customers.stream().filter(t -> t.getPoints() > 100)
				.count();

	}

	@Disabled
	@Test
	void testFilter() {
		Predicate<Integer> predicate = i -> i > 1;
		Predicate<Integer> predicate2 = i -> i > 2;

		Stream.of(1, 2, 3).filter(Predicate.not(predicate))
				.forEach(System.out::println);
	}

	@Disabled
	@Test
	void testSimpleTerminal() {
		List<Integer> lst = Stream.of(1, 2, 3)
				.collect(Collectors.toList());
		Stream.of(1, 2, 3).collect(Collectors.toSet());

		HashSet<Integer> ss = Stream.of(1, 2, 3).collect(
				HashSet<Integer>::new, HashSet<Integer>::add,
				HashSet<Integer>::addAll);

	}

	@Disabled
	@Test
	void testSourcePattern() {
		String ip = "10.221.51.2";
		Stream<String> s = Pattern.compile("\\.").splitAsStream(ip);
		s.forEach(System.out::println);

	}

	@Disabled
	@Test
	void testSourceFile() {
		String dir = "C:\\Windows\\System32\\drivers\\etc";
		String filename = "\\hosts";
		String fpath = dir + filename;

		try (
				Stream<String> s = Files.lines(Paths.get(fpath))
		) {
			s.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (
				Stream<Path> s = Files.walk(Paths.get(dir))
		) {
			s.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Disabled
	@Test
	void testSourcePractice() {
//		Stream<String> ss: 
		// 초기값 "It's me"에서 뒤에 문자"+"를 계속 붙이는
		Stream<String> ss = Stream.iterate("It's me", (s) -> s + "+");
//		Stream<BigInteger> bs: 
		// 초기값 2 에서 이후 그 값에 x2 하는
		Stream<BigInteger> bs = Stream.iterate(BigInteger.TWO,
				(bi) -> bi.multiply(BigInteger.TWO));
//		Stream<Double> rd: 
		// Math.random()값을 연속해서 발생시키는
		Stream<Double> rd = Stream.generate(Math::random);
//		Stream<Integer> seq: 
		// 나열된 데이터 79, 68, 55, 59, 77로
		Stream<Integer> seq = Stream.of(79, 68, 55, 59, 77);
//		Stream<Double> sa: 
		// 배열 myds = {0.0466, 0.5751, 0.6599}에서 index 1, 2 값으로만
		double myds[] = new double[] { 0.0466, 0.5751, 0.6599 };
		DoubleStream sa = Arrays.stream(myds, 1, 3);

//		 Stream<Integer> pis: 리스트  ints에서 병렬스트림
		List<Integer> ints = new ArrayList<>() {
			{
				add(-1387513903);
				add(164529915);
			}
		};
		Stream<Integer> pis = ints.parallelStream();

	}

	@Disabled
	@Test
	void testSource2() {
		// array
		String[] array = Locale.getISOCountries();
		Arrays.stream(array);
		Arrays.stream(array, 0, 10);

		// list
		List.of(1, 2, 3).stream();
		// set
		Set.of(1, 2, 3).stream();
		// map
		Stream<Entry<Integer, Integer>> s = Map.of(1, 1, 2, 2, 3, 3)
				.entrySet().stream();
	}

	@Disabled
	@Test
	void testSource() {
		// empty
//		Stream<Integer> e = Stream.empty();
		var e = Stream.<Integer>empty();
		Stream<Integer> s = Stream.of(1, 2, 3);

		String[] array = Locale.getISOCountries();
		Stream<String> s2 = Arrays.stream(array);

		Stream<Integer> s3 = Stream.iterate(0, i -> i + 1);
		Stream<Integer> s4 = Stream.iterate(0, i -> i < 10,
				i -> i + 1);
//		s3.forEach(System.out::println);
		Stream<String> s5 = Stream.generate(() -> "echo");
		Stream<Double> s6 = Stream.generate(Math::random);

	}

	@Disabled
	@Test
	void test() {
		Random r = new Random();
		IntStream ints = r.ints();
		ints.limit(100).filter(i -> i > 5)
				.mapToDouble((i) -> Double.valueOf(i)).sum();

	}

}
