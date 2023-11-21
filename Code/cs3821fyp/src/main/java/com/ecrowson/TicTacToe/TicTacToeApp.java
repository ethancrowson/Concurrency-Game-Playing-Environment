package com.ecrowson.TicTacToe;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class TicTacToeApp extends Application {

    @Override
    public void start(Stage Stage) throws Exception{
        Stage.setScene(new Scene(new GridPane(),480,480));

        for (int col=0;col<3;col++){
            for (int row=0;row<3;row++){
                Space space = new Space();
            }
        }
        Stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}
