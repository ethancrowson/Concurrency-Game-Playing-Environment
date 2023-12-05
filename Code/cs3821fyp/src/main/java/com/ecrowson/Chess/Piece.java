package com.ecrowson.Chess;

import java.util.LinkedList;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

public class Piece extends ImageView {
    int xp;
    int yp;
    boolean isWhite;
    char type;
    LinkedList<Piece> ps;
    GridPane board;

    public Piece(GridPane board, int xp, int yp, boolean isWhite, char type, LinkedList<Piece> ps) {
        this.xp = xp;
        this.yp = yp;
        this.isWhite = isWhite;
        this.ps = ps;
        this.type = type;
        this.board = board;
        ps.add(this);
        setImage(board);
    }

    public void setImage(GridPane board) {
        char colour;
        if (this.isWhite) {
            colour = 'W';
        } else {
            colour = 'B';
        }
        Image i = new Image("Pieces/" + colour + "" + this.type + ".png");
        ImageView p = new ImageView(i);
        board.add(p, xp, yp);
        p.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(colour + "" + this.type);
            Main.pieceClicked(this);
            event.consume();
        });
    }

    public void move(int xp, int yp) {

    }

    public void kill() {
        ps.remove(this);
    }
}

