package com.company.puzzle;

public class Board {
    private int[][] board;
    private int[][] goal;
    private int size;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        board = tiles;
        size = board.length;
        goal = new int[size][size];

        // construct the goal
        int ct = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goal[i][j] = ct;
                ct++;
            }
        }
        goal[size - 1][size - 1] = 0;
    }

    // string representation of this board
    public String toString() {
        StringBuilder boardPrintOut = new StringBuilder();
        boardPrintOut.append(size);
        boardPrintOut.append("\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boardPrintOut.append(board[i][j]);
            }
            boardPrintOut.append("\n");
        }
        return boardPrintOut.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int wrongCT = 0;
        int i = 0;
        while (i < size * size - 1) {
            int n = i % size;
            int m = i / size;
            if (i + 1 != board[m][n]) {
                wrongCT++;
            }
            i++;
        }
        return wrongCT;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int res = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int currentNum = board[i][j];
                int goalNum = goal[i][j];
                if (currentNum == 0 || currentNum == goalNum) {
                    continue;
                }
                int x = (currentNum - 1) % size;
                int y = (currentNum - 1) / size;
                res += Math.abs(x - j);
                res += Math.abs(y - i);
            }
        }
        return res;
    }


    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != goal[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board other = (Board) y;
        int otherSize = other.dimension();
        if (otherSize != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != other.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // TODO: all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return this;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] testTiles = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] testTiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board test1 = new Board(testTiles);
        Board test2 = new Board(testTiles2);
//        System.out.println(test1.toString());
        System.out.println(test2.toString());
        System.out.println(test2.hamming());
        System.out.println(test2.manhattan());
    }

}