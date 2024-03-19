package com.ecrowson.Chess;

import java.util.LinkedList;

public class King extends Piece {
    public King(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'K';}
}
