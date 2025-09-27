package me.akerke.assignment.algos;

import me.akerke.assignment.metrics.CSVWriter;
import me.akerke.assignment.metrics.DepthTracker;
import me.akerke.assignment.metrics.Metrics;

import java.util.Random;

public class QuickSort {

    private static final Random rnd = new Random();
    private static final int CUTOFF = 16;

    public QuickSort() {}

    public static void start(int size, int trial, CSVWriter csv, Random rnd) {
        int[] arr = rnd.ints(size, -1_000_000, 1_000_000).toArray();
        Metrics m = new Metrics();
        DepthTracker d = new DepthTracker();
        sort(arr, m, d);
        csv.write("QuickSort", m.getTimeNs(), d.getMaxDepth(), m.getComparisons());
    }

    public static void sort(int[] arr, Metrics metrics, DepthTracker depth) {
        depth.reset();
        metrics.setComparisons(0);

        long t0 = System.nanoTime();
        quicksort(arr, 0, arr.length, metrics, depth);
        long elapsed = System.nanoTime() - t0;
        metrics.setTimeNs(elapsed);
    }

    private static void quicksort(int[] arr, int lo, int hi,
                                  Metrics metrics, DepthTracker depth) {
        while (hi - lo > 1) {
            int n = hi - lo;
            if (n <= CUTOFF) {
                insertionSort(arr, lo, hi, metrics);
                return;
            }

            int p = randomizedPartition(arr, lo, hi, metrics);

            if (p - lo < hi - (p + 1)) {
                depth.enter(); // tracking recursion depth
                quicksort(arr, lo, p, metrics, depth);
                depth.exit();
                lo = p + 1; // tail recursion
            } else {
                depth.enter();
                quicksort(arr, p + 1, hi, metrics, depth);
                depth.exit();
                hi = p;
            }
        }
    }


    private static int randomizedPartition(int[] arr, int lo, int hi, Metrics metrics) {
        int pivotIndex = lo + rnd.nextInt(hi - lo);
        swap(arr, pivotIndex, hi - 1);
        return partition(arr, lo, hi, metrics);
    }

    private static void insertionSort(int[] arr, int left, int right, Metrics metrics) {
        for (int i = left + 1; i < right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left) {
                metrics.incrementComparisons();
                if (arr[j] <= key) break;
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static int partition(int[] arr, int left, int right, Metrics metrics) {
        int pivot = arr[right - 1];
        int i = left;
        for (int j = left; j < right - 1; j++) {
            metrics.incrementComparisons();
            if (arr[j] <= pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, right - 1);
        return i;
    }

}
