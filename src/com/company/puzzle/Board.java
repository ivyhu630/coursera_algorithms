package com.company.puzzle;

import com.company.percolation.Percolation;

import java.util.ArrayList;

public class Board {
    private int[][] board;
    private int[][] goal;
    private int size;
    private static final int BLANK = 0;

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
        goal[size - 1][size - 1] = BLANK;
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
        if (this.hamming() == BLANK) {
            return true;
        }
        return false;
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

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == BLANK) {
                    if (i > 0) {
                        Board neighbourUp = new Board(board);
                        neighbourUp.swap(i, j, i - 1, j);
                        neighbors.add(neighbourUp);
                    }
                    if (i < size - 1) {
                        Board neighbourDown = new Board(board);
                        neighbourDown.swap(i, j, i + 1, j);
                        neighbors.add(neighbourDown);
                    }
                    if (j < size - 1) {
                        Board neighbourRight = new Board(board);
                        neighbourRight.swap(i, j, i, j + 1);
                        neighbors.add(neighbourRight);
                    }
                    if (j > 0) {
                        Board neighbourLeft = new Board(board);
                        neighbourLeft.swap(i, j, i, j - 1);
                        neighbors.add(neighbourLeft);
                    }
                }
            }
        }
        return neighbors;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twinBoard = new Board(board);
        int firRow = 0;
        int firCol = 0;
        if (board[firRow][firCol] == BLANK) {
            firCol++;
        }
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board[row][col] != board[firRow][firCol] && board[row][col] != BLANK) {
                    twinBoard.swap(firRow, firCol, row, col);
                    return twinBoard;
                }
            }
        }
        return twinBoard;
    }

    private void swap(int row1, int col1, int row2, int col2) {
        int temp = board[row1][col1];
        board[row1][col1] = board[row2][col2];
        board[row2][col2] = temp;
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