package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

public class Knight extends Piece {
    public Knight(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'N';}
    public ArrayList<Tile> getMoves(Tile[][] board, int file, int rank){
        return null;
    }
}
