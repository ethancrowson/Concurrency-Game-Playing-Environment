package com.ecrowson.Chess;

import java.util.LinkedList;

public class Knight extends Piece {
    public Knight(char colour, LinkedList<Piece> ps){
        super(colour,ps);
    }
    public char getType(){return 'N';}
}
