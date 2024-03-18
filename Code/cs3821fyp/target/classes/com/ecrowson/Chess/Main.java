package com.ecrowson.Chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.LinkedList;

public class Main extends Application {
    Pane board = new Pane();
    LinkedList<Piece> ps = new LinkedList<>();
    public static Piece targetPiece;
    public static Piece currentPiece;
    public static boolean selected = false;
    private Tile[][] tiles = new Tile[8][8]; // Array of Spaces (the TicTacToe board).
    public boolean isLight = true;
    public boolean turnWhite = true;
    private Tile selectedTile;

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(board, 480, 480);
        stage.setScene(scene);
        stage.setTitle("Chess");
        createBoard();

        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void createBoard() {
        // Initial setup of game board. Filling out the game board with spaces (3x3).
        for (int file = 0; file < 8; file++) {
            isLight = !isLight;
            for (int rank = 0; rank < 8; rank++) {
                isLight = !isLight;
                Tile tile = new Tile(isLight);
                tile.setOnMouseClicked(e -> { // Handles when each tile of the board is clicked on.
                    if (selectedTile != null){
                        tile.setPiece(selectedTile.getPiece());
                        selectedTile.removePiece();
                        selectedTile = null;
                    }
                    else if (!tile.isOccupied()){
                    }
                    else if (turnWhite != tile.getPiece().isWhite){
                    }
                    else {
                        currentPiece = tile.getPiece();
                        System.out.println(currentPiece.colour + "" + currentPiece.type);
                        selectedTile = tile;
                        tile.selected();
                    }
                });
                tile.setTranslateX(file * 60);
                tile.setTranslateY(rank * 60);
                board.getChildren().add(tile);

                tiles[file][rank] = tile;
            }
        }
        Piece BR = new Piece(board, tiles[0][0], 'B', 'R', ps);
        Piece BN = new Piece(board, tiles[1][0], 'B', 'N', ps);
        Piece BB = new Piece(board, tiles[2][0], 'B', 'B', ps);
        Piece BQ = new Piece(board, tiles[3][0], 'B', 'Q', ps);
        Piece BK = new Piece(board, tiles[4][0], 'B', 'K', ps);
        Piece BB2 = new Piece(board, tiles[5][0], 'B', 'B', ps);
        Piece BN2 = new Piece(board, tiles[6][0], 'B', 'N', ps);
        Piece BR2 = new Piece(board, tiles[7][0], 'B', 'R', ps);
        Piece BP1 = new Piece(board, tiles[0][1], 'B', 'P', ps);
        Piece BP2 = new Piece(board, tiles[1][1], 'B', 'P', ps);
        Piece BP3 = new Piece(board, tiles[2][1], 'B', 'P', ps);
        Piece BP4 = new Piece(board, tiles[3][1], 'B', 'P', ps);
        Piece BP5 = new Piece(board, tiles[4][1], 'B', 'P', ps);
        Piece BP6 = new Piece(board, tiles[5][1], 'B', 'P', ps);
        Piece BP7 = new Piece(board, tiles[6][1], 'B', 'P', ps);
        Piece BP8 = new Piece(board, tiles[7][1], 'B', 'P', ps);

        Piece WR = new Piece(board, tiles[0][7], 'W', 'R', ps);
        Piece WN = new Piece(board, tiles[1][7], 'W', 'N', ps);
        Piece WB = new Piece(board, tiles[2][7], 'W', 'B', ps);
        Piece WQ = new Piece(board, tiles[3][7], 'W', 'Q', ps);
        Piece WK = new Piece(board, tiles[4][7], 'W', 'K', ps);
        Piece WB2 = new Piece(board, tiles[5][7], 'W', 'B', ps);
        Piece WN2 = new Piece(board, tiles[6][7], 'W', 'N', ps);
        Piece WR2 = new Piece(board, tiles[7][7], 'W', 'R', ps);
        Piece WP1 = new Piece(board, tiles[0][6], 'W', 'P', ps);
        Piece WP2 = new Piece(board, tiles[1][6], 'W', 'P', ps);
        Piece WP3 = new Piece(board, tiles[2][6], 'W', 'P', ps);
        Piece WP4 = new Piece(board, tiles[3][6], 'W', 'P', ps);
        Piece WP5 = new Piece(board, tiles[4][6], 'W', 'P', ps);
        Piece WP6 = new Piece(board, tiles[5][6], 'W', 'P', ps);
        Piece WP7 = new Piece(board, tiles[6][6], 'W', 'P', ps);
        Piece WP8 = new Piece(board, tiles[7][6], 'W', 'P', ps);
    }
    public static void pieceClicked(Piece newPiece) {
        if (!selected) {
            currentPiece = newPiece;
            selected = true;
        } else {
            targetPiece = newPiece;
        }
    }

}

