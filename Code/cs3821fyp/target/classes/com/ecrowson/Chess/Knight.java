package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Knight extends Piece {
    public Knight(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'N';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        ArrayList<Tile> pMoves = new ArrayList<>();
            if (rank > 1){
                //UP LEFT
                if (file > 0){
                    if (!board[file-1][rank-2].isOccupied()){
                        pMoves.add(board[file-1][rank-2]);
                    }else {
                        if (board[file-1][rank-2].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file-1][rank-2]);
                        }
                    }
                }
                //UP RIGHT
                if (file < 7){
                    if (!board[file+1][rank-2].isOccupied()){
                        pMoves.add(board[file+1][rank-2]);
                    }else {
                        if (board[file+1][rank-2].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file+1][rank-2]);
                        }
                    }
                }
            }

            if (file < 6) {
                //RIGHT UP
                if (rank > 0){
                    if (!board[file+2][rank-1].isOccupied()){
                        pMoves.add(board[file+2][rank-1]);
                    }else {
                        if (board[file+2][rank-1].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file+2][rank-1]);
                        }
                    }
                }
                //RIGHT DOWN
                if (rank < 7){
                    if (!board[file+2][rank+1].isOccupied()){
                        pMoves.add(board[file+2][rank+1]);
                    }else {
                        if (board[file+2][rank+1].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file+2][rank+1]);
                        }
                    }
                }
            }

            if (rank < 6) {
                //DOWN RIGHT
                if (file < 7){
                    if (!board[file+1][rank+2].isOccupied()){
                        pMoves.add(board[file+1][rank+2]);
                    }else {
                        if (board[file+1][rank+2].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file+1][rank+2]);
                        }
                    }
                }
                //DOWN LEFT
                if (file > 0){
                    if (!board[file-1][rank+2].isOccupied()){
                        pMoves.add(board[file-1][rank+2]);
                    }else {
                        if (board[file-1][rank+2].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file-1][rank+2]);
                        }
                    }
                }
            }
            if (file > 1){
                //LEFT DOWN
                if (rank < 7){
                    if (!board[file-2][rank+1].isOccupied()){
                        pMoves.add(board[file-2][rank+1]);
                    }else {
                        if (board[file-2][rank+1].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file-2][rank+1]);
                        }
                    }
                }
                //LEFT UP
                if (rank > 0){
                    if (!board[file-2][rank-1].isOccupied()){
                        pMoves.add(board[file-2][rank-1]);
                    }else {
                        if (board[file-2][rank-1].getPiece().isWhite != this.isWhite){
                            pMoves.add(board[file-2][rank-1]);
                        }
                    }
                }
            }
        return pMoves;
    }
}
