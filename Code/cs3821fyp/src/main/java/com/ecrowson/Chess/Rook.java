package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Rook extends Piece {
    public Rook(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'R';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        ArrayList<Tile> pMoves = new ArrayList<>();
        //UP
        for (int f = file-1; f >= 0; f--){
            if (board[f][rank].isOccupied()){
                if (board[f][rank].getPiece().isWhite != isWhite){
                    pMoves.add(board[f][rank]);
                }
                break;
            }
            pMoves.add(board[f][rank]);
        }
        //DOWN
        for (int f = file+1; f < 8; f++){
            if (board[f][rank].isOccupied()){
                if (board[f][rank].getPiece().isWhite != isWhite){
                    pMoves.add(board[f][rank]);
                }
                break;
            }
            pMoves.add(board[f][rank]);
        }
        //LEFT
        for (int r = rank-1; r >= 0; r--){
            if (board[file][r].isOccupied()){
                if (board[file][r].getPiece().isWhite != isWhite){
                    pMoves.add(board[file][r]);
                }
                break;
            }
            pMoves.add(board[file][r]);
        }
        //RIGHT
        for (int r = rank+1; r < 8; r++){
            if (board[file][r].isOccupied()){
                if (board[file][r].getPiece().isWhite != isWhite){
                    pMoves.add(board[file][r]);
                }
                break;
            }
            pMoves.add(board[file][r]);
        }
            
        return pMoves;
    }
}
