package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Rook subclass that extends from the abstract Piece class. Specifies methods
 * for the Rook piece on a chess board.
 * 
 * @author Ethan Crowson
 */
public class Rook extends Piece {

    /**
     * Constructs a Rook piece, calling the super in Piece.
     * 
     * @param colour colour of the Rook piece.
     * @param ps     a linked list of all of the pieces on the board.
     */
    public Rook(char colour, LinkedList<Piece> ps) {
        super(colour, ps);
    }

    /**
     * Getter for the type of piece. 'R' for Rook.
     */
    public char getType() {
        return 'R';
    }

    /**
     * Getter for the material value of piece. 5 for Rook.
     */
    public int getMaterial() {
        return 5;
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
        // UP
        for (int f = file - 1; f >= 0; f--) {
            if (board[f][rank].isOccupied()) {
                if (board[f][rank].getPiece().isWhite != isWhite) {
                    pMoves.add(board[f][rank]);
                }
                break;
            }
            pMoves.add(board[f][rank]);
        }
        // DOWN
        for (int f = file + 1; f < 8; f++) {
            if (board[f][rank].isOccupied()) {
                if (board[f][rank].getPiece().isWhite != isWhite) {
                    pMoves.add(board[f][rank]);
                }
                break;
            }
            pMoves.add(board[f][rank]);
        }
        // LEFT
        for (int r = rank - 1; r >= 0; r--) {
            if (board[file][r].isOccupied()) {
                if (board[file][r].getPiece().isWhite != isWhite) {
                    pMoves.add(board[file][r]);
                }
                break;
            }
            pMoves.add(board[file][r]);
        }
        // RIGHT
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
}
