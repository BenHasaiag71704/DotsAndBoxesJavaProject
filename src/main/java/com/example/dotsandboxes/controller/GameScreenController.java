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

import static com.example.dotsandboxes.model.enums.PlayerIndex.FIRST_PLAYER;


public class GameScreenController {
    private Game model;
    private GameScreen view;
    private int boardSize;

    public GameScreenController(Game model, GameScreen view, Stage stage) throws Exception {
        this.model = model;
        this.view = view;
        this.boardSize = model.getGameBoard().getBoardSize();


        this.model.nodeBoard = new NodeBoard(boardSize);

        //System.out.println(this.model.nodeBoard.NodesAll.size());


        double startingX =  (double) view.getSceneX()/2 - (double) (boardSize *50)/2;
        double startingY = (double) view.getSceneY()/2 - (double) (boardSize *50)/2;

        model.buildBoard(startingX,startingY);
        setLineButtons(model.getGameBoard().getHorizontalLines() , true);
        setLineButtons(model.getGameBoard().getVerticalLines() , false);

        // todo , if first player is an ai , do the first move Onced
        setLabels(view.getLabels(),startingX,startingY);
        view.start(stage);
    }



    public void setLineButtons(Line[][] lines , Boolean isHorizontal) {
        for (int i = 0; i< boardSize; i++) {
            for (int j = 0; j < boardSize - 1; j++) {
                lines[i][j].setStroke(Color.TRANSPARENT);
                lines[i][j].setStrokeWidth(5);

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

                lines[i][j].setOnMouseClicked((mouseEvent -> {
                        Line clickedLine = (Line) mouseEvent.getSource();
                        model.checkAndExecuteMove(clickedLine);
                        updateScores(view.getLabels(),model.getFirst().getScore(),model.getSecond().getScore() , finalI1, finalJ1);
                    }));
                    lines[i][j].setOnMouseEntered((mouseEvent -> {
                        Line hoveredLine = (Line) mouseEvent.getSource();
                        if (hoveredLine.getStroke() != Color.RED && hoveredLine.getStroke() != Color.BLUE) {
                            hoveredLine.setStroke(Color.YELLOW);
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
    public void setLabels(Label[] labels,double startingX,double startingY) {
        labels[0] = new Label();
        labels[0].setText("" + view.getStringCurrentTurn());
        labels[0].setLayoutX(startingX-labels[0].getWidth()/3);
        labels[0].setLayoutY(startingY-startingY/2);
        labels[0].setAlignment(Pos.CENTER);
        labels[0].setStyle("-fx-font-size: 50px;");
        labels[0].setTextFill(Color.BLACK);
        labels[1] = new Label();
        labels[1].setText(view.getStringScorePlayer1() + " " +model.getFirst().getScore());
        labels[1].setLayoutX(startingX-labels[1].getWidth()/3);
        labels[1].setLayoutY(startingY-startingY/2+ 75);
        labels[1].setAlignment(Pos.CENTER);
        labels[1].setStyle("-fx-font-size: 30px;");
        labels[1].setTextFill(Color.BLUE);
        labels[2] = new Label();
        labels[2].setText(view.getStringScorePlayer2()+ " " + model.getSecond().getScore());
        labels[2].setLayoutX(startingX-labels[1].getWidth()/3);
        labels[2].setLayoutY(startingY-startingY/2 + 125);
        labels[2].setAlignment(Pos.CENTER);
        labels[2].setStyle("-fx-font-size: 30px;");
        labels[2].setTextFill(Color.RED);
    }

    public void updateScores(Label[] labels, int player1Score, int player2Score , int i , int j) {

        this.model.nodeBoard.SetNewLine(i,j);
        this.model.nodeBoard.printer();



        labels[1].setText(view.getStringScorePlayer1() + " " + player1Score);
        labels[2].setText(view.getStringScorePlayer2()+ " " + player2Score);
        if (!model.gameStatus()) {
            Pair<Integer,String> results = model.getWinner();
            if (results.getKey() == 0) {
                labels[0].setText(results.getValue() + " Won");
            }
            else {
                labels[0].setText("It's A Tie");
            }
        }
        else {
            System.out.println("was clicked in line and row :"  + i + "" + j);
            labels[0].setText(model.getCurrent().getName() + view.getStringCurrentTurn());

            if (model.getCurrent().isAi()){
                //get Line to turn will be using eval

                System.out.print("bla");

                CustomLine myCustomLine;
                if (model.nodeBoard.NodeCountArrays[0].size() > 0 || model.nodeBoard.NodeCountArrays[3].size() != 0) {
                    myCustomLine = model.getLineToTurn();
                    System.out.println("brain");
                }
                else{
                    myCustomLine = model.getTurnFromMiniMax(model);
                    System.out.println("power");
                }




                Line l = myCustomLine.getCustomLine();


                int pos1 = myCustomLine.getPos1();
                int pos2 = myCustomLine.getPos2();

                model.checkAndExecuteMove(l);
                updateScores(view.getLabels(),model.getFirst().getScore(),model.getSecond().getScore() , pos1 ,pos2);
            }
        }
    }
}
