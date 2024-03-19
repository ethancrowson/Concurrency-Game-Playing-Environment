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
        return pMoves;
    }
}
