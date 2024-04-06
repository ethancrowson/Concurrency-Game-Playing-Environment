package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * King subclass that extends from the abstract Piece class. Specifies methods
 * for the King piece on a chess board.
 * 
 * @author Ethan Crowson
 */
public class King extends Piece {

    /**
     * Constructs a King piece. Calls the super constructor in piece.
     * 
     * @param colour colour of the Knight piece.
     * @param ps     a linked list of all of the pieces on the board.
     */
    public King(char colour, LinkedList<Piece> ps) {
        super(colour, ps);
    }

    /**
     * Getter for the type of piece. 'K' for King.
     */
    public char getType() {
        return 'K';
    }

    /**
     * Getter for the material value of piece. King is invaluable.
     */
    public int getMaterial() {
        return 0;
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
        if (rank > 0) {
            if (board[file][rank - 1].occupiedTakeable(this)) {
                pMoves.add(board[file][rank - 1]); // UP
            }
            if (file > 0) {
                if (board[file - 1][rank - 1].occupiedTakeable(this)) {
                    pMoves.add(board[file - 1][rank - 1]); // UP LEFT
                }
            }
            if (file < 7) {
                if (board[file + 1][rank - 1].occupiedTakeable(this)) {
                    pMoves.add(board[file + 1][rank - 1]); // UP RIGHT
                }
            }
        }
        if (rank < 7) {
            if (board[file][rank + 1].occupiedTakeable(this)) {
                pMoves.add(board[file][rank + 1]); // DOWN
            }
            if (file > 0) {
                if (board[file - 1][rank + 1].occupiedTakeable(this)) {
                    pMoves.add(board[file - 1][rank + 1]); // DOWN LEFT
                }
            }
            if (file < 7) {
                if (board[file + 1][rank + 1].occupiedTakeable(this)) {
                    pMoves.add(board[file + 1][rank + 1]); // DOWN RIGHT
                }
            }
        }
        if (file > 0) {
            if (board[file - 1][rank].occupiedTakeable(this)) {
                pMoves.add(board[file - 1][rank]); // LEFT
            }
        }
        if (file < 7) {
            if (board[file + 1][rank].occupiedTakeable(this)) {
                pMoves.add(board[file + 1][rank]); // RIGHT
            }
        }
        // WHITE CASTLING LEFT
        if (this.getHasMoved() == false && board[0][7].isOccupied()) {
            if (board[0][7].getPiece().getHasMoved() == false) {
                if (!board[file - 1][rank].isOccupied() && !board[file - 2][rank].isOccupied()
                        && !board[file - 3][rank].isOccupied()) {
                    // CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file - 2][rank]);
                }
            }
        }
        // WHITE CASTLING RIGHT
        if (this.getHasMoved() == false && board[7][7].isOccupied()) {
            if (board[7][7].getPiece().getHasMoved() == false) {
                if (!board[file + 1][rank].isOccupied() && !board[file + 2][rank].isOccupied()) {
                    // CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file + 2][rank]);
                }
            }
        }
        // BLACK CASTLING LEFT
        if (this.getHasMoved() == false && board[0][0].isOccupied()) {
            if (board[0][0].getPiece().getHasMoved() == false) {
                if (!board[file - 1][rank].isOccupied() && !board[file - 2][rank].isOccupied()
                        && !board[file - 3][rank].isOccupied()) {
                    // CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file - 2][rank]);
                }
            }
        }
        // BLACK CASTLING RIGHT
        if (this.getHasMoved() == false && board[7][0].isOccupied()) {
            if (board[7][0].getPiece().getHasMoved() == false) {
                if (!board[file + 1][rank].isOccupied() && !board[file + 2][rank].isOccupied()) {
                    // CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file + 2][rank]);
                }
            }
        }
        return pMoves;
    }
}
