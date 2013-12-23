package com.denarced.othello;

import java.util.*;

/**
 * @author denarced
 */
public class ListBoard implements Board {
    private final List<List<CellState>> cellList;
    private Map<Coordinate, List<Coordinate>> blacksAdds;
    private Map<Coordinate, List<Coordinate>> whitesAdds;
    private final CoordinateFactory coordinateFactory;

    public ListBoard(int size, CoordinateFactory coordinateFactory) {
        assert size >= 8;
        this.coordinateFactory = coordinateFactory;

        cellList = new ArrayList<List<CellState>>(size);
        for (int row = 0; row < size; ++row) {
            List<CellState> rowList = new ArrayList<CellState>(size);

            for (int col = 0; col < size; ++col) {
                rowList.add(CellState.NONE);
            }
            cellList.add(rowList);
        }

        cellList.get(3).set(3, CellState.BLACK);
        cellList.get(3).set(4, CellState.WHITE);
        cellList.get(4).set(3, CellState.WHITE);
        cellList.get(4).set(4, CellState.BLACK);

        reDeriveLegalAdds();
    }

    private void reDeriveLegalAdds() {
        blacksAdds = deriveLegalAdds(CellState.BLACK);
        whitesAdds = deriveLegalAdds(CellState.WHITE);
    }

    @Override
    public int size() {
        return cellList.size();
    }

    @Override
    public CellState at(Coordinate coordinate) {
        assertCoordinates(coordinate);

        int row = coordinate.row();
        int col = coordinate.col();

        return cellList.get(row).get(col);
    }

    // TODO Too low level; can't give caller that.
    @Override
    public void add(Coordinate coordinate, CellState cellState) {
        assertCoordinates(coordinate);

        int row = coordinate.row();
        int col = coordinate.col();
        CellState inverse = (cellState == CellState.BLACK)
            ? CellState.WHITE
            : CellState.BLACK;
        Map<Coordinate, List<Coordinate>> legalAdds =
            (cellState == CellState.BLACK)
                ? blacksAdds
                : whitesAdds;
        assert legalAdds.containsKey(coordinate);

        cellList.get(row).set(col, cellState);
        for (Coordinate each: legalAdds.get(coordinate)) {
            int x = each.row();
            int y = each.col();
            assert cellList.get(x).get(y) == inverse;

            cellList.get(x).set(y, cellState);
        }

        reDeriveLegalAdds();
    }

    private void assertCoordinates(Coordinate coordinate) {
        int size = cellList.size();
        int row = coordinate.row();
        int col = coordinate.col();

        assert 0 <= row && row <= size;
        assert 0 <= col && col <= size;
    }

    @Override
    public int points(CellState cellState) {
        int total = 0;
        for (List<CellState> row: cellList) {
            for (CellState each: row) {
                if (each == cellState) {
                    ++total;
                }
            }
        }

        return total;
    }

    @Override
    public boolean isLegalAdd(Coordinate coordinate, CellState cellState) {
        assert cellState != CellState.NONE;

        Map<Coordinate, List<Coordinate>> legalAdds =
            (cellState == CellState.BLACK)
                ? blacksAdds
                : whitesAdds;
        return legalAdds.containsKey(coordinate);
    }

    @Override
    public boolean moveIsPossible() {
        return
            blacksAdds.size() > 0 ||
            whitesAdds.size() > 0;
    }

    @Override
    public boolean moveIsPossibleFor(CellState cellState) {
        assert cellState != CellState.NONE;

        return (cellState == CellState.BLACK)
            ? blacksAdds.size() > 0
            : whitesAdds.size() > 0;
    }

    Map<Coordinate, List<Coordinate>> deriveLegalAddsToDirection(
        int row,
        int col,
        int hor,
        int ver) {

        final int size = cellList.size();
        assert 0 <= row && row < size;
        assert 0 <= col && col < size;
        assert -1 <= hor && hor <= 1;
        assert -1 <= ver && ver <= 1;

        final CellState player = cellList.get(row).get(col);
        final CellState opponent = (player == CellState.BLACK)
            ? CellState.WHITE
            : CellState.BLACK;

        int multiplier = 0;
        boolean opponentsDiskFound = false;
        List<Coordinate> opponentsDiskList = new ArrayList<Coordinate>();
        while (multiplier < size) {
            ++multiplier;
            int x = row + (ver * multiplier);
            int y = col + (hor * multiplier);
            if (x < 0) {
                assert ver < 0;
                if (opponentsDiskFound) {
                    assert row > 0;
                }

                return Collections.emptyMap();
            }
            if (y < 0) {
                assert hor < 0;
                if (opponentsDiskFound) {
                    assert col > 0;
                }

                return Collections.emptyMap();
            }
            if (x >= size) {
                assert ver > 0;
                if (opponentsDiskFound) {
                    assert row < (size - 1);
                }

                return Collections.emptyMap();
            }
            if (y >= size) {
                assert hor > 0;
                if (opponentsDiskFound) {
                    assert col < (size - 1);
                }

                return Collections.emptyMap();
            }

            CellState currentCell = cellList.get(x).get(y);
            if (opponentsDiskFound) {
                if (currentCell == CellState.NONE) {
                    Map<Coordinate, List<Coordinate>> map =
                        new HashMap<Coordinate, List<Coordinate>>();
                    map.put(
                        coordinateFactory.getInstance(x, y),
                        opponentsDiskList);

                    return map;
                } else if (currentCell == player) {
                    return Collections.emptyMap();
                } else {
                    opponentsDiskList.add(coordinateFactory.getInstance(x, y));
                }
            } else {
                if (currentCell == opponent) {
                    opponentsDiskList.add(coordinateFactory.getInstance(x, y));
                    opponentsDiskFound = true;
                } else {
                    return Collections.emptyMap();
                }
            }
        }

        assert false;
        return Collections.emptyMap();
    }

    Map<Coordinate, List<Coordinate>> deriveLegalAddsFromCoordinate(
        int row,
        int col) {

        Map<Coordinate, List<Coordinate>> legalAdds =
            new HashMap<Coordinate, List<Coordinate>>();
        for (int hor = -1; hor <= 1; ++hor) {
            for (int ver = -1; ver <= 1; ++ver) {
                if (hor == 0 && ver == 0) {
                    continue;
                }

                legalAdds.putAll(deriveLegalAddsToDirection(row, col, hor, ver));
            }
        }

        return legalAdds;
    }

    private Map<Coordinate, List<Coordinate>> deriveLegalAdds(CellState cellState) {
        assert cellState != CellState.NONE;

        Map<Coordinate, List<Coordinate>> legalAdds =
            new HashMap<Coordinate, List<Coordinate>>();
        final int size = cellList.size();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                legalAdds.putAll(deriveLegalAddsFromCoordinate(row, col));
            }
        }

        return legalAdds;
    }
}
