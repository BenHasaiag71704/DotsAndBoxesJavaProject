package com.example.dotsandboxes.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenuScreen extends Application {
    private Label title;
    private Button humanVsHuman;
    private Button humanVsAi;
    private int sceneX;
    private int sceneY;

    public MainMenuScreen(int sceneX, int sceneY) {
        this.title = new Label("Dots And Boxes");
        this.humanVsHuman = new Button("Human Vs Human");
        this.humanVsAi = new Button("Human Vs Ai");
        this.sceneX = sceneX;
        this.sceneY = sceneY;
    }

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
