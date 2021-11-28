package com.company.hello;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int i = 0;
        String champion = null;
        while (!StdIn.isEmpty()) {
            i++;
            String s = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = s;
            }
        }
        System.out.println(champion);
    }
}
