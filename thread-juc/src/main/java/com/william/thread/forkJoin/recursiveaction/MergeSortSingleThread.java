package com.william.thread.forkJoin.recursiveaction;

import java.util.Arrays;

/**
 * 归并排序
 *
 */
public class MergeSortSingleThread {

    private final int[] arrayToSort;
    private final int threshold;

    public MergeSortSingleThread(final int[] arrayToSort, final int threshold) {
        this.arrayToSort = arrayToSort;
        this.threshold = threshold;
    }

    public int[] sequentialSort() {
        return sequentialSort(arrayToSort, threshold);
    }

    public static int[] sequentialSort(final int[] arrayToSort, int threshold) {
        if (arrayToSort.length < threshold) {
            Arrays.sort(arrayToSort);
            return arrayToSort;
        }

        int midpoint = arrayToSort.length / 2;

        int[] leftArray = Arrays.copyOfRange(arrayToSort, 0, midpoint);
        int[] rightArray = Arrays.copyOfRange(arrayToSort, midpoint, arrayToSort.length);

        leftArray = sequentialSort(leftArray, threshold);
        rightArray = sequentialSort(rightArray, threshold);

        return merge(leftArray, rightArray);
    }

    /**
     * 归并排序
     * @param leftArray 左边数组
     * @param rightArray 右边数组
     * @return 排序后的结果
     */
    public static int[] merge(final int[] leftArray, final int[] rightArray) {
        int[] mergedArray = new int[leftArray.length + rightArray.length];
        int mergedArrayPos = 0;
        int leftArrayPos = 0;
        int rightArrayPos = 0;
        while (leftArrayPos < leftArray.length && rightArrayPos < rightArray.length) {
            if (leftArray[leftArrayPos] <= rightArray[rightArrayPos]) {
                mergedArray[mergedArrayPos] = leftArray[leftArrayPos];
                leftArrayPos++;
            } else {
                mergedArray[mergedArrayPos] = rightArray[rightArrayPos];
                rightArrayPos++;
            }
            mergedArrayPos++;
        }

        while (leftArrayPos < leftArray.length) {
            mergedArray[mergedArrayPos] = leftArray[leftArrayPos];
            leftArrayPos++;
            mergedArrayPos++;
        }

        while (rightArrayPos < rightArray.length) {
            mergedArray[mergedArrayPos] = rightArray[rightArrayPos];
            rightArrayPos++;
            mergedArrayPos++;
        }
        return mergedArray;
    }
}