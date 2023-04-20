package com.example.dotsandboxes.controller;

import com.example.dotsandboxes.model.classes.CustomLine;
import com.example.dotsandboxes.model.classes.Game;
import com.example.dotsandboxes.model.classes.NodeBoard;
import com.example.dotsandboxes.view.GameScreen;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.Instant;


public class GameScreenController {


    public Game model;
    public GameScreen view;
    public int boardSize;


/**
    * constructor for the game screen controller
     * @param model - the game model which contains the game data (board,  nodeboard etc)
     * @param view - the screen view which contains the game screen
     * @param stage - the app window where the gui is displayed
     * @throws Exception
     */
    public GameScreenController(Game model, GameScreen view, Stage stage) throws Exception {
        this.model = model;
        this.view = view;
        this.boardSize = model.getGameBoard().getBoardSize();


        this.model.nodeBoard = new NodeBoard(boardSize);


        double startingX =  (double) view.getSceneX()/2 - (double) (boardSize *50)/2;
        double startingY = (double) view.getSceneY()/2 - (double) (boardSize *50)/2;

        model.buildBoard(startingX,startingY);
        setLineButtons(model.getGameBoard().getHorizontalLines() , true);
        setLineButtons(model.getGameBoard().getVerticalLines() , false);

        // todo , if first player is an ai , do the first move Onced
        setGameScreenText(view.getLabels(),startingX,startingY);
        view.start(stage);
    }


    /**
    * set the mouse reaction to all the lines on the board
     * used to pass data about the line which was clicked to the model
     */
    public void setLineButtons(Line[][] lines , Boolean isHorizontal) {
        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                lines[i][j].setStroke(Color.TRANSPARENT);
                lines[i][j].setStrokeWidth(5);


                // calc where the line came from
                // we have 2 diffrent matrixes to choose from , we need some
                // rules so we can calcualte the line position in the matrix in the model
                int finalI = i;
                int finalJ = j;
                if (isHorizontal){
                    finalI = finalI *2;
                }
                else{
                    finalI = j;
                    finalJ = i;
                    finalI = finalI *2 + 1;
                }
                int finalJ1 = finalJ;
                int finalI1 = finalI;

                // setting the events for the line
                lines[i][j].setOnMouseClicked((mouseEvent -> {
                    Line clickedLine = (Line) mouseEvent.getSource();
                    model.checkAndExecuteMove(clickedLine);
                    updateScores(view.getLabels(),model.getFirst().getScore(),model.getSecond().getScore() , finalI1, finalJ1);
                }));
                lines[i][j].setOnMouseEntered((mouseEvent -> {
                    Line hoveredLine = (Line) mouseEvent.getSource();
                    if (hoveredLine.getStroke() != Color.RED && hoveredLine.getStroke() != Color.BLUE) {
                        hoveredLine.setStroke(Color.GREEN);
                    }
                }));
                lines[i][j].setOnMouseExited((mouseEvent -> {
                    Line hoveredLine = (Line) mouseEvent.getSource();
                    if (hoveredLine.getStroke() != Color.RED && hoveredLine.getStroke() != Color.BLUE) {
                        hoveredLine.setStroke(Color.TRANSPARENT);
                    }
                }));
            }
        }

    }

    /**
     * the function which updates the lables on the screen
     */
    public void setGameScreenText(Label[] labels, double startingX, double startingY) {
        // setting 3 new labels
        // 0 for current player in the game , 1-2 for the players name
        labels[0] = new Label();
        labels[1] = new Label();
        labels[2] = new Label();


        // setting pos and style for the first , second and third label
        labels[0].setText("" + view.getStringCurrentTurn());
        labels[0].setLayoutX(startingX-labels[0].getWidth()/3 - 25);
        labels[0].setLayoutY(startingY-startingY/2 - 50);
        labels[0].setAlignment(Pos.TOP_CENTER);
        labels[0].setStyle("-fx-font-size: 70px;-fx-font-family: 'Bell MT'");
        labels[0].setTextFill(Color.BLACK);

        labels[1].setText(view.getStringScorePlayer1() + " " +model.getFirst().getScore());
        labels[1].setLayoutX(startingX-labels[1].getWidth()/3);
        labels[1].setLayoutY(startingY-startingY/2+ 25);
        labels[1].setAlignment(Pos.TOP_CENTER);
        labels[1].setStyle("-fx-font-size: 40px;-fx-font-family: 'Bell MT'");
        labels[1].setTextFill(Color.BLUE);


        labels[2].setText(view.getStringScorePlayer2()+ " " + model.getSecond().getScore());
        labels[2].setLayoutX(startingX-labels[1].getWidth()/3);
        labels[2].setLayoutY(startingY-startingY/2 + 75);
        labels[2].setAlignment(Pos.TOP_CENTER);
        labels[2].setStyle("-fx-font-size: 40px;-fx-font-family: 'Bell MT'");
        labels[2].setTextFill(Color.RED);
    }

    /**
     * function which updates the scores on the screen
     * also the function which use the ai
     * @param labels - to be able to update them
     * @param player1Score - current p1 score
     * @param player2Score - current p2 score
     * @param i row of line clicked
     * @param j col of line clicked
     */
    public void updateScores(Label[] labels, int player1Score, int player2Score , int i , int j) {

        //set new line in the model
        this.model.nodeBoard.SetNewLine(i,j);
        //print current state of nodeBoard (for debugging)
        this.model.nodeBoard.printer();
        // set move counter
        this.model.setMovesCounter();


        // update the scores on the screen
        labels[1].setText(view.getStringScorePlayer1() + " " + player1Score);
        labels[2].setText(view.getStringScorePlayer2()+ " " + player2Score);
        // check if the game is over
        if (!model.gameStatus()) {
            Pair<Integer,String> results = model.getGameWinner();
            if (results.getKey() == 0) {
                labels[0].setText(results.getValue() + " Won");
            }
            else {
                labels[0].setText("its a tie");
            }
        }
        else {
            System.out.println("was clicked in line and row :"  + i + "" + j);
            labels[0].setText(model.getCurrent().getName() + view.getStringCurrentTurn());

            // if its ai turn get the line from the ai algorithm
            if (model.getCurrent().isAi()){
                //get Line to turn will be using eval
                //CustomLine myCustomLine = model.getLineToTurn();

                //get time
                Instant start = Instant.now();

                //get memory
                final Runtime runtime = Runtime.getRuntime();
                long startMem , endMem;
                runtime.gc();
                startMem = runtime.freeMemory();


                // get the move for the ai
                CustomLine myCustomLine = model.AiBestMove();


                endMem = runtime.freeMemory();
               Instant end = Instant.now();

                //print ms and mem
               System.out.println("the time it took to calc the move in ms : " + (end.toEpochMilli() - start.toEpochMilli()));
               System.out.println((startMem - endMem)/(1024*1024) + "Mb were used");

                //CustomLine myCustomLine = model.miniMax();


                // get the line on screen from the ai
                Line l = myCustomLine.getCustomLine();


                // get the pos of the line to laeter put in the nodeBoard
                int pos1 = myCustomLine.getPos1();
                int pos2 = myCustomLine.getPos2();

                model.checkAndExecuteMove(l);
                updateScores(view.getLabels(),model.getFirst().getScore(),model.getSecond().getScore() , pos1 ,pos2);
            }
        }
    }
}