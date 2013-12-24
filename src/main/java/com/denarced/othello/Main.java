package com.denarced.othello;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static final int SIZE = 8;
    public static final char VALKOINEN = 'O';
    public static final char MUSTA = 'X';
    public static boolean mVuoro = true;

    public static void main(String[] args) {
        final CoordinateFactory coordinateFactory = new CoordinateFactory(SIZE);
        final Board board = new ListBoard(SIZE, coordinateFactory);
        final Ui ui = new Cli(MUSTA, VALKOINEN, coordinateFactory);

        while (board.moveIsPossible()) {
            boolean playerWithTurnCanMove =
                board.moveIsPossibleFor(mVuoro ? CellState.BLACK : CellState.WHITE);
            if (!playerWithTurnCanMove) {
                // TODO Tell UI that current player can't move
                mVuoro = !mVuoro;
            }

            ui.beginTurn(mVuoro, board);
            CellState turn = mVuoro ? CellState.BLACK : CellState.WHITE;
            siirto(ui, board, coordinateFactory, turn);

            mVuoro = !mVuoro;
        }

        ui.endGame(board);
        System.out.println("Game over");
    }

    public static boolean areCoordinatesValid(String coordinates) {
        if (coordinates.length() < 2) {
            return false;
        }
        int[] k = {
            coordinates.codePointAt(1),
            coordinates.codePointAt(0)};

        boolean validAlpha =
            'a' <= k[1] && k[1] <= 'h' ||
            'A' <= k[1] && k[1] <= 'H';
        boolean validNum = '1' <= k[0] && k[0] <= '8';

        return validAlpha && validNum;
    }

    public static void siirto(
        Ui ui,
        Board board,
        CoordinateFactory coordinateFactory,
        CellState turn) {

        boolean moved = false;
        do {
            String syote = ui.askCoordinates(mVuoro);
            if (!areCoordinatesValid(syote)) {
                // TODO Notify UI that coordinates are invalid, try again
                continue;
            }

            int[] k = { syote.codePointAt(1) , syote.codePointAt(0) };
            k[0] -= 49;
            k[1] -= (k[1] < 80) ? 65 : 97;

            Coordinate coordinate = coordinateFactory.getInstance(k[0], k[1]);
            moved = board.isLegalAdd(coordinate, turn);
            if (moved) {
                board.add(coordinate, turn);
            } else {
                ; // TODO Notify UI that current player can't add button there
            }
        } while (!moved);
    }
}
