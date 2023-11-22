package com.ecrowson.TicTacToe;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class TicTacToeApp extends Application {
    private Pane board = new Pane();
    private Boolean isXTurn = true;
    private Space[][] spaces = new Space[3][3]; 
    @Override
    public void start(Stage Stage) throws Exception{
        for (int col=0;col<3;col++){
            for (int row=0;row<3;row++){
                Space space = new Space();
                space.setOnMouseClicked( e -> {
                    space.setValue(isXTurn);
                    isXTurn = !isXTurn;
                });
                space.setTranslateX(row*160);
                space.setTranslateY(col*160);
                board.getChildren().add(space);

                spaces[row][col] = space;
            }
        }
        Stage.setScene(new Scene(board,480,480));
        Stage.setTitle("TicTacToe");
        Stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
