package com.denarced.othello;

import java.util.List;
import java.util.Map;

/**
 * @author denarced
 */
public class TestUtil {
    private final ListBoard board;
    private final CoordinateFactory coordinateFactory;

    public TestUtil(ListBoard board, CoordinateFactory coordinateFactory) {
        this.board = board;
        this.coordinateFactory = coordinateFactory;
    }

    public Map<Coordinate, List<Coordinate>> derive(String coordinate) {
        String lower = coordinate.toLowerCase();
        return board.deriveLegalAddsFromCoordinate(
            lower.charAt(0) - '1',
            lower.charAt(1) - 'a');
    }

    public Coordinate coordinate(String s) {
        String lower = s.toLowerCase();
        return coordinateFactory.getInstance(
            lower.charAt(0) - '1',
            lower.charAt(1) - 'a');
    }
}
