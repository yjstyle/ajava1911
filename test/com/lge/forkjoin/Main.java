package com.lge.forkjoin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class Main {
	@Test
	void test() {
		var cnt = new AtomicInteger(0);
		var array = new Random().ints(0, 100).limit(100).boxed()
				.toArray(Integer[]::new);
		Consumer<Integer> myop = (i) -> cnt.incrementAndGet();

		//Main.forEach(array, myop); // ctrl +1
		System.out.println("testCountedCompleter=" + cnt.get());
		
		Stream.of(0).parallel().forEachOrdered(e->Thread.dumpStack());

	}

	private static void forEach(Integer[] array, Consumer<Integer> myop) {
		if (array.length > 0) {
			class Task extends CountedCompleter<Void>{
				private int lo;
				private int hi;

				public Task(int lo, int hi) {
					this(null, lo, hi);
				}
				public Task(Task task, int lo, int hi) {
					super(task);
					this.lo = lo;
					this.hi = hi;
				}

				@Override public void compute() {
					if(hi - lo > 1) {
						int mid = (hi + lo) >>> 1;
						setPendingCount(1);
						new Task(this, lo,mid).compute();
						new Task(this, mid,hi).fork();
					} else if(hi > lo) { // hi - lo == 1
						myop.accept(array[lo]);
						tryComplete(); // onCompletion o
					}
					//propagateCompletion(); // onCompletion x
				}
				
			}
					
			ForkJoinPool.commonPool().invoke(new Task(0, array.length));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
