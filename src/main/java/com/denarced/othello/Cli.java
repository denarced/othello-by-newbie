package com.denarced.othello;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author denarced
 */
public class Cli implements Ui {
    private final char blacksButton;
    private final char whitesButton;
    private final CoordinateFactory coordinateFactory;
    private final Map<CellState, Character> cellStateCharacterMap =
        new HashMap<CellState, Character>();

    public Cli(
        char blacksButton,
        char whitesButton,
        CoordinateFactory coordinateFactory) {

        this.blacksButton = blacksButton;
        this.whitesButton = whitesButton;
        this.coordinateFactory = coordinateFactory;

        cellStateCharacterMap.put(CellState.BLACK, blacksButton);
        cellStateCharacterMap.put(CellState.WHITE, whitesButton);
        cellStateCharacterMap.put(CellState.NONE, ' ');
    }

    private String horizontalLine(String left, char hor, char mid, char right) {
        String s = left;
        for (int i = 0; i < 7; ++i) {
            s += hor;
            s += mid;
        }
        return s + hor + right;
    }

    private String numberedLine(String left, List<Character> buttons, char mid) {
        String s = left;
        for (Character each: buttons) {
            s += each;
            s += mid;
        }
        return s;
    }

    @Override
    public void beginTurn(boolean blacksTurn, Board board) {
        System.out.println("Musta ( " + blacksButton + " ) .. " +
            board.points(CellState.BLACK));
        System.out.println("Valkoinen ( " + whitesButton + " ) .. " +
            board.points(CellState.WHITE));
        System.out.println("  A B C D E F G H");

        char lt = '╔', hor = '═', rt = '╗';
        char cross = '╬', vert = '║', right = '╣';
        char lb = '╚', bottom = '╩', rb = '╝';

        final int boardSize = board.size();
        System.out.println(horizontalLine(" " + lt, hor, cross, rt));
        for (byte i = 0; i < boardSize; i++) {
            List<Character> buttons = new ArrayList<Character>();
            for (byte j = 0; j < boardSize; j++) {
                Coordinate coordinate = coordinateFactory.getInstance(i, j);
                buttons.add(cellStateCharacterMap.get(board.at(coordinate)));
            }
            String numLine = numberedLine("" + (i+1) + vert, buttons, vert);
            System.out.println(numLine);

            if (i == (boardSize - 1)) {
                System.out.println(horizontalLine(" " + lb, hor, bottom, rb));
            } else {
                System.out.println(
                    horizontalLine(" " + cross, hor, cross, right));
            }
        }
    }

    @Override
    public String askCoordinates(boolean blacksTurn) {
        System.out.print(blacksTurn ? "Mustan" : "Valkoisen");
        System.out.println(" vuoro");
        System.out.print("Anna koordinaatit: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String userName = null;
        try {
            userName = br.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userName;
    }
}
