package com.company.queue;

import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }
        int n = Integer.parseInt(args[0]);
        int i = 0;
        for (String s : q) {
            if (i == n) {
                return;
            }
            ++i;
            System.out.println(s);
        }

    }
}
