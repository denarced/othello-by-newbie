package com.denarced.othello;

public class Main {
    public static final int SIZE = 8;
    public static final char WHITE = 'O';
    public static final char BLACK = 'X';

    public boolean blacksTurn = true;

    public static void main(String[] args) {
        new Main().play();
    }

    /**
     * Fake Ui class used for testing a whole game quickly.
     */
    private static class FakeCli extends Cli {
        private int currentPosition = 1;

        public FakeCli(char blacksButton, char whitesButton, CoordinateFactory coordinateFactory) {
            super(blacksButton, whitesButton, coordinateFactory);
        }

        @Override
        public String askCoordinates(boolean blacksTurn) {
            int row = currentPosition / SIZE + 1;
            int offset = currentPosition % SIZE;
            char col = (char) ('a' + offset);

            currentPosition++;
            return "" + col + row;
        }
    }

    public void play() {
        final CoordinateFactory coordinateFactory = new CoordinateFactory(SIZE);
        final Board board = new ListBoard(SIZE, coordinateFactory);
        final Ui ui = new Cli(BLACK, WHITE, coordinateFactory);
//        final Ui ui = new FakeCli(BLACK, WHITE, coordinateFactory);

        while (board.moveIsPossible()) {
            boolean playerWithTurnCanMove =
                board.moveIsPossibleFor(blacksTurn ? CellState.BLACK : CellState.WHITE);
            if (!playerWithTurnCanMove) {
                // TODO Tell UI that current player can't move
                blacksTurn = !blacksTurn;
            }

            ui.beginTurn(blacksTurn, board);
            CellState turn = blacksTurn ? CellState.BLACK : CellState.WHITE;
            siirto(ui, board, coordinateFactory, turn);

            blacksTurn = !blacksTurn;
        }

        ui.endGame(board);
    }

    boolean areCoordinatesValid(String coordinates) {
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

    private void siirto(
        Ui ui,
        Board board,
        CoordinateFactory coordinateFactory,
        CellState turn) {

        boolean moved = false;
        do {
            String syote = ui.askCoordinates(blacksTurn);
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
