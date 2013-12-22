package com.denarced.othello;

import java.util.*;

/**
 * @author denarced
 */
public class ListBoard implements Board {
    private final List<List<CellState>> cellList;
    private final Map<CellState, Set<Coordinate>> legalAdds =
        new HashMap<CellState, Set<Coordinate>>();
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

        legalAdds.put(CellState.BLACK, deriveLegalAdds(CellState.BLACK));
        legalAdds.put(CellState.WHITE, deriveLegalAdds(CellState.WHITE));
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

        cellList.get(row).set(col, cellState);
        legalAdds.put(cellState, deriveLegalAdds(cellState));
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

        return legalAdds.get(cellState).contains(coordinate);
    }

    @Override
    public boolean moveIsPossible() {
        return
            legalAdds.get(CellState.BLACK).size() > 0 ||
            legalAdds.get(CellState.WHITE).size() > 0;
    }

    @Override
    public boolean moveIsPossibleFor(CellState cellState) {
        assert cellState != CellState.NONE;

        return legalAdds.get(cellState).size() > 0;
    }

    Set<Coordinate> deriveLegalAddsToDirection(
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
        while (multiplier < size) {
            ++multiplier;
            int x = row + (ver * multiplier);
            int y = col + (hor * multiplier);
            if (x < 0) {
                assert ver < 0;
                if (opponentsDiskFound) {
                    assert row > 0;
                }

                return Collections.emptySet();
            }
            if (y < 0) {
                assert hor < 0;
                if (opponentsDiskFound) {
                    assert col > 0;
                }

                return Collections.emptySet();
            }
            if (x >= size) {
                assert ver > 0;
                if (opponentsDiskFound) {
                    assert row < (size - 1);
                }

                return Collections.emptySet();
            }
            if (y >= size) {
                assert hor > 0;
                if (opponentsDiskFound) {
                    assert col < (size - 1);
                }

                return Collections.emptySet();
            }

            CellState currentCell = cellList.get(x).get(y);
            if (opponentsDiskFound) {
                if (currentCell == CellState.NONE) {
                    Set<Coordinate> coordinateSet = new HashSet<Coordinate>();
                    coordinateSet.add(coordinateFactory.getInstance(x, y));

                    return coordinateSet;
                } else if (currentCell == player) {
                    return Collections.emptySet();
                }
            } else {
                if (currentCell == opponent) {
                    opponentsDiskFound = true;
                } else {
                    return Collections.emptySet();
                }
            }
        }

        assert false;
        return Collections.emptySet();
    }

    Set<Coordinate> deriveLegalAddsFromCoordinate(
        int row,
        int col) {

        Set<Coordinate> legalAdds = new HashSet<Coordinate>();
        for (int hor = -1; hor <= 1; ++hor) {
            for (int ver = -1; ver <= 1; ++ver) {
                if (hor == 0 && ver == 0) {
                    continue;
                }

                legalAdds.addAll(deriveLegalAddsToDirection(row, col, hor, ver));
            }
        }

        return legalAdds;
    }

    private Set<Coordinate> deriveLegalAdds(CellState cellState) {
        assert cellState != CellState.NONE;

        Set<Coordinate> legalAdds = new HashSet<Coordinate>();
        final int size = cellList.size();
        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                legalAdds.addAll(deriveLegalAddsFromCoordinate(row, col));
            }
        }

        return legalAdds;
    }
}
