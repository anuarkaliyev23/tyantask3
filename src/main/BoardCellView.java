package main;

import main.exceptions.InvalidBoardCellViewException;

public enum  BoardCellView {
    BOAT, EMPTY, HIDDEN, HIT, MISS;

    private static final char BOAT_CHAR = '＋';
    private static final char EMPTY_CHAR = '０';
    private static final char HIDDEN_CHAR = '＊';
    private static final char HIT_CHAR = 'ｘ';
    private static final char MISS_CHAR = '―';


    public char getUnicodeChar() {
        switch (this) {
            case BOAT: return BOAT_CHAR;
            case EMPTY: return EMPTY_CHAR;
            case HIDDEN: return HIDDEN_CHAR;
            case HIT: return HIT_CHAR;
            case MISS: return MISS_CHAR;
            default: throw new InvalidBoardCellViewException();
        }
    }
}
