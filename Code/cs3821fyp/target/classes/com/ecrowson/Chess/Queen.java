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
        int temprank;
        int tempfile;
        // DIAG UP LEFT
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
        // DIAGONAL DOWN RIGHT
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
        // DIAGONAL DOWN LEFT
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
        // DIAGONAL UP RIGHT
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
