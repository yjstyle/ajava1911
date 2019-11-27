package com.lge.adjava.atomic;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	@Test
	void testLongAdder() {
		var adder = new LongAccumulator(Long::sum, 0);
		adder.accumulate(10);
		adder.reset();
		adder.get();
		adder.getThenReset();
		
		var adder2 = new LongAdder();
		adder2.increment();
		adder2.decrement();
		adder2.reset();
		adder2.sum();
	}

	@Disabled
	@Test
	void testOpaque() {
		// loop 0...100_000
		var executor = Executors.newFixedThreadPool(2);
		var a = new AtomicBoolean(false);
		var b = new AtomicBoolean(false);
		try {
			var lst = executor.<Boolean>invokeAll(List.of(() -> {
				b.setOpaque(true);
				Boolean x = a.getOpaque();
				return x;
			}, () -> {
				a.setOpaque(true);
				Boolean y = b.getOpaque();
				return y;
			}));
			executor.shutdown();
			executor.awaitTermination(5l, TimeUnit.SECONDS);
			System.out.println(lst.get(0).get() + ", " + lst.get(1).get());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Disabled
	@Test
	void testCAS() {
		var ai = new AtomicLong();

		// -------------------------
		IntConsumer action = (int i) -> {
			boolean status = false;
			do {
				long expectedValue = ai.get();
				long newValue = expectedValue + 1;// ==----=-=-=-=-=-=-=
				// status = ai.weakCompareAndSetVolatile(expectedValue,
				// newValue);
				status = ai.weakCompareAndSetPlain(expectedValue, newValue);
				if (!status) {
					System.out.println("Failed:" + Thread.currentThread());
				}
			} while (!status);
			// System.out.println("ai=" + ai.get());
		};
		IntStream.range(0, 100_000).parallel().unordered().forEach(action);
		// --------------------------
	}

	@Disabled
	@Test
	void testAtmoic() {

		var ai = new AtomicInteger(0);
		ai.getAndIncrement(); // i++;
		ai.incrementAndGet(); // ++i;
		ai.addAndGet(2);
		ai.getAndAdd(2);

		ai.getAndUpdate((i) -> i);
		ai.set(50);
		ai.set(Math.max(1, 2));
		ai.updateAndGet((i) -> i);
		ai.lazySet(6);

	}

	int mcnt = 0;

	@Disabled
	@Test
	void test() {
		Integer i = Integer.valueOf(1);
		// AtomicInteger ai = new AtomicInteger(1);
		var ai = new AtomicInteger(0);
		// var ao = new Atomicrefe

		// parallel 프로그래밍을 해봅시다.
		// 1~100 병렬 적이게 실행하는데 카운팅+1씩해보고 화면에 출력해봅시다.
		IntStream.range(0, 100).parallel().forEach(ii -> {
			// ++mcnt;
			ai.getAndIncrement();
		});
		// System.out.println(mcnt);
		System.out.println(ai.get());

	}

}
