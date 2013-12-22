package com.denarced.othello;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static final int SIZE = 8;
    public static final char VALKOINEN = 'O';
    public static final char MUSTA = 'X';
    public static boolean mVuoro = true;

    public static void main(String[] args) {
        final Board board = new ListBoard(SIZE);
        final CoordinateFactory coordinateFactory = new CoordinateFactory(SIZE);
        alusta(board, coordinateFactory);
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

    public static void alusta(Board board, CoordinateFactory coordinateFactory) {
        Map<Coordinate, CellState> map = new HashMap<Coordinate, CellState>();
        map.put(coordinateFactory.getInstance(3, 3), CellState.BLACK);
        map.put(coordinateFactory.getInstance(3, 4), CellState.WHITE);
        map.put(coordinateFactory.getInstance(4, 3), CellState.WHITE);
        map.put(coordinateFactory.getInstance(4, 4), CellState.BLACK);

        for(Map.Entry<Coordinate, CellState> each: map.entrySet()) {
            board.add(each.getKey(), each.getValue());
        }
    }
}
