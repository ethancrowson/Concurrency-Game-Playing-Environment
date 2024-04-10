package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Knight subclass that extends from the abstract Piece class. Specifies methods
 * for the Knight piece on a chess board.
 * 
 * @author Ethan Crowson
 */
public class Knight extends Piece {

    /**
     * Constructs a Knight piece. Calls the super constructor in piece.
     * 
     * @param colour colour of the Knight piece.
     * @param ps     a linked list of all of the pieces on the board.
     */
    public Knight(char colour, LinkedList<Piece> ps) {
        super(colour, ps);
    }

    /**
     * Getter for the type of piece. 'N' for kNight.
     */
    public char getType() {
        return 'N';
    }

    /**
     * Getter for the material value of piece. 3 for Knight.
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
        ArrayList<Tile> pMoves = new ArrayList<>();
        if (rank > 1) {
            // UP LEFT
            if (file > 0) {
                if (!board[file - 1][rank - 2].isOccupied()) {
                    pMoves.add(board[file - 1][rank - 2]);
                } else {
                    if (board[file - 1][rank - 2].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file - 1][rank - 2]);
                    }
                }
            }
            // UP RIGHT
            if (file < 7) {
                if (!board[file + 1][rank - 2].isOccupied()) {
                    pMoves.add(board[file + 1][rank - 2]);
                } else {
                    if (board[file + 1][rank - 2].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file + 1][rank - 2]);
                    }
                }
            }
        }

        if (file < 6) {
            // RIGHT UP
            if (rank > 0) {
                if (!board[file + 2][rank - 1].isOccupied()) {
                    pMoves.add(board[file + 2][rank - 1]);
                } else {
                    if (board[file + 2][rank - 1].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file + 2][rank - 1]);
                    }
                }
            }
            // RIGHT DOWN
            if (rank < 7) {
                if (!board[file + 2][rank + 1].isOccupied()) {
                    pMoves.add(board[file + 2][rank + 1]);
                } else {
                    if (board[file + 2][rank + 1].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file + 2][rank + 1]);
                    }
                }
            }
        }

        if (rank < 6) {
            // DOWN RIGHT
            if (file < 7) {
                if (!board[file + 1][rank + 2].isOccupied()) {
                    pMoves.add(board[file + 1][rank + 2]);
                } else {
                    if (board[file + 1][rank + 2].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file + 1][rank + 2]);
                    }
                }
            }
            // DOWN LEFT
            if (file > 0) {
                if (!board[file - 1][rank + 2].isOccupied()) {
                    pMoves.add(board[file - 1][rank + 2]);
                } else {
                    if (board[file - 1][rank + 2].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file - 1][rank + 2]);
                    }
                }
            }
        }
        if (file > 1) {
            // LEFT DOWN
            if (rank < 7) {
                if (!board[file - 2][rank + 1].isOccupied()) {
                    pMoves.add(board[file - 2][rank + 1]);
                } else {
                    if (board[file - 2][rank + 1].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file - 2][rank + 1]);
                    }
                }
            }
            // LEFT UP
            if (rank > 0) {
                if (!board[file - 2][rank - 1].isOccupied()) {
                    pMoves.add(board[file - 2][rank - 1]);
                } else {
                    if (board[file - 2][rank - 1].getPiece().isWhite != this.isWhite) {
                        pMoves.add(board[file - 2][rank - 1]);
                    }
                }
            }
        }
        return pMoves;
    }
}
