package com.example.dotsandboxes.view;

import com.example.dotsandboxes.model.classes.Board;
import com.example.dotsandboxes.model.classes.Player;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GameScreen extends Application {
    private Board gameBoard;
    private int sceneX;
    private int sceneY;
    private StringProperty player1;
    private StringProperty player2;

    private IntegerProperty player1Score;
    private IntegerProperty player2Score;
    private StringProperty stringCurrentTurn;
    private StringProperty stringScorePlayer1;
    private StringProperty stringScorePlayer2;
    private Label[] labels;

    public GameScreen() {}
    public GameScreen(Board gameBoard,Player p1,Player p2, int sceneX, int sceneY) {
        this.gameBoard = gameBoard;
        this.sceneX = sceneX;
        this.sceneY = sceneY;
        this.labels = new Label[3]; // 0 - for current turn, 1 - for player 1 score, 2 - for player 2 score
        this.player1 = new SimpleStringProperty(p1.getName());
        this.player2 =  new SimpleStringProperty(p2.getName());;
        this.player1Score = new SimpleIntegerProperty(p1.getScore());
        this.player2Score = new SimpleIntegerProperty(p2.getScore());
        this.stringCurrentTurn = new SimpleStringProperty("'s to play");
        this.stringScorePlayer1 = new SimpleStringProperty(player1.getValue() + "'s score - ");
        this.stringScorePlayer2 = new SimpleStringProperty(player2.getValue() + "'s score - ");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, sceneX, sceneY);
        addChildrenToRoot(root,labels,gameBoard.getDots(),gameBoard.getHorizontalLines(),gameBoard.getVerticalLines());
        stage.setScene(scene);
        stage.show();
    }

    public void addChildrenToRoot(Group root, Label[] labels, Circle[][] dots, Line[][] horizontalLines, Line[][] verticalLines) {
        root.getChildren().addAll(labels);
        for(int i = 0; i<gameBoard.getBoardSize(); i++) {
            root.getChildren().addAll(dots[i]);
            root.getChildren().addAll(horizontalLines[i]);
            root.getChildren().addAll(verticalLines[i]);
        }
    }
    public Label[] getLabels() {return labels;}
    public int getSceneX() {return sceneX;}
    public int getSceneY() {return sceneY;}
    public String getStringScorePlayer1() {return stringScorePlayer1.get();}
    public String getStringCurrentTurn() {return stringCurrentTurn.get();}
    public String getStringScorePlayer2() {return stringScorePlayer2.get();}


}
