package com.ecrowson.TicTacToe;

public class WinLine {
    private Space[] spaces;

    public WinLine(Space...spaces){
        this.spaces = spaces;
    }

    public Boolean isComplete(){
        if (spaces[0].getValue().isEmpty()){
            return false;
        }
        return spaces[0].getValue().equals(spaces[1].getValue()) && spaces[0].getValue().equals(spaces[2].getValue());
    }
    public Space getSpace(int i){
        return spaces[i];
    }
}