package com.ecrowson.Chess;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    Check check = new Check();
    Pane board = new Pane();
    LinkedList<Piece> ps = new LinkedList<>();
    public static Piece targetPiece;
    public static Piece currentPiece;
    public static boolean selected = false;
    public static Tile kingTile;
    public static Tile kingTileW;
    public static Tile kingTileB;
    private Tile[][] tiles = new Tile[8][8]; // Array of Spaces (the TicTacToe board).
    public boolean isLight = true;
    public boolean turnWhite = true;
    private Tile selectedTile;
    private ArrayList<Tile> legalMoves;
    private boolean checkMate;

    public Main(Pane board, Check check) throws IOException {
        this.board = board;
        this.check = check;
        createBoard();
    }

    private void createBoard() {
        // Initial setup of game board. Filling out the game board with spaces (3x3).
        for (int file = 0; file < 8; file++) {
            isLight = !isLight;
            for (int rank = 0; rank < 8; rank++) {
                isLight = !isLight;
                Tile tile = new Tile(isLight,file,rank);
                tile.setOnMouseClicked(e -> { // Handles when each tile of the board is clicked on.
                    try {
                        boardManager(tile);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
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
        tiles[5][0].setPiece(new Bishop('B',ps));
        tiles[6][0].setPiece(new Knight('B',ps));
        tiles[7][0].setPiece(new Rook('B',ps));
        for (int i = 0; i < 8; i++){
            tiles[i][1].setPiece(new Pawn('B',ps));
        }

        tiles[0][7].setPiece(new Rook('W',ps));
        tiles[1][7].setPiece(new Knight('W',ps));
        tiles[2][7].setPiece(new Bishop('W',ps));
        tiles[3][7].setPiece(new Queen('W',ps));
        tiles[4][7].setPiece(new King('W',ps));
        tiles[5][7].setPiece(new Bishop('W',ps));
        tiles[6][7].setPiece(new Knight('W',ps));
        tiles[7][7].setPiece(new Rook('W',ps));
        for (int i = 0; i < 8; i++){
            tiles[i][6].setPiece(new Pawn('W',ps));
        }
        kingTileB = tiles[4][0];
        kingTileW = tiles[4][7];
    }
    public void boardManager(Tile tile) throws InterruptedException{
        
        Task<ArrayList<Tile>> task = new Task<ArrayList<Tile>>() {

            @Override
            protected ArrayList<Tile> call() throws Exception {
                if (checkMate){return null;}
                if (tile.isOccupied()) {
                    if (selectedTile == null) {
                        if (turnWhite == tile.getPiece().isWhite){
                            selectedTile = tile;
                            tile.selected();
                            ArrayList<Tile> pMoves = tile.getPiece().getMoves(tiles,tile.getX(),tile.getY());
                            return pMoves;
                        }
                    }
                }
                return null;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent event) {
                if (checkMate){return;}
                legalMoves = task.getValue();
                System.out.println("Moves: "+legalMoves);
                if (turnWhite){kingTile = kingTileW;}
                else{kingTile = kingTileB;}
                if (selectedTile != null){
                    if (legalMoves == null){ 
                        if (tile.getHighlight() == true)
                            if (!tile.isOccupied()){ //if the tile is free, move the piece.
                                tile.setPiece(selectedTile.getPiece());
                                enPassantFlag(selectedTile, tile);
                                enPassantTake(selectedTile, tile);
                                castling(selectedTile, tile);
                                selectedTile.removePiece();
                                //selectedTile.deselect();
                                //selectedTile = null;
                                turnWhite = !turnWhite;
                                tile.getPiece().setHasMoved();
                                pawnPromotion(tile);
                                kingTileUpdate(tile);
                            } 
                            else if (turnWhite != tile.getPiece().isWhite){ //if opponent piece take.
                                tile.getPiece().kill();
                                tile.removePiece();
                                tile.setPiece(selectedTile.getPiece());
                                enPassantFlag(selectedTile, tile);
                                selectedTile.removePiece();
                                //selectedTile.deselect();
                                //selectedTile = null;
                                turnWhite = !turnWhite;  
                                tile.getPiece().setHasMoved();
                                pawnPromotion(tile);
                                kingTileUpdate(tile);
                            } 
                        selectedTile.deselect(); //anything else, deselect.
                        selectedTile = null;
                        if (kingTile == kingTileW){kingTile = kingTileB;}
                        else if (kingTile != kingTileW){kingTile = kingTileW;}
                        checkMate = check.checkMate(tiles,kingTile,turnWhite);
                        System.out.println(checkMate+","+turnWhite+","+kingTile.getPiece().isWhite);
                        if (checkMate){checkMateScreen();}
                    }  
                    else {
                        ArrayList<Tile> lMoves = check.LegaliseMoves(tiles, kingTile, legalMoves, tile.getPiece(), tile.getX(), tile.getY(), turnWhite);
                        possibleMoves(selectedTile,lMoves);
                        return;
                    }
                } 
                if(selectedTile != tile){
                    clearPossibleMoves();
                }
            }
                /*if (selectedTile == null){return;} 
                if (selectedTile == tile && legalMoves == null){ 
                    System.out.println("deselect 1");
                    selectedTile.deselect(); //anything else, deselect.
                    selectedTile = null;
                }
                else if (legalMoves != null){
                    Tile kingTile;
                    if (turnWhite){kingTile = kingTileW;}
                    else{kingTile = kingTileB;}
                    possibleMoves(selectedTile,check.LegaliseMoves(tiles, kingTile, legalMoves, tile.getPiece(), tile.getX(), tile.getY(), turnWhite));

                }
                else if (selectedTile == tile){return;}
                else if (tile.getHighlight() != true){
                    System.out.println("deselect 2");
                    selectedTile.deselect(); //anything else, deselect.
                    selectedTile = null;
                }
                else if (!tile.isOccupied()){ //if the tile is free, move the piece.
                    System.out.println("move to free");
                    tile.setPiece(selectedTile.getPiece());
                    enPassantFlag(selectedTile, tile);
                    enPassantTake(selectedTile, tile);
                    castling(selectedTile, tile);
                    selectedTile.removePiece();
                    selectedTile.deselect();
                    selectedTile = null;
                    turnWhite = !turnWhite;
                    tile.getPiece().setHasMoved();
                    pawnPromotion(tile);
                    kingTileUpdate(tile);
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
                    kingTileUpdate(tile);
                }
                else if (legalMoves == null){
                    System.out.println("deselect 3");
                    selectedTile.deselect(); //anything else, deselect.
                    selectedTile = null;
                }
                clearPossibleMoves();
                
            } *//* 
            else if (!tile.isOccupied()){
            }
            else if (turnWhite != tile.getPiece().isWhite){
            }
            else {
                currentPiece = tile.getPiece();
                selectedTile = tile;
                tile.selected();
                ArrayList<Tile> pMoves = possibleMoves(selectedTile);
                if (turnWhite){
                    check.LegaliseMoves(tiles, kingTileW, pMoves, tile.getPiece(), tile.getX(),tile.getY(), turnWhite);
                } else {
                    check.LegaliseMoves(tiles, kingTileB, pMoves, tile.getPiece(), tile.getX(),tile.getY(), turnWhite);
                }
            }*/
            //System.out.println(String.valueOf(kingTileB.getX())+ String.valueOf(kingTileB.getY()) + String.valueOf(kingTileW.getX()) + String.valueOf(kingTileW.getY()));
            
        });
        Thread th = new Thread(task); // Thread to run the task in the background
        th.start();
    }
    public static void pieceClicked(Piece newPiece) {
        if (!selected) {
            currentPiece = newPiece;
            selected = true;
        } else {
            targetPiece = newPiece;
        }
    }
    public ArrayList<Tile> possibleMoves(Tile sTile, ArrayList<Tile> pMoves){
        //ArrayList<Tile> pMoves = sTile.getPiece().getMoves(tiles,sTile.getX(),sTile.getY());
        for (Tile i : pMoves){
            if (i.isOccupied()){
                i.setHighlight("Take");
            }else {
                i.setHighlight("Move");
            }
        }
        
        return pMoves;
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
    public void enPassantTake(Tile selectedTile, Tile targetTile){
        if (selectedTile.getPiece().getType() == 'P'){
            if (targetTile.getX() != selectedTile.getX()){
                if (selectedTile.getPiece().isWhite){
                    tiles[targetTile.getX()][targetTile.getY()+1].getPiece().kill();
                    tiles[targetTile.getX()][targetTile.getY()+1].removePiece();
                } else {
                    tiles[targetTile.getX()][targetTile.getX()-1].getPiece().kill();
                    tiles[targetTile.getX()][targetTile.getX()-1].removePiece();
                }
                

            } 
        }
    }
    public void castling(Tile selectedTile, Tile targetTile){
        if (selectedTile.getPiece().getType() == 'K'){ 
            if (targetTile.getX() == 2){
                tiles[3][selectedTile.getY()].setPiece(tiles[0][selectedTile.getY()].getPiece());
                tiles[0][selectedTile.getY()].removePiece();
            }
            if (targetTile.getX() == 6){
                tiles[5][selectedTile.getY()].setPiece(tiles[7][selectedTile.getY()].getPiece());
                tiles[7][selectedTile.getY()].removePiece();
            }
        }
    }
    public void kingTileUpdate(Tile tile){
        if (tile.getPiece().getType() == 'K' && tile.getPiece().isWhite){
            kingTileW = tile;
        } else if (tile.getPiece().getType() == 'K' && !tile.getPiece().isWhite){
            kingTileB = tile;
        }
    }
    public void checkMateScreen(){
        kingTile.inCheck();
    }
}

