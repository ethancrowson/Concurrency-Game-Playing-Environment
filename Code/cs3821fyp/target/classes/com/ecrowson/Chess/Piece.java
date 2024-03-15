package com.ecrowson.Chess;

import java.util.LinkedList;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Piece extends ImageView {
    int xp;
    int yp;
    char colour;
    char type;
    boolean isWhite;
    LinkedList<Piece> ps;
    Pane board;

    public Piece(Pane board, Tile tile, char colour, char type, LinkedList<Piece> ps) {
        this.colour = colour;
        this.ps = ps;
        this.type = type;
        this.board = board;
        if (colour == 'W'){
            this.isWhite = true;
        }else {
            this.isWhite = false;
        }
        
        ps.add(this);
        tile.setPiece(this);
    }

    public void kill() {
        ps.remove(this);
    }
}

