package com.example.dotsandboxes.model.classes;

public class Player {
    public String name;
    public int score;
    public boolean isAi;

    /**
     * constructor
     * @param isAi
     */
    public Player(boolean isAi) {
        score = 0;
        this.isAi = isAi;
    }
    public String getName() {return name;}
    public int getScore() {return score;}

    public void setName(String name) {this.name = name;}
    public void setScore(int score) {this.score = score;}
    public Board play(Board gameBoard){
        return gameBoard;
    }
    public boolean isAi() {
        return isAi;
    }
}