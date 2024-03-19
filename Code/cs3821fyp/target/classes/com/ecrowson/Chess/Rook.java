package com.ecrowson.Chess;

import java.util.LinkedList;

public class Rook extends Piece {
    public Rook(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'R';}
}
