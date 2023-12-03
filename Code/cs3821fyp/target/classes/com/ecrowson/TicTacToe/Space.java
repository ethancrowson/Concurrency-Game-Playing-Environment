package com.ecrowson.TicTacToe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Space extends StackPane {
    // The actual tiles of the game board.
    private Text text = new Text();

    public Space() {
        Rectangle square = new Rectangle(160, 160);
        square.setFill(null);
        square.setStroke(Color.BLACK);
        text.setFont(Font.font(80));

        getChildren().addAll(square, text);
    }


    /**
     * Sets the text value of the square to either X or O depending on whose turn it is.
     * 
     * @param xturn Whose turn it is to mark a space. (X/O)
     */
    public void setValue(Boolean xturn) {
        if (!isOccupied()) { // ensure it cannot be overwritten.
            if (xturn) {
                text.setText("X");
            } else {
                text.setText("O");
            }
        }
    }

    public void resetValue() {
        // Resets the text value of the space to blank.
        text.setText("");
    }

    public String getValue() {
        // Returns the text value of the space.
        return text.getText();
    }

    public double getCenterX() {
        // Returns the X co-ord of the center of the space.
        return getTranslateX() + 80;
    }

    public double getCenterY() {
        // Returns the Y co-ord center of the space.
        return getTranslateY() + 80;
    }

    /** Returns if the space has already been assigned a value. */
    public Boolean isOccupied() {
        if (text.getText().isEmpty()) {
            return false;
        }
        return true;
    }
}
