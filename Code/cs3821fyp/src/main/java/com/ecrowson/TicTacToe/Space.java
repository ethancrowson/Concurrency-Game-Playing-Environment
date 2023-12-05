package com.ecrowson.TicTacToe;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * A space on the tic-tac-toe game board.
 * 
 * @author Ethan Crowson
 */
public class Space extends StackPane {
    private Text text = new Text();

    /** Constructs a new Space instance. */
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

    /** Resets the value of the Space back to blank. */
    public void resetValue() {
        text.setText("");
    }

    /** Returns the text value of the space. */
    public String getValue() {
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
