package com.lge.adjava.lock;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class Main {
	@Test
	void testPhaser() {
		
	}

	@Disabled
	@Test
	void test() {
		StampedLock l = new StampedLock();
		ExecutorService exe = Executors.newFixedThreadPool(2);
		exe.submit(() -> {
			long orlock = l.tryOptimisticRead();

			retryOptimisticRead: for (;; orlock = l.readLock()) {
				if (!l.validate(orlock)) {
					System.out.println("or:I'm failed");
					continue retryOptimisticRead;
				} else {
					break;
				}
			}
			l.unlock(orlock);

		});

		exe.submit(() -> {
			long wlock = l.writeLock();
			try {
				System.out.println("w:I'm runing");
				sleep(1000);
			} finally {
				l.unlockWrite(wlock);
			}
		});

		shutdown(exe);

		// long rlock = l.readLock();

		// long orlock = l.tryOptimisticRead();
		// if(l.validate(orlock)) {
		// }
	}

// ctrl shift o
	private void shutdown(ExecutorService executor) {
		try {
			executor.shutdown();
			executor.awaitTermination(60, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (!executor.isTerminated()) {
				System.err.println("killing non-finished tasks");
			}
			executor.shutdownNow();
		}
	}

	public void sleep(long t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
