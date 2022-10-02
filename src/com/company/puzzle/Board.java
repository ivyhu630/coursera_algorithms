 package com.company.puzzle;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private static final int BLANK = 0;
    private int[][] board;
    private int[][] goal;
    private int size;
    private int hammingCache;
    private int manhattanCache;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        // make a deep copy of the tiles
        board = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            board[i] = Arrays.copyOf(tiles[i], tiles[i].length);
        }
        size = board.length;
        goal = new int[size][size];

        hammingCache = 0;
        manhattanCache = 0;
        // construct the goal
        int ct = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                goal[i][j] = ct;
                ct++;
                int currentNum = tiles[i][j];
                int goalNum = goal[i][j];
                if (currentNum != 0) {
                    if (currentNum != ((i * size) + j + 1)) {
                        hammingCache++;
                    }
                }
                if (currentNum == 0 || currentNum == goalNum) {
                    continue;
                }
                int x = (currentNum - 1) % size;
                int y = (currentNum - x - 1) / size;
                manhattanCache += Math.abs(x - j);
                manhattanCache += Math.abs(y - i);
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
                boardPrintOut.append(" ");
            }
            boardPrintOut.setLength(boardPrintOut.length() - 1);
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
        return hammingCache;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattanCache;
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
        if (y == null) {
            return false;
        }
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
                    break;
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
//        int[][] testTiles = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};
        int[][] testTiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board test2 = new Board(testTiles2);
        System.out.println(test2.toString());
        System.out.println(test2.hamming());
        System.out.println(test2.manhattan());
    }

}