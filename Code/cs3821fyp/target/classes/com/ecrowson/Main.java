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
        Piece BR=new Piece(board,0, 0, false, 'R', ps);
        Piece BN=new Piece(board,1, 0, false, 'N', ps);
        Piece BB=new Piece(board,2, 0, false, 'B', ps);
        Piece BQ=new Piece(board,3, 0, false, 'Q', ps);
        Piece BK=new Piece(board,4, 0, false, 'K', ps);
        Piece BB2=new Piece(board,5, 0, false, 'B', ps);
        Piece BN2=new Piece(board,6, 0, false, 'N', ps);
        Piece BR2=new Piece(board,7, 0, false, 'R', ps);
        Piece BP1=new Piece(board,0, 1, false, 'P', ps);
        Piece BP2=new Piece(board,1, 1, false, 'P', ps);
        Piece BP3=new Piece(board,2, 1, false, 'P', ps);
        Piece BP4=new Piece(board,3, 1, false, 'P', ps);
        Piece BP5=new Piece(board,4, 1, false, 'P', ps);
        Piece BP6=new Piece(board,5, 1, false, 'P', ps);
        Piece BP7=new Piece(board,6, 1, false, 'P', ps);
        Piece BP8=new Piece(board,7, 1, false, 'P', ps);

        Piece WR=new Piece(board,0, 7, true, 'R', ps);
        Piece WN=new Piece(board,1, 7, true, 'N', ps);
        Piece WB=new Piece(board,2, 7, true, 'B', ps);
        Piece WQ=new Piece(board,3, 7, true, 'Q', ps);
        Piece WK=new Piece(board,4, 7, true, 'K', ps);
        Piece WB2=new Piece(board,5, 7, true, 'B', ps);
        Piece WN2=new Piece(board,6, 7, true, 'N', ps);
        Piece WR2=new Piece(board,7, 7, true, 'R', ps);
        Piece WP1=new Piece(board,0, 6, true, 'P', ps);
        Piece WP2=new Piece(board,1, 6, true, 'P', ps);
        Piece WP3=new Piece(board,2, 6, true, 'P', ps);
        Piece WP4=new Piece(board,3, 6, true, 'P', ps);
        Piece WP5=new Piece(board,4, 6, true, 'P', ps);
        Piece WP6=new Piece(board,5, 6, true, 'P', ps);
        Piece WP7=new Piece(board,6, 6, true, 'P', ps);
        Piece WP8=new Piece(board,7, 6, true, 'P', ps);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

