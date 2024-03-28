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
                Tile tile = new Tile(isLight,file,rank);
                tile.setOnMouseClicked(e -> { // Handles when each tile of the board is clicked on.

                    if (selectedTile != null){ //if a piece has been selected to move.
                        if (tile.getHighlight() != true){
                            selectedTile.deselect(); //anything else, deselect.
                            selectedTile = null;
                        }
                        else if (!tile.isOccupied()){ //if the tile is free, move the piece.
                            tile.setPiece(selectedTile.getPiece());
                            enPassantFlag(selectedTile, tile);
                            selectedTile.removePiece();
                            selectedTile.deselect();
                            selectedTile = null;
                            turnWhite = !turnWhite;
                            tile.getPiece().setHasMoved();
                            pawnPromotion(tile);
                        } 
                        else if (turnWhite != tile.getPiece().isWhite){ //if opponent piece take.
                            tile.getPiece().kill();
                            tile.removePiece();
                            tile.setPiece(selectedTile.getPiece());
                            enPassantFlag(selectedTile, tile);
                            selectedTile.removePiece();
                            selectedTile.deselect();
                            selectedTile = null;
                            turnWhite = !turnWhite;  
                            tile.getPiece().setHasMoved();
                            pawnPromotion(tile);
                        }
                        clearPossibleMoves();
                    }
                    else if (!tile.isOccupied()){
                    }
                    else if (turnWhite != tile.getPiece().isWhite){
                    }
                    else {
                        currentPiece = tile.getPiece();
                        selectedTile = tile;
                        tile.selected();
                        possibleMoves(selectedTile);
                    }
                });
                tile.setTranslateX(file * 60);
                tile.setTranslateY(rank * 60);
                board.getChildren().add(tile);

                tiles[file][rank] = tile;
            }
        }
    
        tiles[0][0].setPiece(new Rook('B',ps));
        tiles[1][0].setPiece(new Knight('B',ps));
        tiles[2][0].setPiece(new Bishop('B',ps));
        tiles[3][0].setPiece(new Queen('B',ps));
        tiles[4][0].setPiece(new King('B',ps));
        tiles[5][0].setPiece(new Knight('B',ps));
        tiles[6][0].setPiece(new Bishop('B',ps));
        tiles[7][0].setPiece(new Rook('B',ps));
        for (int i = 0; i < 8; i++){
            tiles[i][1].setPiece(new Pawn('B',ps));
        }

        tiles[0][7].setPiece(new Rook('W',ps));
        tiles[1][7].setPiece(new Knight('W',ps));
        tiles[2][7].setPiece(new Bishop('W',ps));
        tiles[3][7].setPiece(new Queen('W',ps));
        tiles[4][7].setPiece(new King('W',ps));
        tiles[5][7].setPiece(new Knight('W',ps));
        tiles[6][7].setPiece(new Bishop('W',ps));
        tiles[7][7].setPiece(new Rook('W',ps));
        for (int i = 0; i < 8; i++){
            tiles[i][6].setPiece(new Pawn('W',ps));
        }
    }
    public static void pieceClicked(Piece newPiece) {
        if (!selected) {
            currentPiece = newPiece;
            selected = true;
        } else {
            targetPiece = newPiece;
        }
    }
    public void possibleMoves(Tile sTile){
        for (Tile i : sTile.getPiece().getMoves(tiles,sTile.getX(),sTile.getY())){
            if (i.isOccupied()){
                i.setHighlight("Take");
            }else {
                i.setHighlight("Move");
            }
        }
    }
    public void clearPossibleMoves(){
        for (int file = 0; file < 8; file++){
            for (int rank = 0; rank < 8; rank++){
                tiles[file][rank].removeHighlight();

            }
        }
    }
    public void pawnPromotion(Tile backTile){
        if (backTile.getPiece().getType() == 'P'){
            if (backTile.getPiece().isWhite && backTile.getY() == 0){
                //Pawn Promotion White Screen - currently auto makes a queen
                backTile.getPiece().kill();
                backTile.removePiece();
                backTile.setPiece(new Queen('W', ps));
            }
            else if (!backTile.getPiece().isWhite && backTile.getY() == 7){
                //Pawn Promotion Black Screen - currently auto makes a queen
                backTile.getPiece().kill();
                backTile.removePiece();
                backTile.setPiece(new Queen('B', ps));
            }
        }

    }
    public void enPassantFlag(Tile startTile, Tile endTile){
        for (int file = 0; file < 8; file++){
            for (int rank = 0; rank < 8; rank ++){
                Tile currTile = tiles[file][rank]; 
                if (currTile.isOccupied()){
                    Piece currPiece = currTile.getPiece();
                     if (turnWhite == currPiece.isWhite && currPiece.getEnPassant() == true){
                        currPiece.setEnPassant(false);
                     }
                }
            }
        }
        if (startTile.getPiece().getType() == 'P'){
            if (startTile.getPiece().getHasMoved() == false){
                if (endTile.getY() == 3 || endTile.getY() == 4){
                    startTile.getPiece().setEnPassant(true);
                }
            }
        }
    }
}

