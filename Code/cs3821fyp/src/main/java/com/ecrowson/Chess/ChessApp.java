package com.ecrowson.Chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChessApp extends Application{
    @Override
    public void start(Stage Stage) throws Exception {
        Check check = new Check();
        // Game 1
        Pane board1 = new Pane();
        new ChessGame(board1, check);
        Stage.setScene(new Scene(board1, 480, 480));
        Stage.setTitle("Chess");
        Stage.show();

        // Game 2
        Stage Stage2 = new Stage();
        Pane board2 = new Pane();
        new ChessGame(board2, check);
        Stage2.setScene(new Scene(board2, 480, 480));
        Stage2.setTitle("Chess2");
        Stage2.show();
    }

    /** Launches the application */
    public static void main(String[] args) {
        launch();
    }
}
