package Confluent;

import java.util.Random;

public class QuickSort {

    public void quickSort(int[] nums, int low, int high) {
        if (low >= high) return;

        int leftPointer = partition(nums, low, high);

        quickSort(nums, low, leftPointer - 1);
        quickSort(nums, leftPointer + 1, high);
    }

    private int partition(int[] nums, int low, int high) {
        // Choosing pivot is important
        // first element or last element. If the input is sorted, then this is basically bubble sort O(N^2)
        // middle element. If Middle element is largest or smallest, then O(N^2)
        // Randomization of pivots. O(NLogN)
        // Median of first, last and middle element.
        int pivotIndex = new Random().nextInt(high - low) + low;
        swap(nums, pivotIndex, high);

        int pivot = nums[high];

        int leftPointer = low;
        int rightPointer = high;

        while (leftPointer != rightPointer) {
            while (nums[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }
            while (nums[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(nums, leftPointer, rightPointer);
        }

        swap(nums, leftPointer, high);
        return leftPointer;
    }

    private void swap(int[] nums, int leftPointer, int rightPointer) {
        int temp = nums[leftPointer];
        nums[leftPointer] = nums[rightPointer];
        nums[rightPointer] = temp;
    }
}
