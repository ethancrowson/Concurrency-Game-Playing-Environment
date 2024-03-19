package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Pawn extends Piece {
    public Pawn(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'P';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        ArrayList<Tile> pMoves = new ArrayList<>();
        //WHITE PAWN
        if(isWhite){
            if (!board[file][rank-1].isOccupied()){
                pMoves.add(board[file][rank-1]);
                if (!hasMoved){
                    if (!board[file][rank-2].isOccupied()){
                        pMoves.add(board[file][rank-2]);
                    }
                }
            }
            if (file != 0){
                if (board[file-1][rank-1].isOccupied()){
                    pMoves.add(board[file-1][rank-1]);
                }
            }
            if (file != 7){
                if (board[file+1][rank-1].isOccupied()){
                    pMoves.add(board[file+1][rank-1]);
                }
            }
        //BLACK PAWN    
        }else {
            if (!board[file][rank+1].isOccupied()){
                pMoves.add(board[file][rank+1]);
                if (!hasMoved){
                    if (!board[file][rank+2].isOccupied()){
                        pMoves.add(board[file][rank+2]);
                    }
                }
            }
            if (file != 0){
                if (board[file-1][rank+1].isOccupied()){
                    pMoves.add(board[file-1][rank+1]);
                }
            }
            if (file != 7){
                if (board[file+1][rank+1].isOccupied()){
                    pMoves.add(board[file+1][rank+1]);
                }
            }
        }
        return pMoves;
    }
}
