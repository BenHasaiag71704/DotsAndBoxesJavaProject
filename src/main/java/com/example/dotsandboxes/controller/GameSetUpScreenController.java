package com.example.dotsandboxes.controller;

import com.example.dotsandboxes.model.classes.Board;
import com.example.dotsandboxes.model.classes.Game;
import com.example.dotsandboxes.view.GameScreen;
import com.example.dotsandboxes.view.GameSetUpScreen;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameSetUpScreenController {
    public GameSetUpScreen view;
    public Game model;
    public Stage stage;

    /**
     * constructor
     *  sets the style of the title and error text
     * @param model - the game model which contains the game data (board,  nodeboard etc)
     *  @param view - the screen view which contains the setUp screen
     * @param stage - the app window where the gui is displayed
     * @throws Exception
     */
    public GameSetUpScreenController(GameSetUpScreen view, Game model, Stage stage) throws Exception {
        this.model = model;
        this.stage = stage;
        this.view = view;

        setLabelStyle(view.getTitle(), view.getErrorText());
        setButtonStyle(view.getMoveToGame());
        view.getMoveToGame().setOnAction(actionEvent -> {
            try {
                checkDataFromSetUp();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        view.start(stage);
    }

    /**
     *  get the input from the user and check if it is valid
     *  if it is valid , set the data in the model and move to the game screen
     *  names must be at least 1 in lenght and board size must be between 2 and 9
     * @throws Exception
     */
    public void checkDataFromSetUp() throws Exception {
        int boardSize = view.getBoardSizeInput();
        String p1 = view.getP1Input();
        String p2 = view.getP2Input();
        if (boardSize > 1 && boardSize < 10 && p1.length() >= 1 && p2.length() >= 1 ) {
            model.getFirst().setName(p1);
            model.getSecond().setName(p2);
            model.setGameBoard(new Board(boardSize));
            GameScreen gameView = new GameScreen(model.getGameBoard(), model.getFirst(), model.getSecond(), view.getSceneX(), view.getSceneY());
            GameScreenController gameController = new GameScreenController(model, gameView, stage);
        }
    }

    /**
     * validate the input from the user
     */
    public boolean validateFields(TextField boardSize,TextField player1Name,TextField player2Name) {
        boolean boardSizeValidator = boardSize.getText().chars().allMatch(Character::isDigit);
        boolean p1NameValidator = player1Name.getText().isEmpty();
        boolean p2NameValidator = player2Name.getText().isEmpty();
        return boardSizeValidator && p1NameValidator && p2NameValidator;
    }
    public void setButtonStyle(Button moveToGame) {}

    /**
     * set the lables for the lables on screen
     * @param title
     * @param errorText
     */
    public void setLabelStyle(Label title, Label errorText) {
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 50px;-fx-font-family: 'Bell MT'");
        title.setTextFill(Color.BLACK);
        errorText.setVisible(false);
        errorText.setTextFill(Color.RED);
        errorText.setStyle("-fx-font-size: 20px;-fx-font-family: 'Bell MT'");
    }
}
