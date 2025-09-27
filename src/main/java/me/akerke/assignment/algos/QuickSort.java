package me.akerke.assignment.algos;

import me.akerke.assignment.metrics.CSVWriter;
import me.akerke.assignment.metrics.DepthTracker;
import me.akerke.assignment.metrics.Metrics;
import me.akerke.assignment.utils.SortUtils;

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
                SortUtils.insertionSort(arr, lo, hi, metrics);
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
        SortUtils.swap(arr, pivotIndex, hi - 1);
        return SortUtils.partition(arr, lo, hi, metrics);
    }

}
