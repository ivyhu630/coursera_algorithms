package com.company.queue;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last = null;
    private int counter;

    private class Node {
        Item item;
        Node next = null;
        Node prev = null;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return counter;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("require input");
        }
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
            counter++;
            return;
        }
        Node second = first;
        first = new Node();
        first.item = item;
        first.next = second;
        second.prev = first;
        counter++;

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("require input");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (first == null) first = last;
        else {
            oldLast.next = last;
        }
        counter++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        --counter;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null;
        }
        --counter;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("end of the elements");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();

        // empty
        assert d.isEmpty();

        // illegal arg
//        try {
//            d.addFirst(null);
//            // should not get to below
//            assert false;
//        } catch (IllegalArgumentException e) {
//        }
        // add 2,1,3,4 then remove 2,4 check size to be 2 and element to match
        d.addFirst(1);
        d.addFirst(2);
        d.addLast(3);
        d.addLast(4);

        d.removeFirst();
        d.removeLast();

        assert d.size() == 2;
        int c = 1;
        for (int i : d) {
//            System.out.println(i);
            assert i == c;
            c += 2;
        }

    }

}

