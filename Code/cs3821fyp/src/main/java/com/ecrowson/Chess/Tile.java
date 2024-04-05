package com.ecrowson.Chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * A tile on the chess board. Allows for pieces to be set on a tile and for the
 * board to have visual changes.
 * 
 * @author Ethan Crowson
 */
public class Tile extends StackPane {
    private Piece piece;
    private Rectangle square;
    private boolean isLight;
    private ImageView p;
    private ImageView hl;
    private int x;
    private int y;
    private boolean isHighlighted;

    /**
     * Constructs a new Tile.
     * 
     * @param isLight a boolean specifying if the tile is light or dark as seen on a
     *                chess board.
     * @param x       the x value of the tile (The file in chess terminology).
     * @param y       the y value of the tile (The rank in chess terminilogy).
     */
    public Tile(Boolean isLight, int x, int y) {
        this.isLight = isLight;
        this.x = x;
        this.y = y;
        this.piece = null;
        this.isHighlighted = false;
        Rectangle square = new Rectangle(60, 60);
        this.square = square;
        if (isLight) {
            square.setFill(Color.rgb(238, 238, 210));
        } else {
            square.setFill(Color.rgb(118, 150, 86));
        }
        square.setStroke(Color.BLACK);

        getChildren().add(square);
    }

    /**
     * Checks if the current tile withholds a piece.
     * 
     * @return if the piece value of the tile is null.
     */
    public boolean isOccupied() {
        return (this.piece != null);
    }

    /**
     * Getter method for the piece in the tile.
     * 
     * @return the piece stored in this tile.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Setter method for the piece in the tile. Adds the corresponding image of the
     * piece to the board.
     * 
     * @param piece the piece to be set to the tile.
     */
    public void setPiece(Piece piece) {
        if (piece != null){
            this.piece = piece;
            Image i = new Image("Pieces/" + piece.colour + "" + piece.getType() + ".png");
            p = new ImageView(i);
            this.getChildren().add(p);
        }
    }
    /**
     * Remove the piece from the tile and sets the piece value to null.
     */
    public void removePiece() {
        if (piece != null) {
            piece = null;
            this.getChildren().remove(p);
        }
    }

    /**
     * Colours the tile to indicate that it has been selected. Different colours if
     * the tile is light or dark.
     */
    public void selected() {
        if (isLight) {
            square.setFill(Color.rgb(244, 246, 128));
        } else {
            square.setFill(Color.rgb(185, 202, 67));
        }
    }

    /**
     * Restores the tile's default colours after being unselected.
     */
    public void deselect() {
        if (isLight) {
            square.setFill(Color.rgb(238, 238, 210));
        } else {
            square.setFill(Color.rgb(118, 150, 86));
        }
    }

    /**
     * Sets the highlights of the tile. Including dots for possible movement and
     * outlines for possible takes.
     * 
     * @param highlight the string corresponding to the chosen highlight.
     */
    public void setHighlight(String highlight) {
        Image i = new Image("Effects/highlight" + highlight + ".png");
        hl = new ImageView(i);
        this.getChildren().add(hl);
        isHighlighted = true;

    }

    /**
     * Removes the highlights from the tile.
     */
    public void removeHighlight() {
        this.getChildren().remove(hl);
        isHighlighted = false;
    }

    /**
     * Checks if the tile is occupied and takeable given another piece.
     * 
     * @param oPiece the piece attacking this tile.
     * @return if there is a piece on this tile that is takeable.
     */
    public boolean occupiedTakeable(Piece oPiece) {
        if (!isOccupied() || (isOccupied() && oPiece.canTake(this.getPiece()))) {
            return true;
        }
        return false;
    }

    /**
     * Sets the colour of the tile if the king is in check.
     */
    public void inCheck() {
        square.setFill(Color.rgb(209, 37, 23));
    }

    /**
     * Getter method for the x value of the tile.
     * 
     * @return value of x.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter method for the y value of the tile.
     * 
     * @return value of y.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Returns a string representation of the tile in the form of the x and y values
     * of the tile.
     * 
     * @return string representation of the tile.
     */
    public String toString() {
        return String.valueOf(getX()) + String.valueOf(getY());
    }

    /**
     * Getter method of the boolean storing if the tile is highlighted.
     * 
     * @return if the tile is currently highlighted.
     */
    public boolean getHighlight() {
        return this.isHighlighted;
    }
}
