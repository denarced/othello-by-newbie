package com.denarced.othello;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

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
        Map<Coordinate, List<Coordinate>> legalAdds = derive("4D");
        legalAdds.putAll(derive("5E"));

        // VERIFY
        Map<Coordinate, List<Coordinate>> expected =
            new HashMap<Coordinate, List<Coordinate>>();

        // From 4D
        expected.put(coordinate("4F"), Arrays.asList(coordinate("4E")));
        expected.put(coordinate("6D"), Arrays.asList(coordinate("5D")));

        // From 5E
        expected.put(coordinate("3E"), Arrays.asList(coordinate("4E")));
        expected.put(coordinate("5C"), Arrays.asList(coordinate("5D")));

        assertAddMapEquality(expected, legalAdds);
    }

    /**
     * Test white player's legal adds in the beginning of the game.
     */
    @Test
    public void testWhitesInitialLegalAdds() {
        // EXERCISE
        Map<Coordinate, List<Coordinate>> legalAdds = derive("4E");
        legalAdds.putAll(derive("5D"));

        // VERIFY
        Map<Coordinate, List<Coordinate>> expected =
            new HashMap<Coordinate, List<Coordinate>>();
        // From 4E
        expected.put(coordinate("4C"), Arrays.asList(coordinate("4D")));
        expected.put(coordinate("6E"), Arrays.asList(coordinate("5E")));
        // From 5D
        expected.put(coordinate("3D"), Arrays.asList(coordinate("4D")));
        expected.put(coordinate("5F"), Arrays.asList(coordinate("5E")));

        assertAddMapEquality(expected, legalAdds);
    }

    @Test
    public void testCoordinateUtilityMethod() throws Exception {
        // EXERCISE
        Coordinate coordinate = coordinate("1A");

        // VERIFY
        Coordinate expected = COORDINATE_FACTORY.getInstance(0, 0);
        Assert.assertEquals(expected, coordinate);
    }

    private Map<Coordinate, List<Coordinate>> derive(String coordinate) {
        String lower = coordinate.toLowerCase();
        return board.deriveLegalAddsFromCoordinate(
            lower.charAt(0) - '1',
            lower.charAt(1) - 'a');
    }

    private Coordinate coordinate(String s) {
        String lower = s.toLowerCase();
        return COORDINATE_FACTORY.getInstance(
            lower.charAt(0) - '1',
            lower.charAt(1) - 'a');
    }

    private void assertAddMapEquality(
        Map<Coordinate, List<Coordinate>> expected,
        Map<Coordinate, List<Coordinate>> actual) {

        if (expected == null) {
            Assert.assertNull(
                "Since expected is null, actual should be too.",
                actual);
            return;
        }

        Assert.assertEquals(
            "The sizes should match.",
            expected.size(),
            actual.size());

        Map<Coordinate, List<Coordinate>> sorted =
            new TreeMap<Coordinate, List<Coordinate>>(expected);
        for (Map.Entry<Coordinate, List<Coordinate>> pair: sorted.entrySet()) {
            Coordinate key = pair.getKey();
            Assert.assertTrue(
                "Actual should contain the same key: " + key,
                actual.containsKey(key));

            if (key != null) {
                Assert.assertEquals(
                    "Coordinate lists should match.",
                    pair.getValue(),
                    actual.get(key));
            }
        }
    }
}
