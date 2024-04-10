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
    private char colour; // Character representation of the colour of the piece.
    boolean isWhite; // Boolean of if the piece is white or black.
    private LinkedList<Piece> ps; // The list of pieces in play.
    private boolean hasMoved; // Flag for if the piece has moved on the board. Castling and double pawn push.
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
     * Getter method for the colour of the piece.
     * 
     * @return the colour of the piece in char format.
     */
    public char getColour() {
        return this.colour;
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

    /**
     * Loops through the board and returns the pseudo legal Orthogonal sliding moves
     * that the piece can make.
     * 
     * @param board the board of tiles the game is being played on.
     * @param file  the file (y) of the piece on the board.
     * @param rank  the rank (x) of the piece on the board.
     * @return the possible tiles that the piece can move to
     *         (including takes).
     */
    public ArrayList<Tile> getOrthogonalSlides(Tile[][] board, int file, int rank) {
        ArrayList<Tile> pMoves = new ArrayList<>();
        // Up
        for (int f = file - 1; f >= 0; f--) {
            if (board[f][rank].isOccupied()) {
                if (board[f][rank].getPiece().isWhite != isWhite) {
                    pMoves.add(board[f][rank]);
                }
                break;
            }
            pMoves.add(board[f][rank]);
        }
        // Down
        for (int f = file + 1; f < 8; f++) {
            if (board[f][rank].isOccupied()) {
                if (board[f][rank].getPiece().isWhite != isWhite) {
                    pMoves.add(board[f][rank]);
                }
                break;
            }
            pMoves.add(board[f][rank]);
        }
        // Left
        for (int r = rank - 1; r >= 0; r--) {
            if (board[file][r].isOccupied()) {
                if (board[file][r].getPiece().isWhite != isWhite) {
                    pMoves.add(board[file][r]);
                }
                break;
            }
            pMoves.add(board[file][r]);
        }
        // Right
        for (int r = rank + 1; r < 8; r++) {
            if (board[file][r].isOccupied()) {
                if (board[file][r].getPiece().isWhite != isWhite) {
                    pMoves.add(board[file][r]);
                }
                break;
            }
            pMoves.add(board[file][r]);
        }
        return pMoves;
    }

    /**
     * Loops through the board and returns the pseudo legal Diagonal sliding moves
     * that the piece can make.
     * 
     * @param board the board of tiles the game is being played on.
     * @param file  the file (y) of the piece on the board.
     * @param rank  the rank (x) of the piece on the board.
     * @return the possible tiles that the piece can move to
     *         (including takes).
     */
    public ArrayList<Tile> getDiagonalSlides(Tile[][] board, int file, int rank) {
        // Up-Left
        ArrayList<Tile> pMoves = new ArrayList<>();
        int tempfile;
        int temprank;
        if (file != 0 || rank != 0) {
            temprank = rank - 1;
            for (int f = file - 1; f >= 0; f--) {
                if (temprank == -1 || f == -1) {
                    break;
                } else if (board[f][temprank].isOccupied()) {
                    if (board[f][temprank].getPiece().isWhite != isWhite) {
                        pMoves.add(board[f][temprank]);
                    }
                    break;
                }
                pMoves.add(board[f][temprank]);
                temprank--;
            }
        }
        // Up-Right
        if (file != 0 || rank != 7) {
            tempfile = file - 1;
            for (int r = rank + 1; r < 8; r++) {
                if (tempfile == -1 || r == 8) {
                    break;
                } else if (board[tempfile][r].isOccupied()) {
                    if (board[tempfile][r].getPiece().isWhite != isWhite) {
                        pMoves.add(board[tempfile][r]);
                    }
                    break;
                }
                pMoves.add(board[tempfile][r]);
                tempfile--;
            }
        }
        // Down-Left
        if (file != 7 || rank != 0) {
            tempfile = file + 1;
            for (int r = rank - 1; r >= 0; r--) {
                if (tempfile == 8 || r == -1) {
                    break;
                } else if (board[tempfile][r].isOccupied()) {
                    if (board[tempfile][r].getPiece().isWhite != isWhite) {
                        pMoves.add(board[tempfile][r]);
                    }
                    break;
                }
                pMoves.add(board[tempfile][r]);
                tempfile++;
            }
        }
        // Down-Right
        if (file != 7 || rank != 7) {
            temprank = rank + 1;
            for (int f = file + 1; f < 8; f++) {
                if (temprank == 8 || f == 8) {
                    break;
                } else if (board[f][temprank].isOccupied()) {
                    if (board[f][temprank].getPiece().isWhite != isWhite) {
                        pMoves.add(board[f][temprank]);
                    }
                    break;
                }
                pMoves.add(board[f][temprank]);
                temprank++;
            }
        }
        return pMoves;
    }
}
