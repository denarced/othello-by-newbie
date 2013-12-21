package com.denarced.othello;

/**
 * @author denarced
 */
public interface Board {
    int size();
    CellState at(int row, int col);
    void add(int row, int col, CellState cellState);
    int points(CellState cellState);
}
