package com.william.thread.forkJoin.recursivetask;


import com.william.thread.utils.CalcUtil;
import com.william.thread.utils.Utils;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * 计算数组元素之和
 */
public class LongSumMain {
	static final int NCPU = Runtime.getRuntime().availableProcessors();
	/** for time conversion */
	static final long NPS = (1000L * 1000 * 1000);
	static long calcSum;
	static final boolean reportSteals = true;

	public static long seqSum(int[] array) {
		return CalcUtil.calSum(array);
	}
}