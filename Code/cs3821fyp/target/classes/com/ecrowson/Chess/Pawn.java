package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Pawn extends Piece {
    public Pawn(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'P';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        return null;
    }
}
