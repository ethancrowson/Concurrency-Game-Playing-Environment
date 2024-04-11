package com.ecrowson.Chess;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * The instance of the chess game. Handles the board generation and game
 * playing.
 * 
 * @author Ethan Crowson
 */
public class ChessGame {
    Check check; // The instance of the Check class that all ChessGame instances concurrently
                 // use.
    Pane board; // The Pane the board is built upon.
    private Label whoseTurn = new Label("White's Turn");
    // private int material = 0; // Stores the total material. Positive is in whites
    // favor.
    private int materialW = 0; // Stores white's material.
    private int materialB = 0; // Stores black's material.
    private Label materialWhite = new Label("White\n" + materialW);
    private Label materialBlack = new Label("Black\n" + materialB);
    private HBox bottom = new HBox(40);
    private LinkedList<Piece> ps = new LinkedList<>(); // A linked list of all of the pieces on the board.
    private static Tile kingTile; // The Tile of the current King piece being observed.
    private static Tile kingTileW; // The current Tile the white king is on.
    private static Tile kingTileB; // The current Tile the black king is on.
    private Tile[][] tiles = new Tile[8][8]; // Array of Tiles (the Chess board).
    private boolean isLight = true; // Boolean of whether the Tile is light or dark. First tile is light.
    private boolean turnWhite = true; // Boolean of whose turn it is to make a move.
    private Tile selectedTile; // Will hold the tile that has been selected for a piece to move from.
    private ArrayList<Tile> pseudolegalMoves; // The pseudo legal moves that a given piece can make on the board.
    private int checkMate; // 0 = no end game. 1 = stalemate. 2 = checkmate.
    private int computer; // True = 2 Computers playing random moves for concurrency demonstration. False
                          // = Human v Human.
    private ArrayList<Tile> compMoves; // Possible computer moves. Used if computer = true.
    private boolean waitPromotion = false;

    /**
     * Constructs the game instance that will handle chess. Calls createBoard().
     * 
     * @param board    the window that the board will be generated upon.
     * @param check    the shared Check instance between chess instances.
     * @param computer the number of computer bots that will play the game (0/1/2).
     */
    public ChessGame(Pane board, Check check, int computer) {
        this.board = board;
        this.check = check;
        this.computer = computer;
        createBoard();
    }

    /**
     * Generates the tiles of the board and fills tiles[][]. Adds the standard piece
     * setup to the board.
     */
    private void createBoard() {
        // Initial setup of game board. Filling out the game board with spaces (3x3).
        for (int file = 0; file < 8; file++) {
            isLight = !isLight;
            for (int rank = 0; rank < 8; rank++) {
                isLight = !isLight;
                Tile tile = new Tile(isLight, file, rank);
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

        tiles[0][0].setPiece(new Rook('B', ps));
        tiles[1][0].setPiece(new Knight('B', ps));
        tiles[2][0].setPiece(new Bishop('B', ps));
        tiles[3][0].setPiece(new Queen('B', ps));
        tiles[4][0].setPiece(new King('B', ps));
        tiles[5][0].setPiece(new Bishop('B', ps));
        tiles[6][0].setPiece(new Knight('B', ps));
        tiles[7][0].setPiece(new Rook('B', ps));
        for (int i = 0; i < 8; i++) {
            tiles[i][1].setPiece(new Pawn('B', ps));
        }

        tiles[0][7].setPiece(new Rook('W', ps));
        tiles[1][7].setPiece(new Knight('W', ps));
        tiles[2][7].setPiece(new Bishop('W', ps));
        tiles[3][7].setPiece(new Queen('W', ps));
        tiles[4][7].setPiece(new King('W', ps));
        tiles[5][7].setPiece(new Bishop('W', ps));
        tiles[6][7].setPiece(new Knight('W', ps));
        tiles[7][7].setPiece(new Rook('W', ps));
        for (int i = 0; i < 8; i++) {
            tiles[i][6].setPiece(new Pawn('W', ps));
        }
        kingTileB = tiles[4][0];
        kingTileW = tiles[4][7];

        whoseTurn.setStyle(
                "-fx-font: 32 arial;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: lightgrey;");
        whoseTurn.setAlignment(Pos.CENTER);
        materialWhite.setStyle(
                "-fx-font: 24 arial;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: lightgrey;");
        materialWhite.setTextAlignment(TextAlignment.CENTER);
        materialBlack.setStyle(
                "-fx-font: 24 arial;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: lightgrey;");
        materialBlack.setTextAlignment(TextAlignment.CENTER);
        bottom.setPadding(new Insets(20, 20, 20, 20));
        bottom.getChildren().addAll(whoseTurn, materialWhite, materialBlack);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setLayoutY(480);
        board.getChildren().add(bottom);
    }

    /**
     * Handles the actual game playing. It is called upon every click of a tile on
     * the chess board.
     * 
     * @param tile the tile that has been clicked. The tile calling this method.
     * @throws InterruptedException thrown when the thread is interrupted, either
     *                              before or during
     *                              the task.
     */
    private void boardManager(Tile tile) throws InterruptedException {
        checkEndGame();
        if (waitPromotion == true){
            return;
        }
        if (checkMate != 0) {
            return;
        }
        // Task to handle the move generation in the background.
        Task<ArrayList<Tile>> task = new Task<ArrayList<Tile>>() {

            @Override
            protected ArrayList<Tile> call() throws Exception {
                if (tile.isOccupied()) {
                    if (selectedTile == null) {
                        if (turnWhite == tile.getPiece().isWhite) {
                            // If the clicked tile has a piece the same colour of whose turn it is.
                            selectedTile = tile;
                            tile.selected();
                            ArrayList<Tile> pMoves = tile.getPiece().getMoves(tiles, tile.getX(), tile.getY());
                            return pMoves; // Returns the possible moves this piece can make.
                        }
                    }
                }
                return null;
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent event) {
                if (checkMate != 0) {
                    return; // If checkmate then no more game playing.
                }
                pseudolegalMoves = task.getValue();
                kingTile = turnWhite ? kingTileW : kingTileB; // points kingTile at correct king.
                if (selectedTile != null) {
                    if (pseudolegalMoves == null) {
                        handleTileClick(tile);
                    } else {
                        ArrayList<Tile> lMoves = check.LegaliseMoves(tiles, kingTile, pseudolegalMoves, tile.getPiece(),
                                tile.getX(), tile.getY(), turnWhite);
                        if (computer == 2 || (computer == 1 && !turnWhite)) { // Computer moves
                            compMoves = possibleMoves(lMoves);
                            computerTurn();
                            compMoves = null;
                            return;
                        }
                        possibleMoves(lMoves); // Highlights the legal moves on the board.
                    }
                }
                if (selectedTile != tile) { // Removes the highlighted moves from the board.
                    clearPossibleMoves();
                }

                if (computer == 2 || (computer == 1 && !turnWhite)) {
                    computerTurn();
                }
            }
        });
        Thread th = new Thread(task); // Thread to run the task in the background
        th.start();
    }

    /**
     * Handles moving and taking of pieces as well as updating whose turn to move it
     * is.
     * 
     * @param tile the tile that was clicked.
     */
    private void handleTileClick(Tile tile) {
        if (tile.getHighlight()) {
            if (!tile.isOccupied()) {
                movePieceToTile(tile);
            } else if (turnWhite != tile.getPiece().isWhite && tile.getPiece().getType() != 'K') {
                takePiece(tile);
            }
        }
        selectedTile.deselect(); // Deselect selectedTile.
        selectedTile = null;
        if (kingTile == kingTileW) {
            kingTile = kingTileB;
        } else {
            kingTile = kingTileW;
        }
        updateTurn();
        updateMaterial();
        checkEndGame();
    }

    /**
     * Moves the selected piece to the designated unoccupied tile.
     * 
     * @param tile the unoccupied tile that was clicked.
     */
    private void movePieceToTile(Tile tile) {
        tile.setPiece(selectedTile.getPiece());
        enPassantFlag(tile);
        enPassantTake(tile);
        castling(tile);
        selectedTile.getPiece().setHasMoved();
        selectedTile.removePiece();
        turnWhite = !turnWhite;
        pawnPromotion(tile);
        kingTileUpdate(tile);
    }

    /**
     * Moves the selected piece to the designated occupied tile and takes the piece.
     * 
     * @param tile the occupied tile that was clicked.
     */
    private void takePiece(Tile tile) {
        tile.getPiece().kill();
        tile.removePiece();
        tile.setPiece(selectedTile.getPiece());
        enPassantFlag(tile);
        selectedTile.removePiece();
        turnWhite = !turnWhite;
        tile.getPiece().setHasMoved();
        pawnPromotion(tile);
        kingTileUpdate(tile);
    }

    /**
     * Updates the material counter and the graphics that display them.
     */
    private void updateMaterial() {
        int material = 0;
        for (Piece p : ps) {
            material += p.isWhite ? p.getMaterial() : -p.getMaterial();
        }
        materialW = material > 0 ? material : -material;
        materialB = material > 0 ? -material : material;
        materialWhite.setText("White\n" + materialW);
        materialBlack.setText("Black\n" + materialB);
    }

    /**
     * Updates the graphics displaying whose turn it is to move.
     */
    private void updateTurn() {
        if (turnWhite) {
            whoseTurn.setText("White's Turn");
        } else {
            whoseTurn.setText("Black's Turn");
        }
    }

    /**
     * Sets the highlights of the legal moves on the tiles on the board.
     * 
     * @param pMoves the tiles which have legal moves to be highlighted.
     * @return the legal moves that have now been highlighted.
     */
    private ArrayList<Tile> possibleMoves(ArrayList<Tile> pMoves) {
        for (Tile i : pMoves) {
            if (i.isOccupied()) {
                i.setHighlight("Take");
            } else {
                i.setHighlight("Move");
            }
        }

        return pMoves;
    }

    /**
     * Removes all the highlights from all tiles on the board.
     */
    private void clearPossibleMoves() {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                tiles[file][rank].removeHighlight();
            }
        }
    }

    /**
     * Checks if a pawn has made it to the back ranks. Creates the piece promotion
     * selection screen and promotes the piece accordingly.
     * 
     * @param backTile the tile on the back rank the pawn is currently occupying.
     */
    private void pawnPromotion(Tile backTile) {
        if (backTile.isOccupied() && backTile.getPiece().getType() == 'P') {
            char colour;
            boolean rTurn;
            if ((backTile.getPiece().isWhite && backTile.getY() == 0)
                    || (!backTile.getPiece().isWhite && backTile.getY() == 7)) {
                VBox promotion = new VBox();
                board.getChildren().add(promotion);
                promotion.setTranslateX(backTile.getX() * 60);
                if (backTile.getPiece().getColour() == 'W') {
                    colour = 'W';
                    rTurn = false;
                } else {
                    colour = 'B';
                    rTurn = true;
                    promotion.setTranslateY(180); // Sets the promotion screen to appear at bottom of board.
                }
                if (computer == 2 || (computer == 1 && turnWhite)) {
                    promotePiece(backTile, new Queen(colour, ps), rTurn);
                    return;
                }
                ArrayList<Piece> pieces = new ArrayList<>(); // Array to temporarily store options for promotion.
                pieces.add(new Queen(colour, ps));
                pieces.add(new Rook(colour, ps));
                pieces.add(new Bishop(colour, ps));
                pieces.add(new Knight(colour, ps));
                ps.removeAll(pieces);
                int pieceCounter = 0;
                for (Piece piece : pieces) {
                    Tile option = new Tile(true, backTile.getX(), pieceCounter);
                    option.setPiece(piece);
                    option.setOnMouseClicked(e -> { // On click of selection, change Pawn to selected piece.
                        promotePiece(backTile, option.getPiece(), rTurn);
                        board.getChildren().remove(promotion);
                        waitPromotion = false;
                    });
                    promotion.getChildren().add(option);
                    pieceCounter++;
                }
                waitPromotion = true;

            }
        }
    }

    /**
     * Handles the actual changing of the piece from the pawn once chosen.
     * 
     * @param backTile      the tile on the back rank the pawn is currently
     *                      occupying.
     * @param selectedPiece the piece chosen to promote to.
     * @param rTurn         what colour is promoting the piece.
     */
    private void promotePiece(Tile backTile, Piece selectedPiece, boolean rTurn) {
        backTile.getPiece().kill();
        backTile.removePiece();
        backTile.setPiece(selectedPiece);
        if (computer == 0) {
            ps.add(selectedPiece);
        }
        updateMaterial();
        checkEndGame();
    }

    /**
     * Checks if en-Passant is possible. Also removes the flag if the 1 turn move
     * has passed.
     * 
     * @param endTile the target tile in which you want to move a piece to.
     */
    private void enPassantFlag(Tile endTile) {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                Tile currTile = tiles[file][rank];
                if (currTile.isOccupied()) {
                    Piece currPiece = currTile.getPiece();
                    if (turnWhite == currPiece.isWhite && currPiece.getEnPassant() == true) {
                        currPiece.setEnPassant(false); // If the one turn has passed. En-passant is no longer possible.
                    }
                }
            }
        }
        if (selectedTile.getPiece().getType() == 'P') { // If double push pawn then then that pawn's flag to true.
            if (selectedTile.getPiece().getHasMoved() == false) {
                if (endTile.getY() == 3 || endTile.getY() == 4) {
                    selectedTile.getPiece().setEnPassant(true);
                }
            }
        }
    }

    /**
     * Handles the actual taking of the piece in the en-Passant move as the take is
     * not on the tile the piece is occupying.
     * 
     * @param targetTile the target tile in which you want to move a piece to.
     */
    private void enPassantTake(Tile targetTile) {
        if (selectedTile.getPiece().getType() == 'P') {
            if (targetTile.getX() != selectedTile.getX()) {
                if (selectedTile.getPiece().isWhite && tiles[targetTile.getX()][targetTile.getY() + 1].isOccupied()) {
                    // If white, remove piece one below the tile being moved to.
                    tiles[targetTile.getX()][targetTile.getY() + 1].getPiece().kill();
                    tiles[targetTile.getX()][targetTile.getY() + 1].removePiece();
                } else if (!selectedTile.getPiece().isWhite
                        && tiles[targetTile.getX()][targetTile.getY() - 1].isOccupied()) {
                    // If black, remove piece one above the tile being moved to.
                    tiles[targetTile.getX()][targetTile.getX() - 1].getPiece().kill();
                    tiles[targetTile.getX()][targetTile.getX() - 1].removePiece();
                }

            }
        }
    }

    /**
     * Handles the moving of the pieces in the instance of a castle move.
     * 
     * @param targetTile the target tile in which you want to move a piece to.
     */
    private void castling(Tile targetTile) {
        if (selectedTile.getPiece().getType() == 'K' && selectedTile.getPiece().getHasMoved() == false) {
            if (targetTile.getX() == 2 && targetTile.getHighlight() == true) {
                tiles[3][selectedTile.getY()].setPiece(tiles[0][selectedTile.getY()].getPiece());
                tiles[0][selectedTile.getY()].removePiece();
            }
            if (targetTile.getX() == 6 && targetTile.getHighlight() == true) {
                tiles[5][selectedTile.getY()].setPiece(tiles[7][selectedTile.getY()].getPiece());
                tiles[7][selectedTile.getY()].removePiece();
            }
        }
    }

    /**
     * Updates the associated kingTile tracking variable if the piece moved is a
     * king.
     * 
     * @param tile the tile that a piece has jsut been moved to.
     */
    private void kingTileUpdate(Tile tile) {
        if (tile.isOccupied() && tile.getPiece().getType() == 'K' && tile.getPiece().isWhite) {
            kingTileW = tile;
        } else if (tile.isOccupied() && tile.getPiece().getType() == 'K' && !tile.getPiece().isWhite) {
            kingTileB = tile;
        }
    }

    /**
     * Observes the board to look for a checkmate or stalemate, and if so calls the
     * method that handles the end screen.
     */
    private void checkEndGame() {
        checkMate = check.checkMate(tiles, kingTile, turnWhite, ps);
        if (checkMate != 0) {
            endScreen();
        }
    }

    /**
     * The Screen displayed when a checkMate or stalemate has been made.
     */
    private void endScreen() {
        if (checkMate == 1) {
            whoseTurn.setText("Stalemate");
        } else if (checkMate == 2) {
            kingTile.inCheck();
            if (kingTile == kingTileW) {
                whoseTurn.setText("Black Wins!");
            } else {
                whoseTurn.setText("White Wins!");
            }
        }
    }

    /**
     * Handles the moves the computer plays.
     */
    private synchronized void computerTurn() {
        Random rand = new Random();
        Tile rTile = null;
        if (selectedTile == null) {
            ArrayList<Tile> possibleTiles = new ArrayList<Tile>();
            for (int f = 0; f < 8; f++) {
                for (int r = 0; r < 8; r++) {
                    Tile pTile = tiles[f][r];
                    if (pTile.isOccupied()) {
                        if (pTile.getPiece().isWhite == turnWhite) {
                            possibleTiles.add(pTile);
                        }
                    }
                }
            }
            // Selects a random tile from the tiles with the colour of whose turn it is.
            if (possibleTiles.size() == 0) {
                return;
            } else {
                rTile = possibleTiles.get(rand.nextInt(possibleTiles.size()));
            }
        } else {
            if (compMoves != null && compMoves.size() > 0) {
                // If selected tiles has makeable moves then make a random move from this set.
                while (rTile == null) {
                    rTile = compMoves.get(rand.nextInt(compMoves.size()));
                    if (rTile == kingTile) {
                        rTile = null;
                    }
                }

            } else {
                // Otherwise reset the selected Tile by clicking the first unoccupied tile.
                for (int f = 0; f < 8; f++) {
                    for (int r = 0; r < 8; r++) {
                        Tile pTile = tiles[f][r];
                        if (!pTile.isOccupied()) {
                            rTile = pTile;
                        }
                    }
                }
            }
        }
        // Calls the board manager to run this tile selection through the game.
        try {
            boardManager(rTile);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
