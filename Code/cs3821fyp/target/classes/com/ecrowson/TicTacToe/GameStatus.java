package com.ecrowson.TicTacToe;

import java.util.ArrayList;
import java.util.List;

public class GameStatus {

    private List<WinLine> lines = new ArrayList<>();
    private Space[][] spaces = new Space[3][3];
    private WinLine result;
    public synchronized WinLine checkStatus(List<WinLine> lines, Space[][] spaces){
        this.lines = lines;
        this.spaces = spaces;
        result = isWin();
        if (result == null){return isDraw();}
        System.out.println(result.toString());
        return result;
    }

    private WinLine isWin(){
        //Checking if there is a winning line formed on the board. If so displays Win Screen.
        for (WinLine line : lines){
            if (line.isComplete()){
                return line;
            }
        }
        return null;
    }

    private WinLine isDraw(){
        //Checks if there is a draw/stalemate. loops over every space and if all are occupied but no win then returns true and displays draw screen.
        for (Space[] spaceRow : spaces){
            for (Space space : spaceRow){
                if (!space.isOccupied()){
                    return null;
                }
            }
        }
        return new WinLine(spaces[0]);
    }
}
