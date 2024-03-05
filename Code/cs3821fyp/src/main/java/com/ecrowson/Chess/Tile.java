package com.ecrowson.Chess;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane{
    /** Constructs a new Space instance. */
    public Tile(Boolean isLight) {
        Rectangle square = new Rectangle(60, 60);
        if (isLight){square.setFill(Color.rgb(238, 238, 210));}
        else{square.setFill(Color.rgb(118, 150, 86));}
        square.setStroke(Color.BLACK);

        getChildren().add(square);
    }
    
}
