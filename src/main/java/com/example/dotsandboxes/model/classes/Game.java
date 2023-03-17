package com.example.dotsandboxes.model.classes;

import com.example.dotsandboxes.model.enums.PlayerIndex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import static com.example.dotsandboxes.model.enums.PlayerIndex.FIRST_PLAYER;

public class Game {
    // first and second player
    private Player first;
    private Player second;

    // the index for the player turn
    //FIRST_PLAYER for the player who started and SECOND_PLAYER
    private PlayerIndex turn;

    // the type of the game , 0 for human vs human and 1 for human vs ai
    private int gameType;

    // the game board itself
    private Board gameBoard;


    //testing adding a bitboard
    public BitBoard bitBoard;

    public Game() {
        this.turn = FIRST_PLAYER;
    }

    public Game(Player first, Player second, int gameType, Board gameBoard) {
        this.gameType = gameType;
        this.first = first;
        this.second = second;
        this.gameBoard = gameBoard;
        this.turn = FIRST_PLAYER;
        this.bitBoard = new BitBoard(gameBoard.getBoardSize());
    }

    public void initBitBoard(){
        this.bitBoard = new BitBoard(gameBoard.getBoardSize());
    }


    public PlayerIndex getTurn() {
        return turn;
    }
    public int getGameType() {return gameType;}
    public Board getGameBoard() {return gameBoard;}
    public Player getSecond() {return second;}
    public Player getFirst() {return first;}

    public Player getCurrent() {return turn == FIRST_PLAYER ? first: second;}
    public boolean gameStatus() {return !((first.getScore() + second.getScore()) == (gameBoard.getBoardSize()-1)*(gameBoard.getBoardSize()-1));}

    public void setGameType(int type) {this.gameType = type;}
    public void setGameBoard(Board gameBoard) {this.gameBoard = gameBoard;}
    public void setFirst(Player first) {this.first = first;}
    public void setSecond(Player second) {this.second = second;}


    // swapping the players turn
    public void swapTurn() {
        turn = turn == FIRST_PLAYER ? PlayerIndex.SECOND_PLAYER : FIRST_PLAYER;
    }

    // check if a move is valid or not
    public boolean checkAndExecuteMove(Line line) {
        if (line.getStroke() == Color.YELLOW || line.getStroke() == Color.TRANSPARENT) {
            int scoreObtained = gameBoard.checkBoxFormed(line);
            if (turn == FIRST_PLAYER){
                first.setScore(first.getScore() + scoreObtained);
                line.setStroke(Color.BLUE);
            }
            else {
                second.setScore(second.getScore() + scoreObtained);
                line.setStroke(Color.RED);
            }
            if (scoreObtained == 0) {
                swapTurn();
            }
            isGameOver();
            return true;
        } else {
            return false;
        }
    }

    // check if the game is over
    public boolean isGameOver() {
        if (!gameStatus()) {
            gameBoard.disableAllLines();
            return true;
        }
        return false;
    }
    // get the name of the winner , pair 1 means it's a draw and pair 0 means there is a winner
    public Pair<Integer,String> getWinner() {
        if (first.getScore() == second.getScore()) {
            return new Pair<>(1,"");
        }
        else if (first.getScore() > second.getScore()) {
            return new Pair<>(0,first.getName());
        }
        return new Pair<>(0,second.getName());
    }

    // using all the init to make the board
    public void buildBoard(double sceneX,double sceneY) {
        gameBoard.initializeLines(sceneX,sceneY,40);
        gameBoard.initializeDots(sceneX,sceneY,40);
        gameBoard.initializeBoxes();
    }


    public CustomLine getLineToTurn() {
        int pos1;
        int pos2;

        for (int i = 0 ; i < this.gameBoard.getBoardSize() ; i++){
            for (int j = 0 ; j < this.gameBoard.getBoardSize() - 1 ; j++){
                if (this.gameBoard.getVerticalLines()[i][j].getStroke() == Color.TRANSPARENT){
                    pos1 = j;
                    pos2 = i;
                    pos1 = pos1*2+1;
                    CustomLine line = new CustomLine(pos1,pos2,this.gameBoard.getVerticalLines()[i][j]);
                    return line;
                }
            }
        }


        for (int i = 0 ; i < this.gameBoard.getBoardSize() ; i++){
            for (int j = 0 ; j < this.gameBoard.getBoardSize() - 1 ; j++){
                if (this.gameBoard.getHorizontalLines()[i][j].getStroke() == Color.TRANSPARENT){
                    pos1 = i*2;
                    pos2 = j;
                    CustomLine line = new CustomLine(pos1,pos2,this.gameBoard.getHorizontalLines()[i][j]);
                    return line;
                }
            }
        }
        return null;
    }

    public int eval(){
        return 1;
    }

}
