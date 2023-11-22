package com.ecrowson.TicTacToe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Space extends StackPane{
    
    public Space() {
        Rectangle square = new Rectangle(160,160);
        square.setFill(null);
        square.setStroke(Color.BLACK);
        getChildren().addAll(square);
    }
    
}
