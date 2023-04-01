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

    public NodeBoard nodeBoard;

    public int countP1Moves;
    public int countP2Moves;


    public Game() {
        this.turn = FIRST_PLAYER;
    }

    public Game(Player first, Player second, int gameType, Board gameBoard) {
        this.gameType = gameType;
        this.first = first;
        this.second = second;
        this.gameBoard = gameBoard;
        this.turn = FIRST_PLAYER;
        this.countP1Moves = 0;
        this.countP2Moves = 0;
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
        if (line.getStroke() == Color.GREEN || line.getStroke() == Color.TRANSPARENT) {
            int scoreObtained = gameBoard.checkBox(line);
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







    public CustomLine getBestMove(){


        int score = Integer.MIN_VALUE;
        int tempScore;
        CustomLine bestMove = null;

        int totalBefore = 0;
        int totalAfter = 0;

        int size3Before = 0;
        int size3After = 0;

        int scoreP1 = 0;
        int scoreP2 = 0;


        //for each box , try all 4 moves , if they are posible to make
        for (NodeBox b : this.nodeBoard.NodeCountArrays[4]){

            int row = b.getBoxRow();
            int col = b.getBoxCol();

            // the row and col of the box

            if (b.getUp() == 0){
                int row1 = row*2;
                int col1 = col;

                // row1 col1 are the row and col of the calculated line
                CustomLine line = new CustomLine(row1,col1,this.gameBoard.getHorizontalLines()[row][col]);

                //saving the number of total boxes and number of box 3 before the move
                totalBefore = this.nodeBoard.numOfBoxesInGame();
                size3Before = this.nodeBoard.NodeCountArrays[3].size();

                this.nodeBoard.SetNewLine(row1,col1);


                //saving the number of total boxes and number of box 3 after the move
                totalAfter = this.nodeBoard.numOfBoxesInGame();
                size3After = this.nodeBoard.NodeCountArrays[3].size();


                scoreP1 = this.first.getScore();
                scoreP2 = this.second.getScore();

                // if the total numebr changed , we gain a point
                if (totalBefore - totalAfter == 1){
                    if (this.turn == FIRST_PLAYER){
                        scoreP1 += 1;
                    }
                    else {
                        scoreP2 += 1;
                    }
                }
                // if it wasnt , we need to see that we didnt give the enemy a box to close  , therefor if the
                // size ISNT the same , give the enemy a point
                else if (size3Before!=size3After){
                        if (this.turn == FIRST_PLAYER){
                            scoreP2 += 1;
                        }
                        else {
                            scoreP1 += 1;
                        }
                }
                //calc the score
                tempScore = eval(scoreP1 , scoreP2 , row ,col);
                tempScore++;

                // undo the move
                this.nodeBoard.UndoMove(row,col,1);


                // check if the score is the best so far
                if (tempScore > score){
                    score = tempScore;
                    bestMove = line;
                }
            }
            if (b.getDown() == 0){
                int row1 = row*2+2;
                int col1 = col;
                CustomLine line = new CustomLine(row1,col1,this.gameBoard.getHorizontalLines()[row+1][col]);

                totalBefore = this.nodeBoard.numOfBoxesInGame();
                size3Before = this.nodeBoard.NodeCountArrays[3].size();

                this.nodeBoard.SetNewLine(row1,col1);
                totalAfter = this.nodeBoard.numOfBoxesInGame();
                size3After = this.nodeBoard.NodeCountArrays[3].size();


                scoreP1 = this.first.getScore();
                scoreP2 = this.second.getScore();

                if (totalBefore - totalAfter == 1){
                    if (this.turn == FIRST_PLAYER){
                        scoreP1 += 1;
                    }
                    else {
                        scoreP2 += 1;
                    }
                }
                else if (size3Before!=size3After){
                        if (this.turn == FIRST_PLAYER){
                            scoreP2 += 1;
                        }
                        else {
                            scoreP1 += 1;
                        }

                }

                tempScore = eval(scoreP1 , scoreP2  , row ,col);
                this.nodeBoard.UndoMove(row,col,2);


                if (tempScore > score){
                    score = tempScore;
                    bestMove = line;
                }

            }
            if (b.getLeft() == 0){
                int row1 = (row*2)+1;
                int col1 = col;
                CustomLine line = new CustomLine(row1,col1,this.gameBoard.getVerticalLines()[col][row]);


                size3Before = this.nodeBoard.NodeCountArrays[3].size();
                totalBefore = this.nodeBoard.numOfBoxesInGame();
                this.nodeBoard.SetNewLine(row1,col1);

                size3After = this.nodeBoard.NodeCountArrays[3].size();
                totalAfter  = this.nodeBoard.numOfBoxesInGame();

                scoreP1 = this.first.getScore();
                scoreP2 = this.second.getScore();

                if (totalBefore - totalAfter == 1){
                    if (this.turn == FIRST_PLAYER){
                        scoreP1 += 1;
                    }
                    else {
                        scoreP2 += 1;
                    }
                }
                else if (size3Before != size3After){
                        if (this.turn == FIRST_PLAYER){
                            scoreP2 += 1;
                        }
                        else {
                            scoreP1 += 1;
                        }

                }


                tempScore = eval(scoreP1,scoreP2 , row ,col);
                this.nodeBoard.UndoMove(row,col,3);


                if (tempScore > score){
                    score = tempScore;
                    bestMove = line;
                }

            }
            if (b.getRight() == 0){
                int row1 = (row*2)+1;
                int col1 = col+1;
                CustomLine line = new CustomLine(row1,col1,this.gameBoard.getVerticalLines()[col+1][row]);


                size3Before = this.nodeBoard.NodeCountArrays[3].size();
                totalBefore = this.nodeBoard.numOfBoxesInGame();
                this.nodeBoard.SetNewLine(row1,col1);

                size3After = this.nodeBoard.NodeCountArrays[3].size();
                totalAfter  = this.nodeBoard.numOfBoxesInGame();


                scoreP1 = this.first.getScore();
                scoreP2 = this.second.getScore();

                if (totalBefore - totalAfter == 1){
                    if (this.turn == FIRST_PLAYER){
                        scoreP1 += 1;
                    }
                    else {
                        scoreP2 += 1;
                    }
                }
                else if (size3Before!=size3After){
                        if (this.turn == FIRST_PLAYER){
                            scoreP2 += 1;
                        }
                        else {
                            scoreP1 += 1;
                        }
                }



                tempScore = eval(scoreP1,scoreP2  , row ,col);
                this.nodeBoard.UndoMove(row,col,4);


                if (tempScore > score){
                    score = tempScore;
                    bestMove = line;
                }
            }

        }
        return bestMove;
    }


    // s1 is score of player 1
    // s2 is score of player 2
    // int row/col are the row and col of the box that was just played
    // eval is running AFTER the mov has been made
    public int eval(int s1 , int s2 , int row , int col){
        int score = 0;

        // getting the diffrence between the scores
        int grade = s2*10 - s1*10;

        // check if the ai has closed a box and how much 3 edges boxes has left
        if (this.turn == FIRST_PLAYER){
            score = grade + this.nodeBoard.NodeCountArrays[3].size()*10;
        }
        else {
            score = grade - this.nodeBoard.NodeCountArrays[3].size()*10;
        }

        // check the number of long chain the ai want to achive

        // if p1moves-p2moves is odd , bot want even number of long chains and vice versa
        if ((this.countP1Moves - this.countP2Moves)%2==1){
            // count Scc will count the number of long chains equal or bigger then 3
            if (this.nodeBoard.countScc()%2==0){
                score = score + 5;
            }
            else {
                score = score - 5;
            }
        }
        else {
            if (this.nodeBoard.countScc()%2==1){
                score = score + 5;
            }
            else {
                score = score - 5;
            }
        }
        // get the smallest scc which can be placed on , if there any any (last move temp=null)
        if (this.nodeBoard.NodeCountArrays[0].size() == 0 && this.nodeBoard.NodeCountArrays[1].size() == 0){
            NodeBox Temp = this.nodeBoard.findNodeInSmallestSCC(this.nodeBoard.AllNodes);
            if (Temp != null) {
                if (Temp.getBoxCol() == col && Temp.getBoxRow() == row) {
                    score = score + 7;
                }
            }
        }
        return score;

    }







    public NodeBoard getNodeBoard() {
        return nodeBoard;
    }

    public void setNodeBoard(NodeBoard nodeBoard) {
        this.nodeBoard = nodeBoard;
    }

    public void setMovesCounter(){
        if (this.turn == FIRST_PLAYER){
            this.countP1Moves++;
        }
        else {
            this.countP2Moves++;
        }
    }

    public void setTurn(PlayerIndex turn) {
        this.turn = turn;
    }


















}