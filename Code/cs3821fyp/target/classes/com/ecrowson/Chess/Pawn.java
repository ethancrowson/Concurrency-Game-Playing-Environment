package com.ecrowson.Chess;

import java.util.LinkedList;

public class Pawn extends Piece {
    public Pawn(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'P';}
}
