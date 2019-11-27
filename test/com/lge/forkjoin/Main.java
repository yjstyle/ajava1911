package com.lge.forkjoin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

class Main {

	@Test
	void test() {
		var cnt = new AtomicInteger(0);
		var array = new Random().ints(0, 100).limit(100).boxed()
				.toArray(Integer[]::new);
		Consumer<Integer> myop = (i) -> cnt.incrementAndGet();

		Main.forEach(array, myop); // ctrl +1
		System.out.println("testCountedCompleter=" + cnt.get());

	}

	private static void forEach(Integer[] array, Consumer<Integer> myop) {
		if (array.length > 0) {
			class Task extends CountedCompleter<Void>{

				public Task(int i, int length) {
					// TODO Auto-generated constructor stub
				}

				@Override
				public void compute() {
					// TODO Auto-generated method stub
					
				}
				
			}
					
			ForkJoinPool.commonPool().invoke(new Task(0, array.length));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
