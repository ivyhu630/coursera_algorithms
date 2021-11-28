package com.company.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] square;
    private final int size;
    private final WeightedQuickUnionUF ufPercolate;
    private final WeightedQuickUnionUF ufFull;
    private final int vtop;
    private final int vbot;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("wrong grid size");
        }
        square = new boolean[n][n];
        ufPercolate = new WeightedQuickUnionUF(n * n + 2);
        ufFull = new WeightedQuickUnionUF(n * n + 1);
        size = n;
        vtop = n * n;
        vbot = vtop + 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                square[i][j] = false;
            }
        }
    }

    private class Pair {
        int r;
        int c;

        Pair(int rParam, int cParam) {
            r = rParam;
            c = cParam;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isInbound(row, col)) {
            throw new IllegalArgumentException("out of boundary");
        }
        if (isOpen(row, col)) {
            return;
        }

        square[row - 1][col - 1] = true;

        if (row == 1) {
            ufPercolate.union(index(1, col), vtop);
            ufFull.union(index(1, col), vtop);
        }

        if (row == size) {
            ufPercolate.union(index(size, col), vbot);
        }

        Pair left = new Pair(0, -1);
        Pair right = new Pair(0, 1);
        Pair up = new Pair(-1, 0);
        Pair down = new Pair(1, 0);
        Pair[] offsets = {left, right, up, down};
        for (Pair offset : offsets) {
            int destRow = row + offset.r;
            int destCol = col + offset.c;
            if (isInbound(destRow, destCol) && isOpen(destRow, destCol)) {
                ufPercolate.union(index(row, col), index(destRow, destCol));
                ufFull.union(index(row, col), index(destRow, destCol));
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInbound(row, col)) {
            throw new IllegalArgumentException("out of boundary");
        }
        return square[row - 1][col - 1];
    }

    // is the side within boundary?
    private boolean isInbound(int row, int col) {
        return (row > 0 && row <= size && col > 0 && col <= size);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isInbound(row, col)) {
            throw new IllegalArgumentException("out of boundary");
        }
        if (!isOpen(row, col)) {
            return false;
        }
        return ufFull.find(index(row, col)) == ufFull.find(vtop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= size; ++i) {
            for (int j = 1; j <= size; ++j) {
                if (isOpen(i, j)) {
                    ++count;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufPercolate.find(vbot) == ufPercolate.find(vtop);
    }

    // convert row column to #
    private int index(int row, int col) {
        return ((row - 1) * size + col - 1);
    }

    private void print() {
        System.out.println("--------------");
        for (int i = 1; i <= size; ++i) {
            for (int j = 1; j <= size; ++j) {
                System.out.print((isOpen(i, j) ? "o" : "x") + " ");
            }
            System.out.println();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = 4;
        Percolation p = new Percolation(n);
        while (!p.percolates()) {
            p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            p.print();
        }
        assert p.percolates();
    }
}
