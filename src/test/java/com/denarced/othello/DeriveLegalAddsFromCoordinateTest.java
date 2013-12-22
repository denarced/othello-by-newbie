package com.denarced.othello;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author denarced
 */
public class DeriveLegalAddsFromCoordinateTest {
    private static final int SIZE = 8;
    private static final CoordinateFactory COORDINATE_FACTORY =
        new CoordinateFactory(SIZE);
    private ListBoard board;

    @Before
    public void setUp() {
        board = new ListBoard(SIZE, COORDINATE_FACTORY);
    }

    /**
     * Test black player's legal adds in the beginning of the game.
     */
    @Test
    public void testBlacksInitialLegalAdds() {
        // EXERCISE
        Set<Coordinate> legalAdds = board.deriveLegalAddsFromCoordinate(3, 4);
        legalAdds.addAll(board.deriveLegalAddsFromCoordinate(4, 3));

        // VERIFY
        Set<Coordinate> expected = new HashSet<Coordinate>();
        expected.add(COORDINATE_FACTORY.getInstance(3, 2));
        expected.add(COORDINATE_FACTORY.getInstance(5, 4));
        expected.add(COORDINATE_FACTORY.getInstance(2, 3));
        expected.add(COORDINATE_FACTORY.getInstance(4, 5));

        Assert.assertEquals(expected, legalAdds);
    }

    /**
     * Test white player's legal adds in the beginning of the game.
     */
    @Test
    public void testWhitesInitialLegalAdds() {
        // EXERCISE
        Set<Coordinate> legalAdds = board.deriveLegalAddsFromCoordinate(3, 3);
        legalAdds.addAll(board.deriveLegalAddsFromCoordinate(4, 4));

        // VERIFY
        Set<Coordinate> expected = new HashSet<Coordinate>();
        expected.add(COORDINATE_FACTORY.getInstance(3, 5));
        expected.add(COORDINATE_FACTORY.getInstance(5, 3));
        expected.add(COORDINATE_FACTORY.getInstance(4, 2));
        expected.add(COORDINATE_FACTORY.getInstance(2, 4));

        Assert.assertEquals(expected, legalAdds);
    }

}
