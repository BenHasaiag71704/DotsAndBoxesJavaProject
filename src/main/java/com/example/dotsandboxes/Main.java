package com.example.dotsandboxes;

import com.example.dotsandboxes.controller.MainMenuScreenController;
import com.example.dotsandboxes.model.classes.Game;
import com.example.dotsandboxes.view.MainMenuScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        int sceneX = 800;
        int sceneY = 800;
        Game model = new Game();
        MainMenuScreen title = new MainMenuScreen(sceneX,sceneY);
        MainMenuScreenController titleController = new MainMenuScreenController(title,model,primaryStage);
    }
}



