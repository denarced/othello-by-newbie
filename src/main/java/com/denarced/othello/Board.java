package com.denarced.othello;

/**
 * @author denarced
 */
public interface Board {
    int size();
    CellState at(Coordinate coordinate);
    void add(Coordinate coordinate, CellState cellState);
    int points(CellState cellState);
}
