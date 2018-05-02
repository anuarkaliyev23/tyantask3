package main;

import main.exceptions.DoubleHitException;

import java.util.Arrays;
import java.util.Objects;

public class BoardView {
    private Board board;
    private BoardCellView[][] boardCellView;

    public BoardView(boolean own) {
        board = new Board();
        boardCellView = new BoardCellView[Board.BOARD_SIZE][Board.BOARD_SIZE];

        fillView(own);
    }

    private boolean wasHit(int x, int y) {
        return boardCellView[x][y] == BoardCellView.HIT || boardCellView[x][y] == BoardCellView.MISS;
    }

    public boolean hit(int x, int y) {
        if (wasHit(x, y)) throw new DoubleHitException();

        boolean result;
        if (board.getValue(x, y)) {
            boardCellView[x][y] = BoardCellView.HIT;
            result = true;
        } else {
            boardCellView[x][y] = BoardCellView.MISS;
            result = false;
        }

        board.setCell(x, y, false);
        return result;

    }

    private void fillView(boolean own) {
        if (own) {
            fillViewOwn();
        } else {
            fillViewEnemy();
        }
    }

    private void fillViewOwn() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                boardCellView[i][j] = board.getBoard()[i][j] ? BoardCellView.BOAT : BoardCellView.EMPTY;
            }
        }
    }

    private void fillViewEnemy() {
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                boardCellView[i][j] = BoardCellView.HIDDEN;
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardView boardView = (BoardView) o;
        return Objects.equals(board, boardView.board) &&
                Arrays.equals(boardCellView, boardView.boardCellView);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(board);
        result = 31 * result + Arrays.hashCode(boardCellView);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Board.BOARD_SIZE; i++) {
            for (int j = 0; j < Board.BOARD_SIZE; j++) {
                builder.append(boardCellView[i][j].getUnicodeChar()).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
