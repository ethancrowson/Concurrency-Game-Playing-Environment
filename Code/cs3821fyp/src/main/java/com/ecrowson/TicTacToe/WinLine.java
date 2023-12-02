package com.ecrowson.TicTacToe;

public class WinLine {
    //Lines for winning a game of TicTacToe.
    private Space[] spaces; //An array of spaces. A row/column/diagonal of spaces.

    public WinLine(Space...spaces){
        this.spaces = spaces;
    }

    public Boolean isComplete(){
        //Checks if a combination is complete (3 spaces in a line have the same value).
        if (spaces[0].getValue().isEmpty()){
            return false; //returns false if the first space in the line is empty.
        }
        return spaces[0].getValue().equals(spaces[1].getValue()) && spaces[0].getValue().equals(spaces[2].getValue());
    }
    public Space getSpace(int i){
        //In a given line it returns the space at the queried place.
        return spaces[i];
    }

    public String toString(){
        return "["+spaces[0].getValue()+"],["+spaces[1].getValue()+"],["+spaces[2].getValue()+"]";
    }
}