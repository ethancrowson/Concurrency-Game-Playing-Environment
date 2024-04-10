package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Pawn subclass that extends from the abstract Piece class. Specifies methods
 * for the Pawn piece on a chess board.
 * 
 * @author Ethan Crowson
 */
public class Pawn extends Piece {

    /**
     * Constructs a Pawn piece, calling the super in Piece.
     * 
     * @param colour colour of the Pawn piece.
     * @param ps     a linked list of all of the pieces on the board.
     */
    public Pawn(char colour, LinkedList<Piece> ps) {
        super(colour, ps);
    }

    /**
     * Getter for the type of piece. 'P' for Pawn.
     */
    public char getType() {
        return 'P';
    }

    /**
     * Getter for the material value of piece. 1 for Pawn.
     */
    public int getMaterial() {
        return 1;
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
        // WHITE PAWN
        if (isWhite && rank != 0) {
            if (!board[file][rank - 1].isOccupied()) {
                pMoves.add(board[file][rank - 1]);
                if (!getHasMoved()) {
                    if (!board[file][rank - 2].isOccupied()) {
                        pMoves.add(board[file][rank - 2]);
                    }
                }
            }
            if (file != 0) {
                if (board[file - 1][rank - 1].isOccupied() && canTake(board[file - 1][rank - 1].getPiece())) {
                    pMoves.add(board[file - 1][rank - 1]);
                }
                if (board[file - 1][rank].isOccupied() && board[file - 1][rank].getPiece().getEnPassant()
                        && rank == 3) {
                    pMoves.add(board[file - 1][rank - 1]);
                }
            }
            if (file != 7) {
                if (board[file + 1][rank - 1].isOccupied() && canTake(board[file + 1][rank - 1].getPiece())) {
                    pMoves.add(board[file + 1][rank - 1]);
                }
                if (board[file + 1][rank].isOccupied() && board[file + 1][rank].getPiece().getEnPassant()
                        && rank == 3) {
                    pMoves.add(board[file + 1][rank - 1]);
                }
            }
            // BLACK PAWN
        } else if (!isWhite && rank != 7) {
            if (!board[file][rank + 1].isOccupied()) {
                pMoves.add(board[file][rank + 1]);
                if (!getHasMoved()) {
                    if (!board[file][rank + 2].isOccupied()) {
                        pMoves.add(board[file][rank + 2]);
                    }
                }
            }
            if (file != 0) {
                if (board[file - 1][rank + 1].isOccupied() && canTake(board[file - 1][rank + 1].getPiece())) {
                    pMoves.add(board[file - 1][rank + 1]);
                }
                if (board[file - 1][rank].isOccupied() && board[file - 1][rank].getPiece().getEnPassant()
                        && rank == 4) {
                    pMoves.add(board[file - 1][rank + 1]);
                }
            }
            if (file != 7) {
                if (board[file + 1][rank + 1].isOccupied() && canTake(board[file + 1][rank + 1].getPiece())) {
                    pMoves.add(board[file + 1][rank + 1]);
                }
                if (board[file + 1][rank].isOccupied() && board[file + 1][rank].getPiece().getEnPassant()
                        && rank == 4) {
                    pMoves.add(board[file + 1][rank + 1]);
                }
            }
        }
        return pMoves;
    }
}
