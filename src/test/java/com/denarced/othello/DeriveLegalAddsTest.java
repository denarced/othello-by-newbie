package com.denarced.othello;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author denarced
 */
public class DeriveLegalAddsTest {
    private static final int SIZE = 8;
    private static final CoordinateFactory COORDINATE_FACTORY =
        new CoordinateFactory(SIZE);

    private ListBoard board;
    private TestUtil util;

    @Before
    public void setUp() {
        board = new ListBoard(SIZE, COORDINATE_FACTORY);
        util = new TestUtil(board, COORDINATE_FACTORY);
    }

    /**
     * Verify that several of opponent's disks are turned when necessary.
     *
     * BLACK
     *  ABCDEFGH
     * 4   XO
     * 5   OX
     * 6
     *
     * WHITE
     *  ABCDEFGH
     * 4   XXX
     * 5   OX
     * 6
     *
     * BLACK
     *  ABCDEFGH
     * 4   XXX
     * 5   OOO
     * 6
     *
     *  ABCDEFGH
     * 4   XXX
     * 5   OXX
     * 6     X
     */
    @Test
    public void testTurningSeveralDisks() {
        // SETUP SUT
        board.add(util.coordinate("4f"), CellState.BLACK);
        board.add(util.coordinate("5f"), CellState.WHITE);

        // EXERCISE
        Map<Coordinate, List<Coordinate>> legalAdds =
            board.deriveLegalAdds(CellState.BLACK);

        // VERIFY
        Coordinate target = util.coordinate("6f");
        Assert.assertTrue(
            "Coordinate must have been found: " + target.toString(),
            legalAdds.containsKey(target));

        List<Coordinate> turnedDisks = Arrays.asList(
            util.coordinate("5e"),
            util.coordinate("5f"));
        Assert.assertEquals(
            "The turned disks should be equal.",
            turnedDisks,
            legalAdds.get(target));
    }
}
