package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Contains the logic associated with legalising possible moves on the board.
 * 
 * @author Ethan Crowson
 */
public class Check {

    /**
     * Takes an array list of pseudo legal moves and returns an arraylist of only
     * the legal ones.
     * 
     * @param board            the playing board constructed from tiles.
     * @param kingTile         the tile with the king needed for observing check.
     * @param pseudoLegalMoves the array list of psuedo legal moves.
     * @param testPiece        the piece whose moves are being legalised.
     * @param file             the file of the tile the piece is currently
     *                         occupying.
     * @param rank             the rank of the tile the piece is currently
     *                         occupying.
     * @param turnWhite        the boolean representing whose turn it is (W/B).
     * @return the legal moves the piece can play.
     */
    public synchronized ArrayList<Tile> LegaliseMoves(Tile[][] board, Tile kingTile, ArrayList<Tile> pseudoLegalMoves,
            Piece testPiece, int file, int rank, boolean turnWhite) {
        ArrayList<Tile> opponentMoves = new ArrayList<>(); // The moves all opponent tiles can make on the board.
        Piece temp = null; // Will store a piece temporarily whilst testing moves.
        Iterator<Tile> iterator = pseudoLegalMoves.iterator();

        while (iterator.hasNext()) { // Loop through the current pieces psuedo legal moves
            Tile pMove = iterator.next();
            if (pMove.isOccupied()) { // If can take, store opponent piece in temp
                temp = pMove.getPiece();
                pMove.removePiece();
            }
            pMove.setPiece(testPiece);
            if (testPiece.getType() == 'K') {
                kingTile = pMove;
            }
            board[file][rank].removePiece(); // Move the test piece to pseudo legal move.
            testOpponentMoves: for (int f = 0; f < 8; f++) { // Loop through board to find opponents possible moves if
                                                             // above move is made.
                for (int r = 0; r < 8; r++) {
                    if (board[f][r].isOccupied()) {
                        Piece p = board[f][r].getPiece();
                        if (p.isWhite != turnWhite) {
                            opponentMoves.addAll(p.getMoves(board, f, r));
                            for (Tile t : opponentMoves) {
                                if (t == kingTile) { // If opponent can make a move that takes the king.
                                    iterator.remove(); // Safely remove pMove from the pseudo legal moves.
                                    break testOpponentMoves;
                                }
                            }
                        }
                    }
                }
            }
            // Restore board to normality after testing.
            board[file][rank].setPiece(testPiece);
            if (testPiece.getType() == 'K') {
                kingTile = board[file][rank];
            }
            pMove.removePiece();
            if (temp != null) {
                pMove.setPiece(temp);
                temp = null;
            }
        }
        return pseudoLegalMoves;
    }

    /**
     * Returns whether there is a checkmate on the board or not.
     * 
     * @param board     the playing board constructed from tiles.
     * @param kingTile  the tile with the king needed for observing checkmate.
     * @param turnWhite the boolean representing whose turn it is (W/B).
     * @param ps        a linked list of all of the pieces on the board.
     * @return if the game is still playing/ in stalemate/ in checkmate.
     */
    public synchronized int checkMate(Tile[][] board, Tile kingTile, boolean turnWhite, LinkedList<Piece> ps) {
        ArrayList<Tile> possibleMoves = new ArrayList<Tile>();
        ArrayList<Tile> checkingMoves = new ArrayList<Tile>();
        // Loops through all legal moves that a colour can make.
        for (int f = 0; f < 8; f++) {
            for (int r = 0; r < 8; r++) {
                Tile t = board[f][r];
                if (t.isOccupied()) {
                    if (turnWhite == t.getPiece().isWhite) {
                        for (Tile tt : LegaliseMoves(board, kingTile, t.getPiece().getMoves(board, t.getX(), t.getY()),
                                t.getPiece(), t.getX(), t.getY(), turnWhite)) {
                            possibleMoves.add(tt);
                        }
                    } else {
                        for (Tile tt2 : t.getPiece().getMoves(board, t.getX(), t.getY())) {
                            if (tt2 == kingTile) {
                                checkingMoves.add(tt2);
                            }
                        }
                    }
                }

            }
        }
        if (possibleMoves.size() == 0) {
            if (checkingMoves.size() == 0) {
                return 1; // If no legal moves and king is not in check then it must be a stalemate.
            }
            return 2; // If a colour can make no legal moves and king is in check then it must be
                      // checkmate.
        }
        if (ps.size() == 2) {
            return 1;
        }
        return 0; // No stalemate or checkmate.
    }

}
