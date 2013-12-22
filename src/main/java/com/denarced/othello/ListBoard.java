package com.denarced.othello;

import java.util.ArrayList;
import java.util.List;

/**
 * @author denarced
 */
public class ListBoard implements Board {
    private final List<List<CellState>> cellList;

    public ListBoard(int size) {
        assert size >= 8;

        cellList = new ArrayList<List<CellState>>(size);
        for (int row = 0; row < size; ++row) {
            List<CellState> rowList = new ArrayList<CellState>(size);

            for (int col = 0; col < size; ++col) {
                rowList.add(CellState.NONE);
            }
            cellList.add(rowList);
        }
    }

    @Override
    public int size() {
        return cellList.size();
    }

    @Override
    public CellState at(Coordinate coordinate) {
        assertCoordinates(coordinate);

        int row = coordinate.row();
        int col = coordinate.col();

        return cellList.get(row).get(col);
    }

    // TODO Too low level; can't give caller that.
    @Override
    public void add(Coordinate coordinate, CellState cellState) {
        assertCoordinates(coordinate);

        int row = coordinate.row();
        int col = coordinate.col();

        cellList.get(row).set(col, cellState);
    }

    private void assertCoordinates(Coordinate coordinate) {
        int size = cellList.size();
        int row = coordinate.row();
        int col = coordinate.col();

        assert 0 <= row && row <= size;
        assert 0 <= col && col <= size;
    }

    @Override
    public int points(CellState cellState) {
        int total = 0;
        for (List<CellState> row: cellList) {
            for (CellState each: row) {
                if (each == cellState) {
                    ++total;
                }
            }
        }

        return total;
    }
}
