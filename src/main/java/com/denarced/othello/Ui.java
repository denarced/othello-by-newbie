package com.denarced.othello;

/**
 * @author denarced
 */
public interface Ui {
    void beginTurn(boolean blacksTurn, Board board);
    String askCoordinates(boolean blacksTurn);
}
