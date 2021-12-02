package com.company.Queue;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int size = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null input");
        }
        if (size == s.length) {
            resize(2 * s.length);
        }
        s[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        if (size > 0 && size == s.length / 4) {
            resize(s.length / 2);
        }
        int n = StdRandom.uniform(size);
        Item res = s[n];
        s[n] = s[size - 1];
        s[size - 1] = null;
        --size;
        return res;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("queue is empty");
        }
        int n = StdRandom.uniform(size);
        Item res = s[n];
        return res;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private final int[] randomList = StdRandom.permutation(size);
        private int i = 0;

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("end of the elements");
            }
            return s[randomList[i++]];
        }
    }

    // resize the array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            copy[i] = s[i];
        }
        s = copy;
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        System.out.println("removed: " + q.dequeue());
        System.out.println("sample: " + q.sample());
        for (int i : q) {
            System.out.println(i);
        }
        System.out.println("removed: " + q.dequeue());
        System.out.println("removed: " + q.dequeue());
        System.out.println("removed: " + q.dequeue());
        System.out.println("removed: " + q.dequeue());
        System.out.println(q.isEmpty());
    }

}
