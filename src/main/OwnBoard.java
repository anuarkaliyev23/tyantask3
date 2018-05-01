package main;

import main.exceptions.OutOfBoundCoordinatesException;
import main.exceptions.UnexpectedEdgingCoordinatesException;
import main.exceptions.UnexpectedNotEdgingCoordinatesExceptions;

public class OwnBoard {
    private static final int BOARD_SIZE = 10;
    private boolean[][] board;

    public OwnBoard() {
        board = new boolean[BOARD_SIZE][BOARD_SIZE];
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

    private boolean isAvailableForPlacementMiddleBoth(int x,int y) {
        if (isEdgeCoordinate(x) || isEdgeCoordinate(y)) throw new UnexpectedEdgingCoordinatesException();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (board[i][j]) return false;
            }
        }
        return true;
    }

}
