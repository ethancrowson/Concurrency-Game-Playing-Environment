package com.ecrowson.Chess;

import java.util.ArrayList;
import java.util.LinkedList;

import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
    char colour;
    boolean isWhite;
    LinkedList<Piece> ps;
    boolean hasMoved;

    public Piece(char colour, LinkedList<Piece> ps) {
        this.colour = colour;
        this.ps = ps;
        this.hasMoved = false;
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
    public boolean canTake(Piece oPiece){
        if (oPiece.isWhite != this.isWhite){
            return true;
        }
        return false;
    }
    public boolean getHasMoved(){return this.hasMoved;}
    public void setHasMoved(){this.hasMoved = true;}
    public abstract char getType();
    public abstract ArrayList<Tile> getMoves(Tile[][] board, int file, int rank);
}

