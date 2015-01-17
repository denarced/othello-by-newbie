package com.denarced.othello;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author denarced
 */
public class AreCoordinatesValidTest {
    @Test
    public void testEmptyString() {
        exerciseAndVerify("", false);
    }

    @Test
    public void testTooShortString() {
        exerciseAndVerify("A", false);
    }

    @Test(expected = NullPointerException.class)
    public void testNullString() {
        boolean irrelevant = true;
        exerciseAndVerify(null, irrelevant);
    }

    @Test
    public void testLowerCaseAlpha() {
        exerciseAndVerify("a1", true);
    }

    @Test
    public void testUpperCaseAlpha() {
        exerciseAndVerify("H8", true);
    }

    @Test
    public void testTooLowNum() {
        exerciseAndVerify("A0", false);
    }

    @Test
    public void testTooHighNum() {
        exerciseAndVerify("A9", false);
    }

    @Test
    public void testTooHighAlpha() {
        exerciseAndVerify("I0", false);
    }

    @Test
    public void testNumIsNotNum() {
        exerciseAndVerify("Aa", false);
    }

    @Test
    public void testAlphaIsNotAlpha() {
        exerciseAndVerify("#0", false);
    }

    private void exerciseAndVerify(String coordinate, boolean expected) {
        // EXERCISE
        boolean valid = new Main().areCoordinatesValid(coordinate);

        // VERIFY
        Assert.assertEquals(expected, valid);
    }
}
