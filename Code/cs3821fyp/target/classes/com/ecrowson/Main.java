package com.ecrowson;

import com.ecrowson.Chess.Check;
import com.ecrowson.Chess.ChessGame;
import com.ecrowson.TicTacToe.Game;
import com.ecrowson.TicTacToe.GameStatus;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The main menu. Handles creation of new game instances.
 * 
 * @author Ethan Crowson
 */
public class Main extends Application {

    /**
     * Creates the menu on the Application thread and generates new instances of
     * games off of this.
     * 
     * @param Stage The primary stage the application thread is running.
     * @throws Exception Any unchecked exception.
     */
    @Override
    public void start(Stage Stage) throws Exception {
        // Creation of the main menu screen.
        StackPane b = new StackPane();
        b.setAlignment(Pos.CENTER);
        HBox h = new HBox(20);
        h.setAlignment(Pos.CENTER);
        Insets marginh = new Insets(20, 20, 20, 20);
        Insets marginv = new Insets(20, 20, 20, 20);
        Border border = new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        h.setPadding(marginh);

        // Chess Box
        VBox cBox = new VBox(40);
        cBox.setStyle(
                "-fx-background-color: tan;");
        cBox.setBorder(border);
        cBox.setPadding(marginv);
        cBox.setAlignment(Pos.BASELINE_CENTER);
        Text cText = new Text("Chess");
        cText.setStyle("-fx-font: 32 arial;" + "-fx-font-weight: bold;");
        StackPane cStack = new StackPane();
        cStack.getChildren().add(new ImageView(new Image("Icons/cIcon.png")));
        Button cButton = new Button("New Chess Instance");
        cButton.setStyle(
                "-fx-font: 16 arial;" +
                        "-fx-font-weight: bold;");
        cButton.setFocusTraversable(false);
        cButton.setPadding(new Insets(5, 5, 5, 5));
        Check check = new Check();
        HBox compBox = new HBox(5);
        compBox.setAlignment(Pos.CENTER);
        Text numComp = new Text("Number of Computer Bots");
        numComp.setTextAlignment(TextAlignment.CENTER);
        ChoiceBox<Integer> cCB = new ChoiceBox<Integer>(FXCollections.observableArrayList(0, 1, 2));
        cCB.setValue(0);
        cCB.setFocusTraversable(false);
        compBox.getChildren().addAll(numComp, cCB);
        numComp.setStyle("-fx-font: 12 arial;-fx-font-weight: bold;");

        cBox.getChildren().addAll(cText, cStack, cButton, compBox);
        cButton.setOnAction(new EventHandler<ActionEvent>() {
            // 'New Chess Instance' creates a new chess game on press.
            @Override
            public void handle(ActionEvent e) {
                Pane board = new Pane();
                board.setStyle("-fx-background-color: #222222");
                Stage stage = new Stage();
                new ChessGame(board, check, cCB.getValue());
                stage.setScene(new Scene(board, 480, 580));
                stage.setTitle("Chess");
                stage.show();
            }
        });

        // Tic-Tac-Toe Box
        VBox tttBox = new VBox(40);
        tttBox.setStyle(
                "-fx-background-color: lightblue;");
        tttBox.setBorder(border);
        tttBox.setPadding(marginv);
        tttBox.setAlignment(Pos.BASELINE_CENTER);
        Text tttText = new Text("Tic-Tac-Toe");
        tttText.setStyle("-fx-font: 32 arial;" + "-fx-font-weight: bold;");
        StackPane tttStack = new StackPane();
        tttStack.getChildren().add(new ImageView(new Image("Icons/tttIcon.png")));
        Button tttButton = new Button("New Tic-Tac-Toe Instance");
        tttButton.setStyle(
                "-fx-font: 16 arial;" +
                        "-fx-font-weight: bold;");
        tttButton.setFocusTraversable(false);
        tttButton.setPadding(new Insets(5, 5, 5, 5));
        GameStatus gamestatus = new GameStatus();
        tttBox.getChildren().addAll(tttText, tttStack, tttButton);
        tttButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // 'New Tic-Tac-Toe' creates a new tictactoe game on press.
                Pane board = new Pane();
                Stage stage = new Stage();
                new Game(board, gamestatus);
                stage.setScene(new Scene(board, 480, 580));
                stage.setTitle("Tic-Tac-Toe");
                stage.show();
            }
        });

        h.getChildren().add(cBox);
        h.getChildren().add(tttBox);
        b.getChildren().add(h);

        Stage.setScene(new Scene(b, 560, 480));
        Stage.setTitle("Game Playing Environment");
        Stage.show();
    }

    
    /** 
     * Launches the application and calls start().
     * 
     * @param args arguments 
     */
    public static void main(String[] args) {
        launch();
    }
}
