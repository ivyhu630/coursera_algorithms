package com.company.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] percolationResult;
    private final int numTrials;

    private final double CONFIDENCE_MULT = 1.96;

    // perform independent trials on an n-by-n gridbackwash with predetermined sites
    public PercolationStats(int n, int trials) {
        if (trials < 1) {
            throw new IllegalArgumentException("wrong trail size");
        }
        numTrials = trials;
        percolationResult = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            percolationResult[i] = (double) p.numberOfOpenSites() / (n * n);

        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationResult);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationResult);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_MULT * stddev() / Math.sqrt(numTrials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_MULT * stddev() / Math.sqrt(numTrials);
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("need to supply n and T");
        }
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats p = new PercolationStats(n, t);
        System.out.println("mean                     = " + p.mean());
        System.out.println("stddev                   = " + p.stddev());
        System.out.println("95% confidence interval  = " + "[" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
    }

}
