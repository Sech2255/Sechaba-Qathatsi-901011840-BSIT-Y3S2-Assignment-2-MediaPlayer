package com.example.mediaplayerjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;



public class MediaPlayerAssign extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        //Assigning variables to screen dimensions
        double screenWidth = screenSize.getWidth();
        double screenHeight = screenSize.getHeight();

        FXMLLoader fxmlLoader = new FXMLLoader(MediaPlayerAssign.class.getResource("media-player.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWidth/1.5, screenHeight/1.5);
        scene.getStylesheets().add("style.css");
        stage.setTitle("Media Player");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}