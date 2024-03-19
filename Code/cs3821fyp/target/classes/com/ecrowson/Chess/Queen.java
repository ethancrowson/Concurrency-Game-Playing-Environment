package com.ecrowson.Chess;

import java.util.LinkedList;

public  class Queen extends Piece{
    public Queen(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'Q';}
}
