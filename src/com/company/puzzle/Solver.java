 package com.company.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.Stack;

import java.util.Comparator;
import java.util.Stack;

public class Solver {
    private boolean solvable;
    private SearchNode originLast;

    private class SearchNode {
        final int steps;
        final int priority;
        final SearchNode prevNode;
        final Board currBoard;
        private  boolean isTwin;

        private SearchNode(Board initial, int steps, SearchNode prevNode, boolean isTwin) {
            this.currBoard = initial;
            this.steps = steps;
            this.priority = steps + initial.manhattan();
            this.prevNode = prevNode;
            this.isTwin = isTwin;
        }


    }

    private class SearchNodeCompare implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            if (o1.priority == o2.priority) {
                return 0;
            }
            if (o1.priority > o2.priority) {
                return 1;
            } else {
                return -1;
            }
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public  Solver(Board initial) {
        MinPQ<SearchNode> pq;

        if (initial == null) {
            throw new IllegalArgumentException("Board cannot be empty");
        }
        solvable = true;

        SearchNode searchNode = new SearchNode(initial, 0, null, false);
        SearchNode searchNodeTwin = new SearchNode(initial.twin(), 0, null, true);

        pq = new MinPQ<>(new SearchNodeCompare());

        pq.insert(searchNode);
        pq.insert(searchNodeTwin);

        while (!pq.isEmpty()) {
            SearchNode minNode = pq.delMin();

            if (!minNode.isTwin) {
                originLast = minNode;
            }

            if (minNode.currBoard.isGoal()) {
                if (minNode.isTwin) {
                    solvable = false;
                }
                break;
            }

            for (Board neighbour : minNode.currBoard.neighbors()) {
                if (minNode.prevNode == null || !minNode.prevNode.currBoard.equals(neighbour)) {
                    SearchNode neighbourSearchNode = new SearchNode(neighbour, minNode.steps + 1, minNode, minNode.isTwin);
                    pq.insert(neighbourSearchNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!this.isSolvable()) {
            return -1;
        }
        return originLast.steps;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!this.isSolvable()) {
            return null;
        }
        Stack<Board> solutions = new Stack<Board>();
        SearchNode current = originLast;
        while (current.prevNode != null) {
            solutions.push(current.currBoard);
            current = current.prevNode;
        }
        solutions.push(current.currBoard);

        Stack<Board> solutions2 = new Stack<Board>();

        while (!solutions.empty()) {
            solutions2.push(solutions.pop());
        }

        return solutions2;
    }

    // test client (see below)
    public static void main(String[] args) {
        System.out.println("Hello, World");
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println("Initial board is " + initial.toString());
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}
