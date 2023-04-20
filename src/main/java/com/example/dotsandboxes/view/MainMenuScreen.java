package com.example.dotsandboxes.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenuScreen extends Application {
    public Label title;
    public Button humanVsHuman;
    public Button humanVsAi;
    public int sceneX;
    public int sceneY;

    /**
     * constructor for the main menu screen
     * @param sceneX width of the screen
     * @param sceneY height of the screen
     */
    public MainMenuScreen(int sceneX, int sceneY) {
        this.title = new Label("Dots And Boxes");
        this.humanVsHuman = new Button("Human Vs Human");
        this.humanVsAi = new Button("Human Vs Ai");
        this.sceneX = sceneX;
        this.sceneY = sceneY;
    }

    /**
     * starts the main menu screen and adds all the children to the root
     * @param stage the stage to be shown
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        VBox root = new VBox(title, humanVsHuman, humanVsAi);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        Scene scene = new Scene(root, sceneX, sceneY);
        stage.setScene(scene);
        stage.show();



    }

    public Label getTitle() {return title;}
    public Button getHumanVsHuman() {return humanVsHuman;}
    public Button getHumanVsAi() {return humanVsAi;}
    public int getSceneX() {return sceneX;}
    public int getSceneY() {return sceneY;}
}
