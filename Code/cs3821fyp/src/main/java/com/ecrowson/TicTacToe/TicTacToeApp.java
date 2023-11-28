package com.ecrowson.TicTacToe;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;

public class TicTacToeApp extends Application {
    private Pane board = new Pane();
    private Boolean isXTurn = true;
    private Space[][] spaces = new Space[3][3]; 
    private Boolean inPlay = true;
    private Boolean xStart = true;
    private List<WinLine> lines = new ArrayList<>();
    private Label whosTurn = new Label("To Play\nX");
    private HBox bottom = new HBox();
    private int numWinsX = 0;
    private int numWinsO = 0;
    private int numDraws = 0;
    private Label xCount = new Label("X\n"+numWinsX);
    private Label oCount = new Label("O\n"+numWinsO);
    private Label drawCount = new Label("Draw\n"+numDraws);
    
    @Override
    public void start(Stage Stage) throws Exception{
        createBoard();
        Stage.setScene(new Scene(board,480,580));
        Stage.setTitle("TicTacToe");
        Stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void createBoard(){
        for (int col=0;col<3;col++){
            for (int row=0;row<3;row++){
                Space space = new Space();
                space.setOnMouseClicked( e -> {
                    gameManager(space);
                });
                space.setTranslateX(row*160);
                space.setTranslateY(col*160);
                board.getChildren().add(space);

                spaces[col][row] = space;
            }
        }
        addLines();
        board.getChildren().add(bottom);
        bottom.getChildren().addAll(whosTurn, xCount, drawCount, oCount);
        bottom.setStyle("-fx-font: 32 arial;");
        bottom.setLayoutY(480);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setSpacing(30);
        HBox.setMargin(whosTurn, new Insets(10,100,0,15));
        whosTurn.setTextAlignment(TextAlignment.CENTER);
        xCount.setTextAlignment(TextAlignment.CENTER);
        drawCount.setTextAlignment(TextAlignment.CENTER);
        oCount.setTextAlignment(TextAlignment.CENTER);

        
    }
    public Boolean checkWin(){
        for (WinLine line : lines){
            if (line.isComplete()){
                inPlay = false;
                winScreen(line);
                return true;
            }
        }
        return false;
    }
    private void gameManager(Space space){
        if (!space.isOccupied()){
            if (inPlay){
                space.setValue(isXTurn);
                    if (checkWin()){
                        return;
                    }
                    isDraw();
                    isXTurn = !isXTurn;
                    if (isXTurn){
                        whosTurn.setText("To Play\nX");
                    }
                    else{
                        System.out.println("a");
                        whosTurn.setText("To Play\nO");
                    }
             }
        }
    }

    private void addLines(){
        //horizontals
        for (int y=0; y<3; y++){
            lines.add(new WinLine(spaces[0][y], spaces[1][y], spaces[2][y]));
        }
        //verticals
        for (int x=0; x<3; x++){
            lines.add(new WinLine(spaces[x][0], spaces[x][1], spaces[x][2]));
        }
        //diagonals
        lines.add(new WinLine(spaces[0][0], spaces[1][1], spaces[2][2]));
        lines.add(new WinLine(spaces[2][0], spaces[1][1], spaces[0][2]));
    }
    private void winScreen(WinLine line){
        double lineStartX = line.getSpace(0).getCenterX();
        double lineStartY = line.getSpace(0).getCenterY();
        double lineEndX = line.getSpace(2).getCenterX();
        double lineEndY = line.getSpace(2).getCenterY();
    
        if (line.getSpace(0).getValue().equals("X")){
            numWinsX ++;
            whosTurn.setText("Result\nX Wins");
            System.out.println(whosTurn.getText());
            xCount.setText("X\n"+numWinsX);
        }
        else{
            numWinsO ++;
            whosTurn.setText("Result\nO Wins");
            oCount.setText("O\n"+numWinsO);
        }

        Line winningLine = new Line(lineStartX,lineStartY,lineStartX,lineStartY);
        board.getChildren().add(winningLine);
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
            new KeyValue(winningLine.endXProperty(), lineEndX), 
            new KeyValue(winningLine.endYProperty(), lineEndY)));
        timeline.play();

        timeline.setOnFinished(e -> {
            System.out.println(whosTurn.getText());
            board.getChildren().remove(winningLine);
            resetBoard();
        });
    }

    private void drawScreen(){
        numDraws ++;
        drawCount.setText("Draw\n"+numDraws);
        whosTurn.setText("Result\nDraw");
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2), 
            new KeyValue(whosTurn.textProperty(), "Result\nDraw")));
        timeline.play();

        timeline.setOnFinished(e -> {
            resetBoard();
        });
    }

    private Boolean isDraw(){
        if (inPlay){
            System.out.println("b");
            for (Space[] spaceRow : spaces){
                for (Space space : spaceRow){
                    if (!space.isOccupied()){
                        return false;
                    }
                }
            }
            inPlay = false;
            drawScreen();
            return true;
        }
        return false;
    }

    private void resetBoard(){
        inPlay = true;
        xStart = !xStart;
        if (xStart){
            whosTurn.setText("To Play\nX");
            isXTurn = true;
        }
        else{
            whosTurn.setText("To Play\nO");
            isXTurn = false;
        }
        for (int i=0;i<3;i++){
            for (int j=0;j<3;j++){
                spaces[j][i].resetValue();
            }
        }
    }
}
