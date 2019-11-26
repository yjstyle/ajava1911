package com.lge.stream.terminal.downstream;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	// @Disabled
	@Test
	void testPartiongingBy() {
		Map<Boolean, List<Issue>> m2 = newIssueList().stream()
				.collect(Collectors.groupingBy(t->t.getPriority()<=1));
		
		Map<Boolean, List<Issue>> m = newIssueList().stream()
				.collect(Collectors.partitioningBy(t->t.getPriority()<=1));
		System.out.println(m);
	}

	@Disabled
	@Test
	void testCollectingAndThen() {
		Map<Integer, String> g = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						Collectors.collectingAndThen(
								Collectors.mapping(t -> t.getId(),
										Collectors.toList()),
								(r) -> r.get(0))));
	}

	@Disabled
	@Test
	void testMapping() {
		// Map<Integer, List<Issue>>
		Map<Integer, List<String>> g = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						Collectors.mapping(t -> t.getId(),
								Collectors.toList())));

	}

	@Disabled
	@Test
	void testToCollection() {
		List<Integer> lst = List.of(1);
		Set<Integer> set = lst.stream().collect(Collectors.toSet());
		HashSet<Integer> set2 = lst.stream()
				.collect(Collectors.toCollection(HashSet::new));
	}

	@Disabled
	@Test
	void testGroupingBy2() {
//		몇 번째 groupingBy를 사용하면 결과타입을 구할 수 있나요?
		// groupingBy절을 완성하세요

		Map<Integer, TreeSet<Issue>> g6 = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						Collectors.toCollection(TreeSet::new)));

		HashMap<Integer, PriorityQueue<Issue>> g7 = newIssueList()
				.stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						HashMap<Integer, PriorityQueue<Issue>>::new,
						Collectors.toCollection(PriorityQueue::new)));

		TreeMap<Integer, Set<Issue>> g5 = newIssueList().stream()
				.collect(Collectors.groupingBy(Issue::getPriority,
						TreeMap::new, Collectors.toSet()));

	}

	@Disabled
	@Test
	void testGroupingBy() {
		TreeMap<Integer, Set<Issue>> m4 = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						() -> new TreeMap<Integer, Set<Issue>>(),
						Collectors.toSet()));

		Map<Integer, Set<Issue>> m3 = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						Collectors.toSet()));

		Map<Integer, List<Issue>> m2 = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority(),
						Collectors.toList()));

		Map<Integer, List<Issue>> m = newIssueList().stream()
				.collect(Collectors.groupingBy(t -> t.getPriority()));
		System.out.println(m);

	}

	// ctrl + shift + o
	static class Issue implements Comparable<Issue> {
		String id;
		int priority;

		public Issue(String id, int priority) {
			this.id = id;
			this.priority = priority;
		}

		static Issue of(String id, int priority) {
			return new Issue(id, priority);
		}

		public String getId() {
			return id;
		}

		public int getPriority() {
			return priority;
		}

		@Override
		public int compareTo(Issue o) {
			return id.compareToIgnoreCase(o.id);
		}
	}

	List<Issue> newIssueList() {
		return List.of(Issue.of("JIRA-51", 2), Issue.of("JIRA-52", 1),
				Issue.of("JIRA-53", 2), Issue.of("JIRA-54", 3));
	}

	@Disabled
	@Test
	void testPractice() {
		Map<Integer, HashSet<String>> em = newEmployeeStream2()
				.collect(Collectors.toMap(e -> e.getScore(), (t) -> {
					HashSet<String> set = new HashSet<String>();
					set.add(t.getName());
					return set;
				}, (v1, v2) -> {
					v1.addAll(v2);
					return v1;
				}));
		System.out.println(em);

	}

	@Disabled
	@Test
	void testMergeFunction() {
		TreeMap<String, Integer> map3 = newEmployeeStream2().collect(
				Collectors.toMap(e -> e.getName(), e -> e.getScore(),
						(v1, v2) -> v1 < v2 ? v1 : v2,
						() -> new TreeMap<>()));

		Map<String, Integer> map2 = newEmployeeStream2().collect(
				Collectors.toMap(e -> e.getName(), e -> e.getScore(),
						(v1, v2) -> v1 < v2 ? v1 : v2));
		// {yahooe=20, google=40, naver=30}
		Map<String, Integer> map = newEmployeeStream2()
				.collect(Collectors.toMap(e -> e.getName(),
						e -> e.getScore(), (v1, v2) -> v1 + v2));
		System.out.println(map);
	}

	Stream<Employee> newEmployeeStream2() {
		return Stream.of(Employee.of("google", 40),
				Employee.of("yahooe", 20), Employee.of("naver", 30),
				Employee.of("naver", 20));
	}

	// ctrl1
	@Disabled
	@Test
	void test() {
		Map<String, Integer> map = newEmployeeStream()
				.collect(Collectors.toMap(e -> e.getName(),
						e -> e.getScore()));

		// Collector <- Collectors.toMap()
		Map<Employee, Integer> map2 = newEmployeeStream()
				.collect(Collectors.toMap(e -> e, e -> e.getScore()));
		System.out.println(map);
	}

	static class Employee {
		String name;
		int score;

		public String getName() {
			return name;
		}

		public int getScore() {
			return score;
		}

		public Employee(String name, int score) {
			this.name = name;
			this.score = score;
		}

		static Employee of(String name, int score) {
			return new Employee(name, score);
		}
	}

	// ctrl 1
	Stream<Employee> newEmployeeStream() {
		return Stream.of(Employee.of("google", 40),
				Employee.of("yahooe", 20), Employee.of("naver", 30));
	}

}
