package com.denarced.othello;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author denarced
 */
public class AreCoordinatesValidTest {
    @Test
    public void testEmptyString() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("");

        // VERIFY
        Assert.assertFalse(valid);
    }

    @Test
    public void testTooShortString() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("A");

        // VERIFY
        Assert.assertFalse(valid);
    }

    @Test(expected = NullPointerException.class)
    public void testNullString() {
        // EXERCISE
        Main.areCoordinatesValid(null);
    }

    // legit with lower case
    @Test
    public void testLowerCaseAlpha() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("a1");

        // VERIFY
        Assert.assertTrue(valid);
    }

    // legit with upper case
    @Test
    public void testUpperCaseAlpha() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("H8");

        // VERIFY
        Assert.assertTrue(valid);
    }

    // legit alpha, too low num
    @Test
    public void testTooLowNum() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("A0");

        // VERIFY
        Assert.assertFalse(valid);
    }

    // legit alpha, too high num
    @Test
    public void testTooHighNum() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("A9");

        // VERIFY
        Assert.assertFalse(valid);
    }

    @Test
    public void testTooHighAlpha() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("I0");

        // VERIFY
        Assert.assertFalse(valid);
    }

    // num is not num

    @Test
    public void testNumIsNotNum() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("Aa");

        // VERIFY
        Assert.assertFalse(valid);
    }

    // alpha is not alpha

    @Test
    public void testAlphaIsNotAlpha() {
        // EXERCISE
        boolean valid = Main.areCoordinatesValid("#0");

        // VERIFY
        Assert.assertFalse(valid);
    }
}
