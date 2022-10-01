package com.company.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;

public class Solver {
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> pqTwin;
    private int minMoves = 0;
    private int minMovesTwin = 0;
    private ArrayList<Board> solutions = new ArrayList<>();

    private class SearchNode {
        final int steps;
        final int priority;
        final SearchNode prevNode;
        final Board currBoard;

        private SearchNode(Board initial, int steps, SearchNode prevNode) {
            this.currBoard = initial;
            this.steps = steps;
            this.priority = steps + initial.manhattan();
            this.prevNode = prevNode;
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
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board cannot be empty");
        }
        SearchNode searchNode = new SearchNode(initial, 0, null);
        pq = new MinPQ<>(new SearchNodeCompare());
        pq.insert(searchNode);
        while (!pq.isEmpty()) {
            SearchNode minNode = pq.delMin();
            solutions.add(minNode.currBoard);

            for (Board neighbour : minNode.currBoard.neighbours()) {
                if (neighbour.isGoal()) {
                    minMoves = minNode.steps + 1;
                    solutions.add(neighbour);

                    return;
                } else {
                    SearchNode neighbourSearchNode = new SearchNode(neighbour, minNode.steps + 1, minNode);
                    if (minNode.prevNode != null && neighbour.toString().equals(minNode.prevNode.currBoard.toString())) {
                        continue;
                    } else {


                        pq.insert(neighbourSearchNode);
                    }
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        if (minMovesTwin > 0) {
            return false;
        } else {
            return true;
        }
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!this.isSolvable()) {
            return -1;
        }
        return Math.max(this.minMoves, this.minMovesTwin);
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!this.isSolvable()) {
            return null;
        } else {
            return solutions;
        }
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
