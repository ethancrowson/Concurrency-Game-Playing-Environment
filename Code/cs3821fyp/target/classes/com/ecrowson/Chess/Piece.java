package com.ecrowson.Chess;

import java.util.LinkedList;
import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
    char colour;
    boolean isWhite;
    LinkedList<Piece> ps;

    public Piece(char colour, LinkedList<Piece> ps) {
        this.colour = colour;
        this.ps = ps;
        if (colour == 'W'){
            this.isWhite = true;
        }else {
            this.isWhite = false;
        }
        
        ps.add(this);
    }

    public void kill() {
        ps.remove(this);
    }
    public abstract char getType();
}

