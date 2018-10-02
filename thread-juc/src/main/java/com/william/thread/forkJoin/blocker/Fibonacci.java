package com.william.thread.forkJoin.blocker;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.*;

/**
 * User: William Cheng
 * Create Time: 2018/10/2 11:20
 * Description:
 * 	测试功能：fork-join ManagedBlocker 功能
 * 			当程序中需要阻塞线程的时候，调用block()，fj 新增线程利用多核进行处理
 * 			当程序处理完成以后，调用 	isReleasable()
 * 	测试案例：菲波拉契数计算
 */
// demo1: test100_000_000() time = 42332
// demo2: test100_000_000() time = 20991
// demo3: test100_000_000() time = 12555
// demo4: test100_000_000() time = 9973
// demo5: test100_000_000() time = 7069
// demo6: test100_000_000() time = 6881
public class Fibonacci {
	public BigInteger f(int n) {
		Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();
		cache.put(0, BigInteger.ZERO);
		cache.put(1, BigInteger.ONE);
		return f(n, cache);
	}

	private final BigInteger RESERVED = BigInteger.valueOf(-1000);

	public BigInteger f(int n, Map<Integer, BigInteger> cache) {
		BigInteger result = cache.putIfAbsent(n, RESERVED);
		if (result == null) {
			int half = (n + 1) / 2;
			RecursiveTask<BigInteger> f0_task = new RecursiveTask<BigInteger>() {
				@Override
				protected BigInteger compute() {
					return f(half - 1, cache);
				}
			};
			f0_task.fork();

			BigInteger f1 = f(half, cache);//當前線程自己計算
			BigInteger f0 = f0_task.join();//等待FJ計算結果

			long time = n > 10_000 ? System.currentTimeMillis() : 0;
			try {
				if (n % 2 == 1) {
					result = f0.multiply(f0).add(f1.multiply(f1));
				} else {
					result = f0.shiftLeft(1).add(f1).multiply(f1);
				}
				synchronized (RESERVED) {
					cache.put(n, result);
					RESERVED.notifyAll();
				}
			} finally {
				time = n > 10_000 ? System.currentTimeMillis() - time : 0;
				if (time > 50)
					System.out.printf("f(%d) took %d%n", n, time);
			}
		} else if (result == RESERVED) {//如果等于预留值,则阻塞并等待结果
			try {
				ReservedFibonacciBlocker blocker = new ReservedFibonacciBlocker(n, cache);
				ForkJoinPool.managedBlock(blocker);
				result = blocker.result;
			} catch (InterruptedException e) {
				throw new CancellationException("interrupted");
			}
		}

        return result;
		// return f(n - 1).add(f(n - 2));
	}

	private class ReservedFibonacciBlocker implements ForkJoinPool.ManagedBlocker {
		private BigInteger result;
		private final int n;
		private final Map<Integer, BigInteger> cache;

		public ReservedFibonacciBlocker(int n, Map<Integer, BigInteger> cache) {
			this.n = n;
			this.cache = cache;
		}

		@Override
		public boolean block() throws InterruptedException {
			synchronized (RESERVED) {
				while (!isReleasable()) {
					RESERVED.wait();
				}
			}
			return true;
		}

		@Override
		public boolean isReleasable() {
			return (result = cache.get(n)) != RESERVED;
		}
	}
}