package com.ecrowson.TicTacToe;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
/**
 * The instance of the tic-tac-toe game. Handles the game playing.
 * 
 * @author Ethan Crowson
 */
public class Game {
    private Pane board = new Pane();
    private GameStatus gamestatus = new GameStatus();
    private Boolean isXTurn = true; // Tracks whose turn it is in the game.
    private Space[][] spaces = new Space[3][3]; // Array of Spaces (the TicTacToe board).
    private WinLine statusLine;
    private Boolean inPlay = true; // Toggles depending on if the game is in a playable state or
                                   // not.
    private Boolean xStart = true; // Keeps track of who started the last game (this swaps after
                                   // each game to ensure fair play).
    private List<WinLine> lines = new ArrayList<>(); // Array which will fill up with all possible
                                                     // winning lines.
    private Label whosTurn = new Label("To Play\nX");
    private HBox bottom = new HBox();
    private int numWinsX = 0; // Keeps track of # of times X has won.
    private int numWinsO = 0; // Keeps track of # of times O has won.
    private int numDraws = 0; // Keeps track of # of times there has been a tie.
    private Label xCount = new Label("X\n" + numWinsX);
    private Label oCount = new Label("O\n" + numWinsO);
    private Label drawCount = new Label("Draw\n" + numDraws);
    /**
     * Creates a new Game.
     * 
     * @param board The pane the game GUI will be created and played on.
     * @param gamestatus The shared object between game instances that checks the status of a game.
     */
    public Game(Pane board, GameStatus gamestatus) {
        this.gamestatus = gamestatus;
        this.board = board;

        createBoard();
        statusLine = new WinLine(spaces[0][0], spaces[1][1], spaces[2][2]);
    }

    /**
     * Creates the board the game will be played upon and any objects needed before game playing can
     * commence.
     */
    private void createBoard() {
        // Initial setup of game board. Filling out the game board with spaces (3x3).
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                Space space = new Space();
                space.setOnMouseClicked(e -> { // Handles when each tile of the board is clicked on.
                    try {
                        gameManager(space);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                });
                space.setTranslateX(row * 160);
                space.setTranslateY(col * 160);
                board.getChildren().add(space);

                spaces[col][row] = space;
            }
        }
        addLines();

        // Formatting the layout of the game board.
        board.getChildren().add(bottom);
        bottom.getChildren().addAll(whosTurn, xCount, drawCount, oCount);
        bottom.setStyle("-fx-font: 32 arial;");
        bottom.setLayoutY(480);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setSpacing(30);
        HBox.setMargin(whosTurn, new Insets(10, 100, 0, 15));
        whosTurn.setTextAlignment(TextAlignment.CENTER);
        xCount.setTextAlignment(TextAlignment.CENTER);
        drawCount.setTextAlignment(TextAlignment.CENTER);
        oCount.setTextAlignment(TextAlignment.CENTER);
    }

    /**
     * Handles what happens when a Space object on the board is clicked (Manages the turn by turn
     * workings of the game).
     * 
     * @param space The space the user has clicked on with the mouse.
     * @throws InterruptedException Thrown when the thread is interrupted, either before or during
     *         the task.
     */
    private void gameManager(Space space) throws InterruptedException {
        if (!space.isOccupied()) {
            if (inPlay) {
                space.setValue(isXTurn);
                Task<WinLine> task = new Task<WinLine>() {

                    @Override
                    protected WinLine call() throws Exception {
                        return gamestatus.checkStatus(lines, spaces);
                    }
                };
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

                    @Override
                    public void handle(WorkerStateEvent event) {
                        statusLine = task.getValue();
                        if (statusLine != null) {
                            if (statusLine.isComplete()) {
                                inPlay = false;
                                winScreen(statusLine);
                                return;
                            }
                            inPlay = false;
                            drawScreen();
                            return;
                        }
                        isXTurn = !isXTurn;
                        if (isXTurn) {
                            whosTurn.setText("To Play\nX");
                        } else {
                            whosTurn.setText("To Play\nO");
                        }
                    }

                });
                Thread th = new Thread(task);
                th.setDaemon(true);
                th.start();
            }
        }
    }

    /** Adds all possible winning line combinations to the lines array */
    private void addLines() {
        // horizontals
        for (int y = 0; y < 3; y++) {
            lines.add(new WinLine(spaces[0][y], spaces[1][y], spaces[2][y]));
        }
        // verticals
        for (int x = 0; x < 3; x++) {
            lines.add(new WinLine(spaces[x][0], spaces[x][1], spaces[x][2]));
        }
        // diagonals
        lines.add(new WinLine(spaces[0][0], spaces[1][1], spaces[2][2]));
        lines.add(new WinLine(spaces[2][0], spaces[1][1], spaces[0][2]));
    }

    /**
     * Handles the screen that is displayed when a player wins a game.
     * 
     * @param line The line that won the game.
     */
    private void winScreen(WinLine line) {
        double lineStartX = line.getSpace(0).getCenterX();
        double lineStartY = line.getSpace(0).getCenterY();
        double lineEndX = line.getSpace(2).getCenterX();
        double lineEndY = line.getSpace(2).getCenterY();

        if (line.getSpace(0).getValue().equals("X")) {
            numWinsX++;
            whosTurn.setText("Result\nX Wins");
            xCount.setText("X\n" + numWinsX);
        } else {
            numWinsO++;
            whosTurn.setText("Result\nO Wins");
            oCount.setText("O\n" + numWinsO);
        }

        Line winningLine = new Line(lineStartX, lineStartY, lineStartX, lineStartY);
        board.getChildren().add(winningLine);
        // Timeline animates the winning line being draw on screen.
        Timeline timeline = new Timeline();
        timeline.getKeyFrames()
                .add(new KeyFrame(Duration.seconds(2),
                        new KeyValue(winningLine.endXProperty(), lineEndX),
                        new KeyValue(winningLine.endYProperty(), lineEndY)));
        timeline.play();

        timeline.setOnFinished(e -> {
            board.getChildren().remove(winningLine);
            resetBoard();
        });
    }

    /** Handles the screen that is displayed when the result of the game is a tie. */
    private void drawScreen() {
        numDraws++;
        drawCount.setText("Draw\n" + numDraws);
        whosTurn.setText("Result\nDraw");

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(2),
                new KeyValue(whosTurn.textProperty(), "Result\nDraw")));
        timeline.play();

        timeline.setOnFinished(e -> {
            resetBoard();
        });
    }

    /**
     * Resets all the values of the spaces on the board without needing them to be initiated again.
     * Swaps whose turn it is to start.
     */
    private void resetBoard() {
        inPlay = true;
        xStart = !xStart;
        if (xStart) {
            whosTurn.setText("To Play\nX");
            isXTurn = true;
        } else {
            whosTurn.setText("To Play\nO");
            isXTurn = false;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                spaces[j][i].resetValue();
            }
        }
    }
}
