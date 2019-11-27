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
		ExecutorService exe = Executors.newCachedThreadPool();
		Phaser ph = new Phaser(1) {
			protected boolean onAdvance(int phase, int parties) {
				System.out.println("phase=" + phase + ", parties=" + parties);
				return false;
			}
		};
		assertEquals(0, ph.getPhase());

		// when
		exe.submit(new LongRunningAction("thread-1", ph));
		exe.submit(new LongRunningAction("thread-2", ph));
		exe.submit(new LongRunningAction("thread-3", ph));

		// then
		ph.arriveAndAwaitAdvance();
		assertEquals(1, ph.getPhase());

		// and
		exe.submit(new LongRunningAction("thread-4", ph));
		exe.submit(new LongRunningAction("thread-5", ph));

		ph.arriveAndAwaitAdvance();

		assertEquals(2, ph.getPhase());

		ph.arriveAndDeregister();

	}

	class LongRunningAction implements Runnable {
		private String name;
		private Phaser ph;

		LongRunningAction(String threadName, Phaser ph) {
			this.name = threadName;
			this.ph = ph;
			this.ph.register();
		}

		@Override
		public void run() {
			System.out.println("This is phase " + ph.getPhase());
			System.out
					.println("Thread " + name + " before long running action");
			ph.arriveAndAwaitAdvance();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ph.arriveAndDeregister();
		}
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
