package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public  class Queen extends Piece{
    public Queen(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'Q';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        return null;
    }
}
