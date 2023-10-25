package com.ecrowson;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import java.io.IOException;
import java.util.LinkedList;


public class Main extends Application {
    GridPane board = new GridPane();

    @Override
    public void start(Stage stage) throws IOException {
  
        for (int file=0; file<8; file++){
            for (int rank=0; rank<8; rank++){
                Rectangle tile = new Rectangle();
                boolean isWhite = (file+rank)%2 == 0;
                if (isWhite) tile.setFill(Color.rgb(238,238,210));
                else tile.setFill(Color.rgb(118,150,86));
                board.add(tile, file, rank);
                tile.widthProperty().bind(board.widthProperty().divide(8));
                tile.heightProperty().bind(board.heightProperty().divide(8));
            }
        }
        Scene scene = new Scene(board, 480, 480);
        stage.setScene(scene);
        stage.setTitle("Chess");
        
        LinkedList<Piece> ps = new LinkedList<>();
        Piece brook=new Piece(board,0, 0, false, "rook", ps);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

