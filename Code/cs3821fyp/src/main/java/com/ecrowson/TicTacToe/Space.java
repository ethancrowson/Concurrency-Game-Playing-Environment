package com.ecrowson.TicTacToe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Space extends StackPane{
    private Text text = new Text();
    
    public Space() {
        Rectangle square = new Rectangle(160,160);
        square.setFill(null);
        square.setStroke(Color.BLACK);
        text.setFont(Font.font(80));

        getChildren().addAll(square, text);
    }
    
    public Boolean setValue(Boolean xturn){
        if (!isOccupied()){
            if (xturn){
                text.setText("X");
            }
            else{
                text.setText("O");
            }
        }
        return xturn;
    }

    public void resetValue(){
        text.setText("");
    }

    public String getValue(){
        return text.getText();
    } 
    
    public double getCenterX(){
        return getTranslateX();
    }

    public double getCenterY(){
        return getTranslateY();
    }

    private Boolean isOccupied(){
        if (text.getText().isEmpty()){
            return false;
        }
        return true;
    }
}
