package com.ecrowson.TicTacToe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Space extends StackPane{
    private Text text = new Text();
    
    public Space() {
        Rectangle square = new Rectangle(160,160);
        square.setFill(null);
        square.setStroke(Color.BLACK);
        getChildren().addAll(square, text);
    }
    
    public void setValue(Boolean xturn){
        if (!isOccupied()){
            if (xturn){
                text.setText("X");
            }
            else{
                text.setText("O");
            }
        }
    }

    private Boolean isOccupied(){
        if (text.getText().isEmpty()){
            return false;
        }
        return true;
    }
}
