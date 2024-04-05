package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.scene.image.ImageView;

/**
 * Abstract class for a piece on a chess board. Has subclasses of King, Queen,
 * Rook, Bishop, Knight and Pawn.
 * 
 * @author Ethan Crowson
 */
public abstract class Piece extends ImageView {
    char colour; // Character representation of the colour of the piece.
    boolean isWhite; // Boolean of if the piece is white or black.
    LinkedList<Piece> ps; // The list of pieces in play.
    boolean hasMoved; // Flag for if the piece has moved on the board. Castling and double pawn push.
    private boolean enPassant = false; // Flag for if the pawn is "enPassantable".

    /**
     * Constructs a new piece. Adds it to the list of pieces.
     * 
     * @param colour the colour of the piece.
     * @param ps     the linkedlist containing all of the pieces on the board.
     */
    public Piece(char colour, LinkedList<Piece> ps) {
        this.colour = colour;
        this.ps = ps;
        this.hasMoved = false;
        if (colour == 'W') {
            this.isWhite = true;
        } else {
            this.isWhite = false;
        }

        ps.add(this);
    }

    /**
     * 'Kills' the piece. - Removes it from the list of pieces.
     */
    public void kill() {
        ps.remove(this);
    }

    /**
     * Checks if a piece can take this piece (if they are different colours).
     * 
     * @param oPiece the opponent's piece.
     * @return if the piece is takeable.
     */
    public boolean canTake(Piece oPiece) {
        if (oPiece.isWhite != this.isWhite) {
            return true;
        }
        return false;
    }

    /**
     * Getter method for the hasMoved boolean.
     * 
     * @return returns if the piece has moved or not.
     */
    public boolean getHasMoved() {
        return this.hasMoved;
    }

    /**
     * Setter method for the hasMoved boolean.
     */
    public void setHasMoved() {
        this.hasMoved = true;
    }

    /**
     * Getter method for the enPassant flag boolean.
     * 
     * @return enPassant boolean.
     */
    public boolean getEnPassant() {
        return this.enPassant;
    }

    /**
     * Setter method for the enPassant flag boolean.
     * 
     * @param result the boolean result that enPassant will be set to.
     */
    public void setEnPassant(boolean result) {
        this.enPassant = result;
    }

    /**
     * Abstract getter method for the piece type.
     * 
     * @return teturns the type of the piece in character form.
     */
    public abstract char getType();

    /**
     * Abstract getter method for the piece material value.
     * 
     * @return the material value of the piece.
     */
    public abstract int getMaterial();

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
    public abstract ArrayList<Tile> getMoves(Tile[][] board, int file, int rank);
}
