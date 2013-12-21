package com.denarced.othello;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int SIZE = 8;
    public static final char valkoinen = 'O';
    public static final char musta = 'X';
    public static boolean mVuoro = true;

    public static void main(String[] args) {
        final Board board = new ListBoard(SIZE);
        alusta(board);

        final Ui ui = new Cli(musta, valkoinen);
        ui.beginTurn(mVuoro, board);
        boolean tulos = siirto(ui, board);
        System.out.println(tulos);
    }

    public static List<List<Character>> createMatrix(int size) {
        List<List<Character>> matrix = new ArrayList<List<Character>>(size);
        for (int row = 0; row < size; ++row) {
            List<Character> rowList = new ArrayList<Character>(size);

            for (int col = 0; col < size; ++col) {
                rowList.add(' ');
            }

            matrix.add(rowList);
        }

        return matrix;
    }

    public static boolean areCoordinatesValid(String coordinates) {
        if (coordinates.length() < 2) {
            return false;
        }
        int[] k = { coordinates.codePointAt(1) , coordinates.codePointAt(0) };

        boolean validAlpha =
            'a' <= k[1] && k[1] <= 'h' ||
            'A' <= k[1] && k[1] <= 'H';
        boolean validNum = '1' <= k[0] && k[0] <= '8';

        return validAlpha && validNum;
    }

    public static boolean siirto(Ui ui, Board board) {
        String syote = ui.askCoordinates(mVuoro);
        if (!areCoordinatesValid(syote)) {
            return false;
        }

        int[] k = { syote.codePointAt(1) , syote.codePointAt(0) };
        k[0] -= 49;
        k[1] -= (k[1] < 80) ? 65 : 97;

        if (board.at(k[1], k[0]) != CellState.NONE) {
            return false;
        }

        return true;
    }

    public static void alusta(Board board) {
        board.add(3, 3, CellState.BLACK);
        board.add(3, 4, CellState.WHITE);
        board.add(4, 3, CellState.WHITE);
        board.add(4, 4, CellState.BLACK);
    }
}
