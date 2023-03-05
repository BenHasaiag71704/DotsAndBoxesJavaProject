package com.example.dotsandboxes.model.classes;

public class Player {
    private String name;
    private int score;
    private boolean isAi;

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
