package com.denarced.othello;

import java.io.Console;
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
        tulosta();
        boolean tulos;
        tulos = siirto();
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

    public static boolean siirto() {
        Console koso = System.console();
        if (mVuoro) {
            System.out.print("Mustan");
        } else {
            System.out.print("Valkoisen");
        }
        System.out.println(" vuoro");
        System.out.print("Anna koordinaatit: ");
        String syote = koso.readLine();
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

    public static String horizontalLine(String left, char hor, char mid, char right) {
        String s = left;
        for (int i = 0; i < 7; ++i) {
            s += hor;
            s += mid;
        }
        return s + hor + right;
    }

    public static String numberedLine(String left, List<Character> buttons, char mid) {
        String s = left;
        for (Character each: buttons) {
            s += each;
            s += mid;
        }
        return s;
    }

    public static void tulosta() {
        tilanne();

        System.out.println("Musta ( " + musta + " ) .. " + byteTilanne[0]);
        System.out.println("Valkoinen ( " + valkoinen + " ) .. " + byteTilanne[1]);
        System.out.println("  A B C D E F G H");

        char lt = '╔', hor = '═', rt = '╗';
        char cross = '╬', vert = '║', right = '╣';
        char lb = '╚', bottom = '╩', rb = '╝';

        System.out.println(horizontalLine(" " + lt, hor, cross, rt));
        for (byte i = 0; i < PELITAULU.size(); i++) {
            List<Character> buttons = new ArrayList<Character>();
            for (byte j = 0; j < PELITAULU.size(); j++) {
                buttons.add(PELITAULU.get(i).get(j));
            }
            String numLine = numberedLine("" + (i+1) + vert, buttons, vert);
            System.out.println(numLine);

            if (i == (PELITAULU.size() - 1)) {
                System.out.println(horizontalLine(" " + lb, hor, bottom, rb));
            } else {
                System.out.println(
                    horizontalLine(" " + cross, hor, cross, right));
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
