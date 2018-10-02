package com.william.thread.forkJoin.recursiveaction;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

/**
 * 数组排序
 */
public class MergeSortForkJoinAction extends RecursiveAction {

	private static final Logger LOGGER = Logger.getLogger(MergeSortForkJoinAction.class.getName());
	private static final long serialVersionUID = -8194109984277933935L;
	private final int threshold;
	private int[] arrayToSort;

	public MergeSortForkJoinAction(final int[] arrayToSort, final int threshold) {
		this.arrayToSort = arrayToSort;
		this.threshold = threshold;
	}

	@Override
	protected void compute() {
		// 1. 当数组比较短的时候，直接排序
		if (arrayToSort.length <= threshold) {
			// sequential sort
			Arrays.sort(arrayToSort);
			return;
		}

		// 2. 数组比较大的时候，直接拆分并排序
		// Sort halves in parallel
		// 实际工作中不能随便增加线程，要依据实际的情况进行处理
		int midpoint = arrayToSort.length / 2;
		int[] leftArray = Arrays.copyOfRange(arrayToSort, 0, midpoint);
		int[] rightArray = Arrays.copyOfRange(arrayToSort, midpoint, arrayToSort.length);

		MergeSortForkJoinAction left = new MergeSortForkJoinAction(leftArray, threshold);
		MergeSortForkJoinAction right = new MergeSortForkJoinAction(rightArray, threshold);

		//invokeAll(left, right);
		left.fork();		//放入工作对队列中
		right.fork();		//放入工作对队列中

		left.join(); 	//等待结果
		right.join(); 	//等待结果
		// 3. sequential merge 归并排序
		arrayToSort = MergeSortSingleThread.merge(left.getSortedArray(), right.getSortedArray());
	}

	public int[] getSortedArray() {
		return arrayToSort;
	}

}