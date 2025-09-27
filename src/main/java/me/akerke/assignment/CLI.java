package me.akerke.assignment;

import me.akerke.assignment.algos.ClosestPair;
import me.akerke.assignment.algos.DeterministicSelect;
import me.akerke.assignment.algos.MergeSort;
import me.akerke.assignment.algos.QuickSort;
import me.akerke.assignment.metrics.CSVWriter;

import java.util.Random;

public class CLI {

    public static void main(String[] args) throws Exception {
        int size = 10_000;
        int trials = 3;
        String output = "results.csv";
        String algo = "";
        int rnd = 42;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--size": size = Integer.parseInt(args[++i]); break;
                case "--trials": trials = Integer.parseInt(args[++i]); break;
                case "--output": output = args[++i]; break;
                case "--algo": algo = args[++i].toLowerCase(); break;
                case "--rnd": rnd = Integer.parseInt(args[++i]); break;
            }
        }

        Random random = new Random(rnd);

        try (CSVWriter csv = new CSVWriter(output)) {
            if (algo.equals("quicksort")) {
                QuickSort.start(size, trials, csv, random);
            } else if (algo.equals("mergesort")) {
                MergeSort.start(size, csv, random);
            } else if (algo.equals("closestpair")) {
                ClosestPair.start(size, trials, csv, random);
            } else if (algo.equals("deterministic")) {
                DeterministicSelect.start(size, trials, csv, random);
            }
        }

        System.out.println("Finished. Results written to " + output);
    }

}