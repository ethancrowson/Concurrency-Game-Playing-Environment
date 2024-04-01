package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class King extends Piece {
    public King(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'K';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        ArrayList<Tile> pMoves = new ArrayList<>();
        if (rank > 0) {
            if (board[file][rank-1].occupiedTakeable(this)){
                pMoves.add(board[file][rank-1]); // UP
            }
            if (file > 0){
                if (board[file-1][rank-1].occupiedTakeable(this)){
                    pMoves.add(board[file-1][rank-1]); // UP LEFT
                }
            }
            if (file < 7){
                if (board[file+1][rank-1].occupiedTakeable(this)){
                    pMoves.add(board[file+1][rank-1]); // UP RIGHT
                }
            }
        }
        if (rank < 7) {
            if (board[file][rank+1].occupiedTakeable(this)){
                pMoves.add(board[file][rank+1]); // DOWN
            }
            if (file > 0){
                if (board[file-1][rank+1].occupiedTakeable(this)){
                    pMoves.add(board[file-1][rank+1]); // DOWN LEFT
                }
            }
            if (file < 7){
                if (board[file+1][rank+1].occupiedTakeable(this)){
                    pMoves.add(board[file+1][rank+1]); // DOWN RIGHT
                }
            }
        }
        if (file > 0){
            if (board[file-1][rank].occupiedTakeable(this)){
                pMoves.add(board[file-1][rank]); // LEFT
            }
        }
        if (file < 7){
            if (board[file+1][rank].occupiedTakeable(this)){
                pMoves.add(board[file+1][rank]); // RIGHT
            }
        }
        // WHITE CASTLING LEFT
        if (this.hasMoved == false && board[0][7].isOccupied()){
            if(board[0][7].getPiece().getHasMoved() == false){
                if (!board[file-1][rank].isOccupied() && !board[file-2][rank].isOccupied() && !board[file-3][rank].isOccupied()){
                    //CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file-2][rank]);
                }
            }
        }
        // WHITE CASTLING RIGHT
        if (this.hasMoved == false && board[7][7].isOccupied()){
            if (board[7][7].getPiece().getHasMoved() == false){
                if (!board[file+1][rank].isOccupied() && !board[file+2][rank].isOccupied()){
                    //CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file+2][rank]);
                }
            }
        }
        // BLACK CASTLING LEFT
        if (this.hasMoved == false && board[0][0].isOccupied()){
            if (board[0][0].getPiece().getHasMoved() == false){
                if (!board[file-1][rank].isOccupied() && !board[file-2][rank].isOccupied() && !board[file-3][rank].isOccupied()){
                    //CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file-2][rank]);
                }
            }
        }
        // BLACK CASTLING RIGHT
        if (this.hasMoved == false && board[7][0].isOccupied()){
            if (board[7][0].getPiece().getHasMoved() == false){
                if (!board[file+1][rank].isOccupied() && !board[file+2][rank].isOccupied()){
                    //CHECK IF TILES INBETWEEN ARE POTENTIAL CHECKS (IF SO NO MOVE)
                    pMoves.add(board[file+2][rank]);
                }
            }
        }
        return pMoves;
    }
}
