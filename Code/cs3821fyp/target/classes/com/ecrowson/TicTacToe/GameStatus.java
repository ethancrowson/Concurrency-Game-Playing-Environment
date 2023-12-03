package com.ecrowson.TicTacToe;

import java.util.ArrayList;
import java.util.List;

public class GameStatus {

    private List<WinLine> lines = new ArrayList<>();
    private Space[][] spaces = new Space[3][3];
    private WinLine result;

    /**
     * Checks the status of the game ( Won / Tied / In Play )
     * 
     * @param lines A list of the possible winning line combinations.
     * @param spaces The 2D array representation of the spaces on the board.
     * @return WinLine Either the winning line (result = win), the first row of the board (result =
     *         draw) or null (no win or draw).
     */
    public synchronized WinLine checkStatus(List<WinLine> lines, Space[][] spaces) {
        this.lines = lines;
        this.spaces = spaces;
        result = isWin();
        if (result == null) {
            return isDraw();
        }
        System.out.println(result.toString());
        return result;
    }

    /** Checks if the game is won. If a winning line is completed. */
    private WinLine isWin() {
        // Checking if there is a winning line formed on the board. If so displays Win Screen.
        for (WinLine line : lines) {
            if (line.isComplete()) {
                return line;
            }
        }
        return null;
    }

    /** Checks if the game is tied. All spaces on the board are occupied. */
    private WinLine isDraw() {
        // Checks if there is a draw/stalemate. loops over every space and if all are occupied but
        // no win then returns true and displays draw screen.
        for (Space[] spaceRow : spaces) {
            for (Space space : spaceRow) {
                if (!space.isOccupied()) {
                    return null;
                }
            }
        }
        return new WinLine(spaces[0]);
    }
}
