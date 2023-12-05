package com.ecrowson.TicTacToe;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
/**
 * The application that runs the multiple instances of tic-tac-toe.
 * 
 * @author Ethan Crowson
 */
public class TicTacToeApp extends Application {

    /**
     * Starts the Application. Initializes a GameStatus object,2 Game objects and their respective
     * Stages to display them.
     * 
     * @param Stage The primary stage created with the application.
     * @throws Exception Thrown when a thread is interrupted.
     */
    @Override
    public void start(Stage Stage) throws Exception {
        GameStatus gamestatus = new GameStatus();
        // Game 1
        Pane board1 = new Pane();
        new Game(board1, gamestatus);
        Stage.setScene(new Scene(board1, 480, 580));
        Stage.setTitle("TicTacToe");
        Stage.show();

        // Game 2
        Stage stage = new Stage();
        Pane board2 = new Pane();
        new Game(board2, gamestatus);
        stage.setScene(new Scene(board2, 480, 580));
        stage.setTitle("TicTacToe");
        stage.show();

    }
    /** Launches the application */
    public static void main(String[] args) {
        launch();
    }


}
