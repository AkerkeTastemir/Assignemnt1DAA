package me.akerke.assignment;

import me.akerke.assignment.algos.QuickSort;
import me.akerke.assignment.metrics.DepthTracker;
import me.akerke.assignment.metrics.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    @Test
    public void testSmallArray() {
        int[] arr = {5, 2, 4, 1, 3};
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics m = new Metrics();
        DepthTracker d = new DepthTracker();
        QuickSort.sort(arr, m, d);

        assertArrayEquals(expected, arr);
        assertTrue(d.getMaxDepth() <= 2 * (int)(Math.log(arr.length) / Math.log(2)) + 5);

        System.out.printf("Quick Sort, Comparisons=%d, Depth=%d, Time=%d nanosec.%n", m.getComparisons(), d.getMaxDepth(), m.getTimeNs());
    }

    @Test
    public void testRandomLargeArray() {
        Random rnd = new Random(42);
        int[] arr = rnd.ints(20_000, -1000, 1000).toArray();
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics m = new Metrics();
        DepthTracker d = new DepthTracker();
        QuickSort.sort(arr, m, d);

        assertArrayEquals(expected, arr);

        System.out.printf("Quick Sort, Comparisons=%d, Depth=%d, Time=%d nanosec.%n", m.getComparisons(), d.getMaxDepth(), m.getTimeNs());
    }

    @Test
    public void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = arr.clone();

        Metrics m = new Metrics();
        DepthTracker d = new DepthTracker();
        QuickSort.sort(arr, m, d);

        assertArrayEquals(expected, arr);
        System.out.printf("Quick Sort, Comparisons=%d, Depth=%d, Time=%d nanosec.%n", m.getComparisons(), d.getMaxDepth(), m.getTimeNs());

    }

}
