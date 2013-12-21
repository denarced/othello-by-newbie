package com.denarced.othello;

import java.util.List;

/**
 * @author denarced
 */
public interface Ui {
    void beginTurn(
        boolean blacksTurn,
        int blacksPoints,
        int whitesPoints,
        List<List<Character>> board);

    String askCoordinates(boolean blacksTurn);
}
