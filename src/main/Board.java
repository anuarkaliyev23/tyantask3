package main;

import main.exceptions.InvalidBoatSizeException;
import main.exceptions.OutOfBoundCoordinatesException;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public static final int BOARD_SIZE = 10;
    private static final int MAX_BOAT_SIZE = 4;

    private boolean[][] board;

    public Board() {
        board = new boolean[BOARD_SIZE][BOARD_SIZE];
        placeAllBoats();
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

        System.out.println("Iteration");
        int x = ThreadLocalRandom.current().nextInt(BOARD_SIZE);
        int y = ThreadLocalRandom.current().nextInt(BOARD_SIZE);

        boolean availableFlag = true;
        boolean vertical = true;

        if (x + size < BOARD_SIZE) {
            System.out.println("X + SIZE: " + (x + size));
            System.out.println("X : " + x);
            System.out.println("SIZE : " + size);
            System.out.println("Y : " + y);
            for (int i = x; i < x + size; i++) {
                if (!isAvailableForPlacement(i, y)) availableFlag = false;
            }
        }
        else if (y + size < BOARD_SIZE) {
            vertical = false;

            System.out.println("Y + SIZE:" + (y + size));
            System.out.println("X : " + x);
            System.out.println("SIZE : " + size);
            System.out.println("Y : " + y);
            for (int j = y; j < y + size; j++) {
                if (!isAvailableForPlacement(x, j)) availableFlag = false;
            }
        }
        else {
            availableFlag = false;
        }

        if (availableFlag) {
            if (vertical) {
                for (int i = x; i < x + size - 1; i++) {
                    System.out.println("Vertical");
                    System.out.println("size: " + size);
                    System.out.println("x: " + x);
                    System.out.println("i : " + i);
                    System.out.println("y: " + y);
                    board[i][y] = true;
                }
            }
            else {
                for (int j = y; j < y + size - 1; j++) {
                    System.out.println("Horizontal:");
                    System.out.println("size :" + size);
                    System.out.println("x : " + x);
                    System.out.println("j: " + j);
                    System.out.println("y: " + y);
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
