package com.ecrowson.TicTacToe;

public class Line {
    private Space[] spaces;
    public Line(Space...spaces){
        this.spaces = spaces;
    }

    public boolean checkLine(){
        if (spaces[0].getValue().isEmpty()){
            return false;
        }
        return spaces[0].getValue().equals(spaces[1].getValue()) && spaces[0].getValue().equals(spaces[2].getValue());
    }
}