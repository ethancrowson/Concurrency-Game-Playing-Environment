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
    /** Constructs a new Tile instance. */
    public Tile(Boolean isLight) {
        this.isLight = isLight;
        this.piece = null;
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
        Image i = new Image("Pieces/" + piece.colour + "" + piece.type + ".png");
        ImageView p = new ImageView(i);
        this.getChildren().add(p);
        /*p.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(piece.colour + "" + piece.type);
            Main.pieceClicked(piece);
            event.consume();
        });*/
    }
    public void selected(){
        if (isLight){
            square.setFill(Color.rgb(244, 246, 128));
        } else {
            square.setFill(Color.rgb(185, 202, 67));
        }
    }
    
}
