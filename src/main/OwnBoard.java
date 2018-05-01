package main;

import main.exceptions.InvalidBoatSizeException;
import main.exceptions.OutOfBoundCoordinatesException;
import main.exceptions.UnexpectedEdgingCoordinatesException;
import main.exceptions.UnexpectedNotEdgingCoordinatesExceptions;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class OwnBoard {
    private static final int BOARD_SIZE = 10;
    private static final int MAX_BOAT_SIZE = 4;

    private boolean[][] board;

    public OwnBoard() {
        board = new boolean[BOARD_SIZE][BOARD_SIZE];
        placeAllBoats();
    }

    private void placeAllBoats() {
        int count = 1;
        for (int i = MAX_BOAT_SIZE; i >= 0; i--) { //size
            for (int j = 0; j <= count; j++) {
                placeBoat(i);
            }
            count++;
        }
    }

    private boolean isEdgeCoordinate(int coordinate) {
        if (coordinate < 0 || coordinate >= BOARD_SIZE) throw new OutOfBoundCoordinatesException();
        return (coordinate == 0 || coordinate == BOARD_SIZE - 1);
    }

    private boolean isAvailableForPlacement(int x, int y) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE) throw new OutOfBoundCoordinatesException();

        if (isEdgeCoordinate(x) && !isEdgeCoordinate(y)) {
            return isAvailableForPlacementEdgeX(x, y);
        } else if (!isEdgeCoordinate(x) && isEdgeCoordinate(y)) {
            return isAvailableForPlacementEdgeY(x, y);
        } else if (isEdgeCoordinate(x) && isEdgeCoordinate(y)) {
            return isAvailableForPlacementEdgeBoth(x, y);
        } else {
            return isAvailableForPlacementMiddleBoth(x, y);
        }
    }

    private boolean isAvailableForPlacementEdgeX(int x, int y) {
        if (!isEdgeCoordinate(x)) throw new UnexpectedNotEdgingCoordinatesExceptions();
        if (isEdgeCoordinate(y)) throw new UnexpectedEdgingCoordinatesException();

        int offset;
        if (x == 0) {
            offset = 1;
        } else {
            offset = -1;
        }

        return !(board[x][y] || board[x][y + 1] || board[x + offset][y + 1] || board[x + offset][y] || board[x + offset][y - 1] || board[x][y - 1]);
    }

    private boolean isAvailableForPlacementEdgeY(int x, int y) {
        if (!isEdgeCoordinate(y)) throw new UnexpectedNotEdgingCoordinatesExceptions();
        if (isEdgeCoordinate(x)) throw new UnexpectedEdgingCoordinatesException();

        int offset;
        if (y == 0) {
            offset = 1;
        } else {
            offset = -1;
        }

        return !(board[x][y] || board[x - 1][y] || board[x - 1][y + offset] || board[x][y + offset] || board[x + 1][y + offset] || board[x + 1][y]);
    }

    private boolean isAvailableForPlacementEdgeBoth(int x, int y) {
        if (!isEdgeCoordinate(x) || !isEdgeCoordinate(y)) throw new UnexpectedNotEdgingCoordinatesExceptions();

        if (x == 0 && y == 0) {
            return !(board[x][y] || board[x + 1][y] || board[x + 1][y + 1] || board[x][y + 1]);
        } else if (x == 0 && y == BOARD_SIZE - 1) {
            return !(board[x][y] || board[x + 1][y] || board[x + 1][y - 1] || board[x][y - 1]);
        } else if (x == BOARD_SIZE - 1 && y == 0) {
            return !(board[x][y] || board[x - 1][y] || board[x - 1][y - 1] || board[x][y + 1]);
        } else {
            return !(board[x][y] || board[x - 1][y] || board[x - 1][y - 1] || board[x][y - 1]);
        }
    }


    private boolean isAvailableForPlacementMiddleBoth(int x, int y) {
        if (isEdgeCoordinate(x) || isEdgeCoordinate(y)) throw new UnexpectedEdgingCoordinatesException();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (board[i][j]) return false;
            }
        }
        return true;
    }

    private void placeBoat(int size) {
        if (size < 0 || size > MAX_BOAT_SIZE) throw new InvalidBoatSizeException();

        int x = ThreadLocalRandom.current().nextInt(BOARD_SIZE);
        int y = ThreadLocalRandom.current().nextInt(BOARD_SIZE);

        boolean[] available = new boolean[size];
        for (int i = 0; i < size; i++) {
            available[i] = false;
        }

        if (x < BOARD_SIZE - size) {
            for (int i = x; i <= x + size; i++) {
                available[i] = isAvailableForPlacement(i, y);
            }
        } if (y < BOARD_SIZE - size) {
            for (int j = y; j <= y + size; j++) {
                available[j] = isAvailableForPlacement(x, j);
            }
        } else {
            placeBoat(size);
        }

        if (allArrayElementsAreTrue(available)) {
            placeBoat(size);
        }
    }

    private boolean allArrayElementsAreTrue(boolean[] values) {
        for (boolean value : values) {
            if (!value) return false;
        }
        return true;
    }

    public boolean[][] getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnBoard ownBoard = (OwnBoard) o;
        return Arrays.equals(board, ownBoard.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
