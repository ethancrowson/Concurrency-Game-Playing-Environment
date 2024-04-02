package com.ecrowson.Chess;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane{
    private Piece piece;
    private Rectangle square;
    private boolean isLight;
    private ImageView p;
    private ImageView hl;
    private int x;
    private int y;
    private boolean isHighlighted;
    /** Constructs a new Tile instance. */
    public Tile(Boolean isLight, int x, int y) {
        this.isLight = isLight;
        this.x = x;
        this.y = y;
        this.piece = null;
        this.isHighlighted = false;
        Rectangle square = new Rectangle(60, 60);
        this.square = square;
        if (isLight){square.setFill(Color.rgb(238, 238, 210));}
        else{square.setFill(Color.rgb(118, 150, 86));}
        square.setStroke(Color.BLACK);

        getChildren().add(square);
    }
    public boolean isOccupied(){
        return (this.piece != null);
    }
    public Piece getPiece(){
        return this.piece;
    }
    public void setPiece(Piece piece){
        this.piece = piece;
        Image i = new Image("Pieces/" + piece.colour + "" + piece.getType() + ".png");
        p = new ImageView(i);
        this.getChildren().add(p);
        /*p.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(piece.colour + "" + piece.type);
            Main.pieceClicked(piece);
            event.consume();
        });*/
    }
    public void removePiece(){
        if (piece != null){
            piece = null;
            this.getChildren().remove(p);
        }
    }
    public void selected(){
        if (isLight){
            square.setFill(Color.rgb(244, 246, 128));
        } else {
            square.setFill(Color.rgb(185, 202, 67));
        }
        System.out.println("selected tile");
    }
    public void deselect(){
        if (isLight){
            square.setFill(Color.rgb(238, 238, 210));
        } else {
            square.setFill(Color.rgb(118, 150, 86));
        }
    }
    public void setHighlight(String highlight){
        Image i = new Image("Effects/highlight" + highlight + ".png");
        hl = new ImageView(i);
        isHighlighted = true;
        this.getChildren().add(hl);
        isHighlighted = true;
        
    }
    public void removeHighlight(){
        if (hl != null){
            this.getChildren().remove(hl);
            hl = null;
            isHighlighted = false;
        }
    }
    public boolean occupiedTakeable(Piece oPiece){
        if (!isOccupied() || (isOccupied() && oPiece.canTake(this.getPiece()))){
            return true;
        }
        return false;
    }
    public void inCheck(){
        square.setFill(Color.rgb(209, 37, 23));
    }
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public String toString(){return String.valueOf(getX())+String.valueOf(getY());}
    public boolean getHighlight(){return this.isHighlighted;}
}
