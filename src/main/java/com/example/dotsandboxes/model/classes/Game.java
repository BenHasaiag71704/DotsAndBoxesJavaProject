package com.example.dotsandboxes.model.classes;

import com.example.dotsandboxes.model.enums.PlayerIndex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Pair;

import static com.example.dotsandboxes.model.enums.PlayerIndex.FIRST_PLAYER;
import static com.example.dotsandboxes.model.enums.PlayerIndex.SECOND_PLAYER;

public class Game {
    // first and second player
    private Player first;
    private Player second;

    // the index for the player turn
    //FIRST_PLAYER for the player who started and SECOND_PLAYER
    public PlayerIndex turn;

    // the type of the game , 0 for human vs human and 1 for human vs ai
    private int gameType;

    // the game board itself
    private Board gameBoard;

    public NodeBoard nodeBoard;


    public Game() {
        this.turn = FIRST_PLAYER;
    }

    public Game(Player first, Player second, int gameType, Board gameBoard ,NodeBoard nodeBoard , PlayerIndex turn) {
        this.gameType = gameType;
        this.first = first;
        this.second = second;
        this.gameBoard = gameBoard;
        this.nodeBoard = nodeBoard;
        this.turn = turn;
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

        //check if any 3 line boxes to close
        if (!this.nodeBoard.NodeCountArrays[3].isEmpty()){
            CustomLine l = getLineFromBoxInArrayCountN(3);
            return l;
        }
        if (!this.nodeBoard.NodeCountArrays[0].isEmpty()) {
            CustomLine myL = getLineFromBoxInArrayCountN(0);
            return myL;
        }
        if (!this.nodeBoard.NodeCountArrays[1].isEmpty()) {
            CustomLine myL = getLineFromBoxInArrayCountN(1);
            return myL;
        }
        if (!this.nodeBoard.NodeCountArrays[2].isEmpty()) {
            CustomLine myL = getLineFromBoxInArrayCountN(2);
            return myL;
        }
        return null;
    }


    public CustomLine getLineFromBoxInArrayCountN(int n){
        NodeBox b = this.nodeBoard.NodeCountArrays[n].get(0);
        int row = b.getBoxRow();
        int col = b.getBoxCol();

        if (b.getUp() == 0){
            int row1 = row*2;
            int col1 = col;
            CustomLine line = new CustomLine(row1,col1,this.gameBoard.getHorizontalLines()[row][col]);
            return line;
        }
        if (b.getDown() == 0){
            int row1 = row*2+2;
            int col1 = col;
            CustomLine line = new CustomLine(row1,col1,this.gameBoard.getHorizontalLines()[row+1][col]);
            return line;

        }
        if (b.getLeft() == 0){
            int row1 = (row*2)+1;
            int col1 = col;
            CustomLine line = new CustomLine(row1,col1,this.gameBoard.getVerticalLines()[col][row]);
            return line;

        }
        if (b.getRight() == 0){
            int row1 = (row*2)+1;
            int col1 = col+1;
            CustomLine line = new CustomLine(row1,col1,this.gameBoard.getVerticalLines()[col+1][row]);
            return line;
        }
        return null;
    }





    public NodeBoard getNodeBoard() {
        return nodeBoard;
    }

    public void setNodeBoard(NodeBoard nodeBoard) {
        this.nodeBoard = nodeBoard;
    }








    public int Max(int depth , Game copyGame){
        //System.out.println("current depth is" + depth);
        if (depth == 0 || !copyGame.gameStatus()){
            return eval(copyGame);
        }
        int maxScore = Integer.MIN_VALUE;
        int currentScore;

        for (NodeBox minimaxBox : copyGame.nodeBoard.NodeCountArrays[4]){
            if (minimaxBox.lineCount() < 4){

                int row = minimaxBox.getBoxRow();
                int col = minimaxBox.getBoxCol();

                int row1 = 0;
                int col1 = 0;
                CustomLine line = null;

                if (minimaxBox.getUp() == 0){
                    row1 = row*2;
                    col1 = col;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getHorizontalLines()[row][col]);
                }
                else if (minimaxBox.getDown() == 0){
                    row1 = row*2+2;
                    col1 = col;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getHorizontalLines()[row+1][col]);

                }
                else if (minimaxBox.getLeft() == 0){
                    row1 = (row*2)+1;
                    col1 = col;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getVerticalLines()[col][row]);

                }
                else if (minimaxBox.getRight() == 0){
                    row1 = (row*2)+1;
                    col1 = col+1;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getVerticalLines()[col+1][row]);
                }
                // now copy game is after the move

                NodeBoard BeforeNodeBoard = copyGame.nodeBoard;
                copyGame.nodeBoard.SetNewLineMiniMax(row1,col1);

                updateScoreInMinimax(BeforeNodeBoard ,copyGame.nodeBoard , copyGame);
                copyGame.swapTurn();

                //checkAndExecuteMove(line.getCustomLine());

                currentScore = Min(depth-1,copyGame);
                if (currentScore > maxScore){
                    maxScore = currentScore;
                }
            }
        }

        return maxScore;
    }

    public int Min(int depth , Game copyGame){
        //System.out.println("current depth is" + depth);
        if (depth == 0 || !copyGame.gameStatus()){
            return eval(copyGame);
        }
        int minScore = Integer.MAX_VALUE;
        int currentScore;

        for (NodeBox minimaxBox : copyGame.nodeBoard.NodeCountArrays[4]){
            if (minimaxBox.lineCount() < 4){

                int row = minimaxBox.getBoxRow();
                int col = minimaxBox.getBoxCol();

                int row1 = 0;
                int col1 = 0;
                CustomLine line = null;

                if (minimaxBox.getUp() == 0){
                    row1 = row*2;
                    col1 = col;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getHorizontalLines()[row][col]);
                }
                else if (minimaxBox.getDown() == 0){
                    row1 = row*2+2;
                    col1 = col;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getHorizontalLines()[row+1][col]);

                }
                else if (minimaxBox.getLeft() == 0){
                    row1 = (row*2)+1;
                    col1 = col;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getVerticalLines()[col][row]);

                }
                else if (minimaxBox.getRight() == 0){
                    row1 = (row*2)+1;
                    col1 = col+1;
                    line = new CustomLine(row1,col1,copyGame.gameBoard.getVerticalLines()[col+1][row]);
                }
                // now copy game is after the move

                NodeBoard BeforeNodeBoard = copyGame.nodeBoard;
                copyGame.nodeBoard.SetNewLineMiniMax(row1,col1);

                updateScoreInMinimax(BeforeNodeBoard ,copyGame.nodeBoard ,copyGame);
                copyGame.swapTurn();

                //checkAndExecuteMove(line.getCustomLine());

                currentScore = Max(depth-1,copyGame);
                if (currentScore < minScore){
                    minScore = currentScore;
                }
            }
        }

        return minScore;
    }

    public int eval(Game copyGame){
        if (copyGame.turn == SECOND_PLAYER && copyGame.second.getScore() > (gameBoard.getBoardSize()*gameBoard.getBoardSize()/2) ){
            return Integer.MAX_VALUE;
        }
        if (copyGame.turn == FIRST_PLAYER && copyGame.first.getScore() > (gameBoard.getBoardSize()*gameBoard.getBoardSize()/2) ){
            return Integer.MIN_VALUE;
        }
        int currentScore = copyGame.second.getScore() - copyGame.first.getScore();
        if (copyGame.turn == SECOND_PLAYER){
            return currentScore + copyGame.nodeBoard.NodeCountArrays[3].size();
        }
        else {
            return currentScore - copyGame.nodeBoard.NodeCountArrays[3].size();
        }
    }

    public CustomLine getTurnFromMiniMax(Game mm) {
        //

        // the problem is probably here !
        Game copyGame = new Game(mm.first.DeepCopyPlayer() , mm.second.DeepCopyPlayer() , mm.getGameType() , mm.gameBoard.DeepCopyBoard() , mm.nodeBoard.DeepCopyNodeBoard(gameBoard.getBoardSize()) , mm.getTurn());
        //
        int maxScore = Integer.MIN_VALUE;
        int minScore = Integer.MAX_VALUE;
        int currentScore;
        CustomLine bestLine = null;


        //copyGame.nodeBoard.NodeCountArrays[4]

        for (NodeBox minimaxBox : copyGame.nodeBoard.NodeCountArrays[4]) {

            if (minimaxBox.lineCount() < 4) {

                int row = minimaxBox.getBoxRow();
                int col = minimaxBox.getBoxCol();

                int row1 = 0;
                int col1 = 0;
                CustomLine line = null;


                if (minimaxBox.getUp() == 0) {
                    row1 = row * 2;
                    col1 = col;
                    line = new CustomLine(row1, col1, copyGame.gameBoard.getHorizontalLines()[row][col]);


                    NodeBoard BeforeNodeBoard = copyGame.nodeBoard;
                    copyGame.nodeBoard.SetNewLineMiniMax(row1,col1);

                    updateScoreInMinimax(BeforeNodeBoard ,copyGame.nodeBoard , copyGame);
                    copyGame.swapTurn();


                    if (copyGame.getTurn() == FIRST_PLAYER){
                        currentScore = Min(4,copyGame);
                    }

                    else{
                        currentScore = Max(4,copyGame);
                    }

                    if (copyGame.getTurn() == FIRST_PLAYER){
                        if (currentScore > maxScore){
                            maxScore = currentScore;
                            bestLine = line;
                        }
                    }
                    else {
                        if (currentScore < minScore){
                            minScore = currentScore;
                            bestLine = line;
                        }
                    }

                }
                if (minimaxBox.getDown() == 0) {
                    row1 = row * 2 + 2;
                    col1 = col;
                    line = new CustomLine(row1, col1, copyGame.gameBoard.getHorizontalLines()[row + 1][col]);

                    NodeBoard BeforeNodeBoard = copyGame.nodeBoard;
                    copyGame.nodeBoard.SetNewLineMiniMax(row1,col1);

                    updateScoreInMinimax(BeforeNodeBoard ,copyGame.nodeBoard , copyGame);
                    copyGame.swapTurn();


                    if (copyGame.getTurn() == FIRST_PLAYER){
                        currentScore = Min(4,copyGame);
                    }

                    else{
                        currentScore = Max(4,copyGame);
                    }

                    if (copyGame.getTurn() == FIRST_PLAYER){
                        if (currentScore > maxScore){
                            maxScore = currentScore;
                            bestLine = line;
                        }
                    }
                    else {
                        if (currentScore < minScore){
                            minScore = currentScore;
                            bestLine = line;
                        }
                    }

                }
                if (minimaxBox.getLeft() == 0) {
                    row1 = (row * 2) + 1;
                    col1 = col;
                    line = new CustomLine(row1, col1, copyGame.gameBoard.getVerticalLines()[col][row]);

                    NodeBoard BeforeNodeBoard = copyGame.nodeBoard;
                    copyGame.nodeBoard.SetNewLineMiniMax(row1,col1);

                    updateScoreInMinimax(BeforeNodeBoard ,copyGame.nodeBoard , copyGame);
                    copyGame.swapTurn();


                    if (copyGame.getTurn() == FIRST_PLAYER){
                        currentScore = Min(4,copyGame);
                    }

                    else{
                        currentScore = Max(4,copyGame);
                    }

                    if (copyGame.getTurn() == FIRST_PLAYER){
                        if (currentScore > maxScore){
                            maxScore = currentScore;
                            bestLine = line;
                        }
                    }
                    else {
                        if (currentScore < minScore){
                            minScore = currentScore;
                            bestLine = line;
                        }
                    }

                }
                if (minimaxBox.getRight() == 0) {
                    row1 = (row * 2) + 1;
                    col1 = col + 1;
                    line = new CustomLine(row1, col1, copyGame.gameBoard.getVerticalLines()[col + 1][row]);

                    NodeBoard BeforeNodeBoard = copyGame.nodeBoard;
                    copyGame.nodeBoard.SetNewLineMiniMax(row1,col1);

                    updateScoreInMinimax(BeforeNodeBoard ,copyGame.nodeBoard , copyGame);
                    copyGame.swapTurn();


                    if (copyGame.getTurn() == FIRST_PLAYER){
                        currentScore = Min(4,copyGame);
                    }

                    else{
                        currentScore = Max(4,copyGame);
                    }

                    if (copyGame.getTurn() == FIRST_PLAYER){
                        if (currentScore > maxScore){
                            maxScore = currentScore;
                            bestLine = line;
                        }
                    }
                    else {
                        if (currentScore < minScore){
                            minScore = currentScore;
                            bestLine = line;
                        }
                    }
                }

            }
        }
        return bestLine;
    }

    public void updateScoreInMinimax(NodeBoard before , NodeBoard after , Game gameCopy){
        if (countTotalBoxes(before)-countTotalBoxes(after) == 1){
            if (gameCopy.turn == FIRST_PLAYER){
                gameCopy.first.setScore(gameCopy.first.getScore()+1);
            }
            else {
                gameCopy.second.setScore(gameCopy.second.getScore()+1);
            }
        }

    }

    public int countTotalBoxes(NodeBoard nb){
        return nb.NodeCountArrays[0].size() + nb.NodeCountArrays[1].size() + nb.NodeCountArrays[2].size() + nb.NodeCountArrays[3].size();
    }
}
