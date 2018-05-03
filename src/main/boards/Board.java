package main.boards;

import main.exceptions.InvalidBoatSizeException;
import main.exceptions.OutOfBoundCoordinatesException;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public static final int BOARD_SIZE = 10;
    private static final int MAX_BOAT_SIZE = 4;

    private boolean[][] board;

    public void setCell(int x, int y, boolean value) {
        board[x][y] = value;
    }

    public Board() {
        board = new boolean[BOARD_SIZE][BOARD_SIZE];
        placeAllBoats();
    }

    public Board(Board other) {
        board = new boolean[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = other.getBoard()[i][j];
            }
        }
    }

    public boolean getValue(int x, int y) {
        checkCoordinatesInBounds(x);
        checkCoordinatesInBounds(y);

        return board[x][y];
    }

    private void checkCoordinatesInBounds(int coordinate) {
        if (coordinate < 0 || coordinate >= BOARD_SIZE) throw new OutOfBoundCoordinatesException();
    }

    private void placeAllBoats() {
        placeBoat(4);
        placeBoat(3);
        placeBoat(3);
        placeBoat(2);
        placeBoat(2);
        placeBoat(2);
        placeBoat(1);
        placeBoat(1);
        placeBoat(1);
        placeBoat(1);
    }

    private boolean isEdgeCoordinate(int coordinate) {
        checkCoordinatesInBounds(coordinate);
        return (coordinate == 0 || coordinate == BOARD_SIZE - 1);
    }

    private boolean allNeighboursAreFalse(int x, int y) {
        checkCoordinatesInBounds(x);
        checkCoordinatesInBounds(y);
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                try {
                    if (board[i][j]) {
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //Do nothing
                }
            }
        }
        return true;
    }

    private boolean isAvailableForPlacement(int x, int y) {
        checkCoordinatesInBounds(x);
        checkCoordinatesInBounds(y);

        return allNeighboursAreFalse(x, y);
    }



    private void placeBoat(int size) {
        if (size < 1 || size > MAX_BOAT_SIZE) throw new InvalidBoatSizeException();

        int x = ThreadLocalRandom.current().nextInt(BOARD_SIZE);
        int y = ThreadLocalRandom.current().nextInt(BOARD_SIZE);

        boolean availableFlag = true;
        boolean vertical = true;

        if (x + size < BOARD_SIZE) {
            for (int i = x; i < x + size; i++) {
                if (!isAvailableForPlacement(i, y)) availableFlag = false;
            }
        }
        else if (y + size < BOARD_SIZE) {
            vertical = false;
            for (int j = y; j < y + size; j++) {
                if (!isAvailableForPlacement(x, j)) availableFlag = false;
            }
        }
        else {
            availableFlag = false;
        }

        if (availableFlag) {
            if (vertical) {
                for (int i = x; i < x + size; i++) {
                    board[i][y] = true;
                }
            }
            else {
                for (int j = y; j < y + size; j++) {
                    board[x][j] = true;
                }
            }
        }
        else {
            placeBoat(size);
        }
    }


    public boolean[][] getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board ownBoard = (Board) o;
        return Arrays.equals(board, ownBoard.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}
