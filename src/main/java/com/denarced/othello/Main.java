package com.denarced.othello;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final int SIZE = 8;
    public static final List<List<Character>> PELITAULU = createMatrix(SIZE);
    public static final char valkoinen = 'O';
    public static final char musta = 'X';
    public static final char tyhja = ' ';
    public static boolean mVuoro = true;
    public static final byte[] byteTilanne = { 2, 2 };

    public static void main(String[] args) {
        alusta();

        final Ui ui = new Cli(musta, valkoinen);
        tilanne();
        ui.beginTurn(mVuoro, byteTilanne[0], byteTilanne[1], PELITAULU);
        boolean tulos = siirto(ui);
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

    public static boolean siirto(Ui ui) {
        String syote = ui.askCoordinates(mVuoro);
        if (!areCoordinatesValid(syote)) {
            return false;
        }

        int[] k = { syote.codePointAt(1) , syote.codePointAt(0) };
        k[0] -= 49;
        k[1] -= (k[1] < 80) ? 65 : 97;

        boolean movingToEmptySpace = PELITAULU.get(k[0]).get(k[1]) == tyhja;
        if (!movingToEmptySpace) {
            return false;
        }

        return true;
    }

    public static void alusta() {
        for (byte i = 0; i < SIZE; i++) {
            for (byte j = 0; j < SIZE; j++) {
                if (i == 3 && j == 3) {
                    PELITAULU.get(i).set(j, musta);
                } else if (i == 3 && j == 4) {
                    PELITAULU.get(i).set(j, valkoinen);
                } else if (i == 4 && j == 3) {
                    PELITAULU.get(i).set(j, valkoinen);
                } else if (i == 4 && j == 4) {
                    PELITAULU.get(i).set(j, musta);
                } else {
                    PELITAULU.get(i).set(j, tyhja);
                }
            }
        }
    }

    public static void tilanne() {
        byte mustia = 0;
        byte valkoisia = 0;
        for (byte i = 0; i < PELITAULU.size(); i++) {
            for (byte j = 0; j < PELITAULU.size(); j++) {
                if (PELITAULU.get(i).get(j).equals(musta)) {
                    mustia++;
                } else if (PELITAULU.get(i).get(j).equals(valkoinen)) {
                    valkoisia++;
                } else {

                }
            }
        }
        byteTilanne[0] = mustia;
        byteTilanne[1] = valkoisia;
    }
}
