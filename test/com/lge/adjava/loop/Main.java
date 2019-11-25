package com.lge.adjava.loop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	// @Disabled
	@Test
	void testUtilitiesPractice() {
		Map<Integer, String> map = Map.of(
				0, "private", 1, "business", 2, "root"
);
		Set<Entry<Integer, String>> set = map.entrySet();
		set = Collections.unmodifiableSet(set);
		set = Collections.synchronizedSet(set);
		set.stream().forEach(System.out::println);

	}

	@Disabled
	@Test
	void testUtilities() {
//		List lst = new ArrayList<>();
		List<Integer> e = Collections.emptyList();
		Collections.unmodifiableList(e);
		Collections.synchronizedList(e);
		Collections.checkedCollection(e, Integer.class);
		int ind = Collections.binarySearch(e, 778);

	}

	List<Integer> processList(List<Integer> l) {
		Collections.checkedCollection(l, Integer.class);

		List<Integer> e = Collections.emptyList();

		return Collections.unmodifiableList(e);
	}

	@Disabled
	@Test
	void testLoop() {
		List<Integer> lst = List.of(0, 1, 2);
		Iterator<Integer> iter = lst.iterator();
		while (iter.hasNext()) {
			Integer item = iter.next();
		}
		// ctrl+space
		for (int i = 0; i < lst.size(); i++) {
			Integer array_element = lst.get(i);
		}

		for (Integer integer : lst) {

		}

		lst.stream().forEach(i -> {
			System.out.println(i);
		});

	}

	@Disabled
	@Test
	void test() {
//		Vector<String> vec = new Vector<String>();
		// ctrl +1
		// ArrayList ls = new ArrayList();
		// ArrayList<Object> ls = new ArrayList<Object>();
		ArrayList<Integer> lst = new ArrayList<Integer>(); // 5
		ArrayList<Integer> lst2 = new ArrayList<>(); // 7
		var lst3 = new ArrayList<>(); // 11

		lst3.add(1);
		lst3.add(2);

		var lst4 = new ArrayList<Integer>() {
			{
				add(1);
				add(2);
			}
		}; // 7

		var lst5 = List.of(1, 2, 4, 5);
		var set = Set.of(1, 2, 3, 5);
		Map<Integer, Integer> map = Map.of(1, 2);
		System.out.println(map);
	}

}
