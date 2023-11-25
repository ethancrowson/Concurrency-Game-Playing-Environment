package com.ecrowson.TicTacToe;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class TicTacToeApp extends Application {
    private Pane board = new Pane();
    private Boolean isXTurn = true;
    private Space[][] spaces = new Space[3][3]; 
    private Boolean inPlay = true;
    private List<WinLine> lines = new ArrayList<>();
    @Override
    public void start(Stage Stage) throws Exception{
        for (int col=0;col<3;col++){
            for (int row=0;row<3;row++){
                Space space = new Space();
                space.setOnMouseClicked( e -> {
                    if (inPlay){
                        space.setValue(isXTurn);
                        checkWin();
                        isXTurn = !isXTurn;
                    }
                });
                space.setTranslateX(row*160);
                space.setTranslateY(col*160);
                board.getChildren().add(space);

                spaces[row][col] = space;
            }
        }
        addLines();
        
        Stage.setScene(new Scene(board,480,480));
        Stage.setTitle("TicTacToe");
        Stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public Boolean checkWin(){
        for (WinLine line : lines){
            if (line.isComplete()){
                inPlay = false;
                winScreen(line);
            }
        }
        return false;
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
        Space lineStart = line.getSpace(0);
        Space lineEnd = line.getSpace(2);
        Line winningLine = new Line(lineStart.getCenterX(),lineStart.getCenterY(),lineEnd.getCenterX(),lineEnd.getCenterY());
        
        board.getChildren().add(winningLine);
    }
}
