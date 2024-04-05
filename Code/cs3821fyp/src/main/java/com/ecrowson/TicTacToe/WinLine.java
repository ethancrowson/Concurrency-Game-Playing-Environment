package com.ecrowson.TicTacToe;

/**
 * A line of spaces on the game board.
 * 
 * @author Ethan Crowson
 */
public class WinLine {
    private Space[] spaces; // An array of spaces. A row/column/diagonal of spaces.

    /**
     * Constructs a new WinLine instance.
     * 
     * @param spaces The spaces that combine to make a line.
     */
    public WinLine(Space... spaces) {
        this.spaces = spaces;
    }

    /**
     * Checks to see if the line's corresponding spaces match in value. If a line
     * has been
     * completed.
     * 
     * @return Boolean If a given line has all spaces with the same value.
     */
    public Boolean isComplete() {
        // Checks if a combination is complete (3 spaces in a line have the same value).
        if (spaces[0].getValue().isEmpty()) {
            return false; // returns false if the first space in the line is empty.
        }
        return spaces[0].getValue().equals(spaces[1].getValue())
                && spaces[0].getValue().equals(spaces[2].getValue());
    }

    /**
     * Returns the space associated with the provided index in the line.
     * 
     * @param i index of the space being requested.
     * @return the space at index i.
     */
    public Space getSpace(int i) {
        return spaces[i];
    }

    public String toString() {
        return "[" + spaces[0].getValue() + "],[" + spaces[1].getValue() + "],["
                + spaces[2].getValue() + "]";
    }
}
