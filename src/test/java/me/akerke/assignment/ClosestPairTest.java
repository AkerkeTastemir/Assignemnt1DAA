package me.akerke.assignment;

import me.akerke.assignment.algos.ClosestPair;
import me.akerke.assignment.metrics.DepthTracker;
import me.akerke.assignment.metrics.Metrics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    public void testSmallFixedPoints() {
        ClosestPair.Point[] pts = {
                new ClosestPair.Point(0, 0),
                new ClosestPair.Point(3, 4),
                new ClosestPair.Point(7, 1),
                new ClosestPair.Point(1, 1)
        };

        Metrics m = new Metrics();
        DepthTracker d = new DepthTracker();

        double fast = ClosestPair.findClosest(pts, m, d);
        double brute = ClosestPair.bruteForce(pts, 0, pts.length, new Metrics());

        assertEquals(brute, fast, 1e-9);
        assertTrue(d.getMaxDepth() > 0);

        System.out.printf("Closest Pair, Comparisons=%d, Depth=%d, Time=%d ns.%n",
                m.getComparisons(), d.getMaxDepth(), m.getTimeNs());
    }

    @Test
    public void testRandomSmallArrays() {
        Random rnd = new Random(42);
        for (int n = 10; n <= 2000; n *= 2) {
            ClosestPair.Point[] pts = new ClosestPair.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair.Point(rnd.nextInt(10_000), rnd.nextInt(10_000));
            }

            Metrics m = new Metrics();
            DepthTracker d = new DepthTracker();

            double fast = ClosestPair.findClosest(pts, m, d);
            double brute = ClosestPair.bruteForce(pts, 0, pts.length, new Metrics());

            assertEquals(brute, fast, 1e-9, "Mismatch at n=" + n);

            System.out.printf("Closest Pair, Comparisons=%d, Depth=%d, Time=%d ns.%n",
                    n, m.getComparisons(), d.getMaxDepth(), m.getTimeNs());
        }
    }

    @Test
    public void testRandomLargeArray() {
        Random rnd = new Random(123);
        int n = 100_000;
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i = 0; i < n; i++) {
            pts[i] = new ClosestPair.Point(rnd.nextInt(), rnd.nextInt());
        }

        Metrics m = new Metrics();
        DepthTracker d = new DepthTracker();

        double fast = ClosestPair.findClosest(pts, m, d);

        assertTrue(fast >= 0); // sanity check

        System.out.printf("Closest Pair, Comparisons=%d, Depth=%d, Time=%d ns.%n",
                n, m.getComparisons(), d.getMaxDepth(), m.getTimeNs());
    }

}
