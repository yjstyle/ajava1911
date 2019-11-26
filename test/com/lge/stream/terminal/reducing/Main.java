package com.lge.stream.terminal.reducing;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {

//	@Disabled
	@Test
	void testReducingPractice() {
		List<Integer> lst = List.of(5, 10, 20, 50);
		String out = lst.stream()
				.collect(Collectors.reducing("",
						i -> String.valueOf(i),(l, r) -> l + r));
		System.out.println(out);
		// 5102050
		// 5102050
		// 5102050
	}

	@Disabled
	@Test
	void testReducing3() {
		List<Product> p = newProductList();
		Map<String, Optional<Integer>> m = p.stream()
				.collect(Collectors.groupingBy(t -> t.getId(),
						Collectors.mapping(t -> t.getPrice(),
								Collectors
										.reducing((l, r) -> l + r))));

		Map<String, Integer> m2 = newProductList().stream()
				.collect(Collectors.groupingBy(t -> t.getId(),
						Collectors.mapping(t -> t.getPrice(),
								Collectors.reducing(0,
										(l, r) -> l + r))));

		Map<String, Integer> m3 = newProductList().stream()
				.collect(Collectors.groupingBy(t -> t.getId(),
						Collectors.reducing(0, t -> t.getPrice(),
								(l, r) -> l + r)));
	}

	@Disabled
	@Test
	void test() {
		List<Product> p = newProductList();
		Map<String, Long> m = p.stream().collect(Collectors
				.groupingBy(t -> t.getId(), Collectors.counting()));

		Map<String, Integer> m2 = p.stream()
				.collect(Collectors.groupingBy(t -> t.getId(),
						Collectors.summingInt(t -> t.getPrice())));
	}

	// ctrl 1
	// ctrl + shift + o
	static class Product {
		String id;
		int price;

		public Product(String id, int price) {
			this.id = id;
			this.price = price;
		}

		static Product of(String id, int price) {
			return new Product(id, price);
		}

		public String getId() {
			return id;
		}

		public int getPrice() {
			return price;
		}

		@Override
		public String toString() {
			return "Product [id=" + id + ", price=" + price + "]";
		}
	}

	List<Product> newProductList() {
		return List.of(Product.of("A", 100), Product.of("B", 200),
				Product.of("B", 300), Product.of("C", 400));
	}

}
