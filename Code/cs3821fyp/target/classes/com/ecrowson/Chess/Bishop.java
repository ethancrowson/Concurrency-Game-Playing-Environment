package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Bishop subclass that extends from the abstract Piece class. Specifies methods
 * for the Bishop piece on a chess board.
 * 
 * @author Ethan Crowson
 */
public class Bishop extends Piece {
    /**
     * Constructs a Bishop piece. Calls the super constructor in piece.
     * 
     * @param colour colour of the Bishop piece.
     * @param ps     a linked list of all of the pieces on the board.
     */
    public Bishop(char colour, LinkedList<Piece> ps) {
        super(colour, ps);
    }

    /**
     * Getter for the type of piece. 'B' for Bishop.
     */
    public char getType() {
        return 'B';
    }

    /**
     * Getter for the material value of piece. 3 for Bishop.
     */
    public int getMaterial() {
        return 3;
    }

    /**
     * Loops through the board and returns the pseudo legal moves that the piece can
     * make.
     * 
     * @param board the board of tiles the game is being played on.
     * @param file  the file (y) of the piece on the board.
     * @param rank  the rank (x) of the piece on the board.
     * @return the possible tiles that the piece can move to
     *         (including takes).
     */
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank) {
        return getDiagonalSlides(board, file, rank);
    }
}
