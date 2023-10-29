package com.example.minesweeper_neu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("minesweeper.fxml"));
        Controller controller = new Controller();
        //with the help of a fellow student
        stage.setWidth(controller.getWidthOfGrid());
        stage.setHeight(controller.getHeightOfGrid());
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Minesweeper");
        //Elements to prevent resizing and set the stage to the scene size with GridPane!
        stage.sizeToScene(); //https://www.java-forum.org/thema/wie-kann-ich-die-fenstergroesse-entsprechend-dem-inhalt-vergroessern-verkleinern.187675/
        stage.setResizable(false); //https://stackoverflow.com/questions/34809447/disable-maximize-button-and-resizing-window-in-javafx
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}