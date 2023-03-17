package com.example.dotsandboxes;

import com.example.dotsandboxes.controller.MainMenuScreenController;
import com.example.dotsandboxes.model.classes.BitBoard;
import com.example.dotsandboxes.model.classes.Game;
import com.example.dotsandboxes.view.MainMenuScreen;
import javafx.application.Application;
import javafx.stage.Stage;

import java.math.BigInteger;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        BitBoard b = new BitBoard(5);
//        b.set(0);
//        b.set(5);
//        b.set(6);
//        b.printLongAsBitMatrix();
//        boolean res = b.checkSetBitInRow(2);



        int sceneX = 800;
        int sceneY = 800;
        Game model = new Game();
        MainMenuScreen title = new MainMenuScreen(sceneX,sceneY);
        MainMenuScreenController titleController = new MainMenuScreenController(title,model,primaryStage);

    }





}



