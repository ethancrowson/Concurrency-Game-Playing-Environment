package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Bishop extends Piece {
    public Bishop(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'B';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        ArrayList<Tile> pMoves = new ArrayList<>();
        int temprank;
        int tempfile;
        //DIAG UP LEFT
        if (file != 0 || rank != 0){
            temprank = rank-1;
            for (int f = file-1; f >= 0; f--){
                if (temprank == -1 || f == -1){ break;}
                else if (board[f][temprank].isOccupied()){
                    if (board[f][temprank].getPiece().isWhite != isWhite){
                        pMoves.add(board[f][temprank]);
                    }
                    break;
                }
                pMoves.add(board[f][temprank]);
                temprank --;
            }
        }
        //DIAGONAL DOWN RIGHT
        if (file != 7 || rank != 7){
            temprank = rank+1;
            for (int f = file+1; f < 8; f++){
                if (temprank == 8 || f == 8){ break;}
                else if (board[f][temprank].isOccupied()){
                    if (board[f][temprank].getPiece().isWhite != isWhite){
                        pMoves.add(board[f][temprank]);
                    }
                    break;
                }
                pMoves.add(board[f][temprank]);
                temprank ++;
            }
        }
        //DIAGONAL DOWN LEFT
        if (file != 7 || rank != 0){
            tempfile = file+1; 
            for (int r = rank-1; r >= 0; r--){
                if (tempfile == 8 || r == -1){ break;}
                else if (board[tempfile][r].isOccupied()){
                    if (board[tempfile][r].getPiece().isWhite != isWhite){
                        pMoves.add(board[tempfile][r]);
                    }
                    break;
                }
                pMoves.add(board[tempfile][r]);
                tempfile ++;
            }
        }
        //DIAGONAL UP RIGHT
        if (file != 0 || rank != 7){
            tempfile = file-1;
            for (int r = rank+1; r < 8; r++){
                if (tempfile == -1 || r == 8){ break;} 
                else if (board[tempfile][r].isOccupied()){
                    if (board[tempfile][r].getPiece().isWhite != isWhite){
                        pMoves.add(board[tempfile][r]);
                    }
                    break;
                }
                pMoves.add(board[tempfile][r]);
                tempfile --;
            }
        }
        return pMoves;
    }
}
