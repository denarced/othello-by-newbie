package com.denarced.othello;

/**
 * @author denarced
 */
public class CoordinateFactory {
    private final int size;

    public CoordinateFactory(int size) {
        this.size = size;
    }

    public Coordinate getInstance(int row, int col) {
        assert 0 <= row && row < size;
        assert 0 <= col && col < size;

        return new Coordinate(row, col);
    }
}
