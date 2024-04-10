package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Queen subclass that extends from the abstract Piece class. Specifies methods
 * for the Queen piece on a chess board.
 * 
 * @author Ethan Crowson
 */
public class Queen extends Piece {

    /**
     * Constructs a Queen Piece, calling the super constructor in Piece.
     * 
     * @param colour the colour of the Queen piece.
     * @param ps     a linked list of all of the pieces on the board.
     */
    public Queen(char colour, LinkedList<Piece> ps) {
        super(colour, ps);
    }

    /**
     * Getter for the type of piece. 'Q' for Queen.
     */
    public char getType() {
        return 'Q';
    }

    /**
     * Getter for the material value of piece. 9 for Queen.
     */
    public int getMaterial() {
        return 9;
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
        ArrayList<Tile> pMoves = new ArrayList<>();
        // Orthogonal Movement
        pMoves.addAll(getOrthogonalSlides(board, file, rank));
        // Diagonal Movement
        pMoves.addAll(getDiagonalSlides(board, file, rank));

        return pMoves;
    }
}
