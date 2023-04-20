package com.example.dotsandboxes.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class GameSetUpScreen extends Application {
    public Label title;
    public Label errorText;
    public Label player1Name;
    public Label player2Name;
    public Label boardSize;
    public TextField player1Field;
    public TextField player2Field;
    public TextField boardSizeField;
    public Button moveToGame;
    public int sceneX;
    public int sceneY;


    /**
     * constructor for the game setup screen
     * @param sceneX width of the screen
     * @param sceneY height of the screen
     */
    public GameSetUpScreen(int sceneX, int sceneY) {
        this.title = new Label("Settings");
        this.player1Name = new Label("Player 1 name ");
        this.errorText = new Label("invalid input");
        this.player2Name = new Label("Player 2 name ");
        this.boardSize = new Label("Board Size: ");
        this.player1Field = new TextField();
        this.player2Field = new TextField();
        this.boardSizeField = new TextField();
        this.moveToGame = new Button("Start");
        this.sceneX = sceneX;
        this.sceneY = sceneY;
    }

    /**
     * starts the game setup screen and adds all the children to the root
     * @param stage the stage to be shown
     * @throws Exception
     */
    public void start(Stage stage) throws Exception {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(25);
        root.setVgap(25);

        root.add(title,0,0,2,1);
        root.add(player1Name,0,1);
        root.add(player1Field,1,1);
        root.add(player2Name,0,2);
        root.add(player2Field,1,2);
        root.add(boardSize,0,3);
        root.add(boardSizeField,1,3);
        root.add(moveToGame,0,4);
        root.add(errorText,0,5);

        Scene scene = new Scene(root, sceneX, sceneY); // sets the scene
        stage.setScene(scene);
        stage.show();
    }

    public int getSceneX() {return sceneX;}
    public int getSceneY() {return sceneY;}

    /**
     * gets the board size input from the user and returns it
     * if the input is invalid it returns 0
     * @return
     */
    public int getBoardSizeInput() {
        int number;
        try {
            number = Integer.parseInt(boardSizeField.getText());
        }
        catch (NumberFormatException e) {
            number = 0;
        }
        return number;
    }
    public String getP1Input() {return player1Field.getText();}
    public String getP2Input() {return player2Field.getText();}
    public Label getErrorText() {return errorText;}
    public Label getBoardSize() {return boardSize;}
    public Label getPlayer1Name() {return player1Name;}
    public Label getPlayer2Name() {return player2Name;}
    public Label getTitle() {return title;}
    public TextField getBoardSizeField() {return boardSizeField;}
    public TextField getPlayer1Field() {return player1Field;}
    public TextField getPlayer2Field() {return player2Field;}

    public Button getMoveToGame() {return moveToGame;}
}
