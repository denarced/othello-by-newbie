package com.denarced.othello;

/**
 * @author denarced
 */
public class Coordinate implements Comparable<Coordinate> {
    private final int row;
    private final int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Coordinate)) {
            return false;
        }

        Coordinate rhs = (Coordinate) o;
        return row == rhs.row && col == rhs.col;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + col;
        return result;
    }

    @Override
    public String toString() {
        return String.format("Coordinate(%d%c)", row + 1, 'A' + col);
    }

    @Override
    public int compareTo(Coordinate o) {
        Integer row = this.row;
        if (row.equals(o.row)) {
            Integer col = this.col;
            return col.compareTo(o.col);
        } else {
            return row.compareTo(o.row);
        }
    }
}
