package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Bishop extends Piece {
    public Bishop(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'B';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        return null;
    }
}
