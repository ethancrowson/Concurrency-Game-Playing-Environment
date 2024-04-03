package com.ecrowson.Chess;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class ChessGame {
    Check check;
    Pane board;
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
    private boolean computer = true;
    private ArrayList<Tile> compMoves;

    public ChessGame(Pane board, Check check) {
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
                                turnWhite = !turnWhite;  
                                tile.getPiece().setHasMoved();
                                kingTileUpdate(tile);
                            } 
                        
                        selectedTile.deselect(); //anything else, deselect.
                        selectedTile = null;
                        if (kingTile == kingTileW){kingTile = kingTileB;}
                        else if (kingTile != kingTileW){kingTile = kingTileW;}
                        checkMate = check.checkMate(tiles,kingTile,turnWhite);
                        if (checkMate){checkMateScreen();}
                        pawnPromotion(tile);
                        
                    }  
                    else {
                        ArrayList<Tile> lMoves = check.LegaliseMoves(tiles, kingTile, legalMoves, tile.getPiece(), tile.getX(), tile.getY(), turnWhite);
                        if (computer /*&& turnWhite == false*/){
                            compMoves = possibleMoves(selectedTile,lMoves);
                            computerTurn();
                            compMoves = null;
                            return;
                        }
                        possibleMoves(selectedTile,lMoves);
                        return;
                    }
                } 
                if(selectedTile != tile){
                    clearPossibleMoves();
                }
                if (computer == true /*&& turnWhite == false*/){computerTurn();}
            }
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
    public synchronized void pawnPromotion(Tile backTile){
        if (backTile.isOccupied() && backTile.getPiece().getType() == 'P'){
            if ((backTile.getPiece().isWhite && backTile.getY() == 0) || (!backTile.getPiece().isWhite && backTile.getY() == 7)){
                char colour;
                boolean rTurn;
                VBox promotion = new VBox();
                promotion.setTranslateX(backTile.getX() * 60);
                if (backTile.getPiece().colour == 'W'){
                    colour = 'W';
                    rTurn = false;
                }else {
                    colour = 'B';
                    rTurn = true;
                    promotion.setTranslateY(180);
                }
                if (computer){
                    backTile.getPiece().kill();
                    backTile.removePiece();
                    backTile.setPiece(new Queen(colour,ps));
                    checkMate = check.checkMate(tiles,kingTile,rTurn);
                    if (checkMate){checkMateScreen();}
                    return;
                }
                ArrayList<Piece> pieces = new ArrayList<Piece>();
                Piece Q = new Queen(colour, ps);
                Piece R = new Rook(colour, ps);
                Piece B = new Bishop(colour, ps);
                Piece N = new Knight(colour, ps);
                pieces.add(Q);
                pieces.add(R);
                pieces.add(B);
                pieces.add(N);
                for (int i = 0; i < 4; i++){
                    Tile option = new Tile(true, backTile.getX(), 0+i);
                    if (i == 0) {
                        option.setPiece(Q);
                    } else if (i == 1) {
                        option.setPiece(R);
                    } else if (i == 2) {
                        option.setPiece(B);
                    } else {
                        option.setPiece(N);
                    }
                    option.setOnMouseClicked(e -> { 
                        backTile.getPiece().kill();
                        backTile.removePiece();
                        backTile.setPiece(option.getPiece());
                        ps.removeAll(pieces);
                        ps.add(option.getPiece());
                        board.getChildren().remove(promotion);
                        checkMate = check.checkMate(tiles,kingTile,rTurn);
                        if (checkMate){checkMateScreen();}
                    });
                    promotion.getChildren().add(option);
                }
                board.getChildren().add(promotion);
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
        if (selectedTile.getPiece().getType() == 'K' && selectedTile.getPiece().hasMoved == false){ 
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
    public synchronized void computerTurn(){
        Random rand = new Random();
        Tile rTile = null;
        if (selectedTile == null) {
            ArrayList<Tile> possibleTiles = new ArrayList<Tile>();
            for (int f = 0; f < 8; f++){
                for (int r = 0; r < 8; r++){
                    Tile pTile = tiles[f][r];
                    if (pTile.isOccupied()){
                        if (pTile.getPiece().isWhite == turnWhite){
                            possibleTiles.add(pTile);
                        }
                    }
                }
            }
            rTile = possibleTiles.get(rand.nextInt(possibleTiles.size()-1));
            try {
                wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            if (compMoves != null && compMoves.size() > 1){
                rTile = compMoves.get(rand.nextInt(compMoves.size()-1));
            } else {
                for (int f = 0; f < 8; f++){
                    for (int r = 0; r < 8; r++){
                        Tile pTile = tiles[f][r];
                        if (!pTile.isOccupied()){
                            rTile = pTile;
                        }
                    }
                } 
            }
        }
        try {
            boardManager(rTile);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}

