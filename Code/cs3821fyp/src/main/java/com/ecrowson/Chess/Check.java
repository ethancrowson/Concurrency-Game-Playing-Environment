package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.Iterator;

public class Check {
    public synchronized ArrayList<Tile> LegaliseMoves(Tile[][] board, Tile kingTile, ArrayList<Tile> pseudoLegalMoves, Piece testPiece, int file, int rank, boolean turnWhite){
        ArrayList<Tile> opponentMoves = new ArrayList<>();
        Piece temp = null;
        Iterator<Tile> iterator = pseudoLegalMoves.iterator();
        while (iterator.hasNext()) { //loop through the current pieces psuedo legal moves
            Tile pMove = iterator.next();
            if (pMove.isOccupied()) { // if can take, store opponent piece in temp
                temp = pMove.getPiece();
                pMove.removePiece();
            }
            pMove.setPiece(testPiece);
            if (testPiece.getType() == 'K'){kingTile = pMove;}
            board[file][rank].removePiece(); //move the test piece to psuedo legal move.
            testOpponentMoves:
            for (int f = 0; f < 8; f++) {
                for (int r = 0; r < 8; r++) {
                    if (board[f][r].isOccupied()) {
                        Piece p = board[f][r].getPiece();
                        if (p.isWhite != turnWhite) { 
                            opponentMoves.addAll(p.getMoves(board, f, r)); // loop through board to find opponents possible moves.
                            for (Tile t : opponentMoves) {
                                if (t == kingTile) { // if there is a move that takes king
                                    iterator.remove();
                                    break testOpponentMoves; // Safe removal
                                }
                            }
                        }
                    }
                }
            }
            board[file][rank].setPiece(testPiece);
            pMove.removePiece();
            if (temp != null) {
                pMove.setPiece(temp);
                temp = null;
            }
        }
        return pseudoLegalMoves;
    }
    public synchronized boolean checkMate(Tile[][] board, Tile kingTile, boolean turnWhite){
        ArrayList<Tile> possibleMoves = new ArrayList<Tile>();
        for (int f = 0; f <8; f++){
            for (int r = 0; r < 8; r++){
                Tile t = board[f][r];
                if (t.isOccupied() && turnWhite == t.getPiece().isWhite){
                    for (Tile tt: LegaliseMoves(board, kingTile, t.getPiece().getMoves(board,t.getX(),t.getY()),t.getPiece(),t.getX(),t.getY(),turnWhite)){
                        possibleMoves.add(tt);
                    }
                }
            }
        }
        System.out.println(possibleMoves.size()+", "+ turnWhite);
        if (possibleMoves.size() == 0){System.out.println("CHECKMATE");return true;}
        return false;
    }
    /*public synchronized ArrayList<Tile> LegaliseMoves(Tile[][] board, Tile kingTile, ArrayList<Tile> pseudoLegalMoves, Piece testPiece, int file, int rank, boolean turnWhite){
        ArrayList<Tile> opponentMoves = new ArrayList<>();
        Piece temp = null;
        for (Tile pMove: pseudoLegalMoves){
            if (pMove.isOccupied()){
                temp = pMove.getPiece();
            }
            pMove.setPiece(testPiece);
            board[file][rank].removePiece();
            System.out.println("test2");
            for (int f = 0; f < 8; f++){
                for (int r = 0; r < 8; r++){
                    if (board[f][r].isOccupied()){
                        Piece p = board[f][r].getPiece();
                        if (p.isWhite != turnWhite){
                            opponentMoves.addAll(p.getMoves(board,f,r));
                            for (Tile t: opponentMoves){
                                if (t == kingTile){
                                    pseudoLegalMoves.remove(pMove);
                                }
                            }
                        }
                    }
                }
            }
            board[file][rank].setPiece(testPiece);
            pMove.removePiece();
            if (temp != null){
                pMove.setPiece(temp);
                temp = null;
            }
        }
        return pseudoLegalMoves;
    }*/
}
