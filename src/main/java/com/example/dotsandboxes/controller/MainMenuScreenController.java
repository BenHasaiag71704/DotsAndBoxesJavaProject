package com.example.dotsandboxes.controller;

import com.example.dotsandboxes.model.classes.Game;
import com.example.dotsandboxes.model.classes.Player;
import com.example.dotsandboxes.view.GameSetUpScreen;
import com.example.dotsandboxes.view.MainMenuScreen;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenuScreenController {
    public MainMenuScreen view;

    public Game model;
    public Stage stage;


    /**
     * constructor
     * @param view - the screen view which contains the title screen
     * @param model - the game model which contains the game data (board,  nodeboard etc)
     * @param stage - the app window where the gui is displayed
     * @throws Exception
     */
    public MainMenuScreenController(MainMenuScreen view, Game model, Stage stage) throws Exception {
        this.model = model;
        this.view = view;
        this.stage = stage;
        buttonSetOnAction(view.getHumanVsHuman(),view.getHumanVsAi());
        setStyles(view.getHumanVsHuman(),view.getHumanVsAi());
        setLabelStyle(view.getTitle());
        view.start(stage);
    }

    // button handlers
    public void handleHumanVsHuman() throws Exception {
        model.setGameType(0);
        model.setFirst(new Player(false));
        model.setSecond(new Player(false));
        moveToSettings();
    }
    public void handleHumanVsAi() throws Exception {
        model.setGameType(1);
        model.setFirst(new Player(false));
        model.setSecond(new Player(true));
        moveToSettings();
    }

    // helper methods to move to settings screen
    public void moveToSettings() throws Exception { // moves the app to the settings screen after a button was pressed
        GameSetUpScreen settingsView = new GameSetUpScreen(view.getSceneX(), view.getSceneY());
        GameSetUpScreenController settingsController = new GameSetUpScreenController(settingsView,model,stage);
    }

    // set the lables and buttons style
    public void setLabelStyle(Label title) {
        title.setAlignment(Pos.CENTER);
        title.setStyle("-fx-font-size: 75px;-fx-font-family: 'Bell MT'");
        title.setTextFill(Color.BLACK);
    }
    public void setStyles(Button humanVsHuman, Button humanVsAi) {
        humanVsHuman.setPadding(new Insets(10,20,10,20));
        humanVsAi.setPadding(new Insets(10,34,10,34));
    }

    /**
     * sets the on action for the buttons
     * @param humanVsHuman - the button for human vs human
     * @param humanVsAi - the button for human vs ai
     */
    public void buttonSetOnAction(Button humanVsHuman, Button humanVsAi) {
        humanVsHuman.setOnAction(buttonEvent -> {
            try {
                handleHumanVsHuman();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        humanVsAi.setOnAction(buttonEvent -> {
            try {
                handleHumanVsAi();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
