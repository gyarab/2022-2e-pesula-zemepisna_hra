package com.example.novy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Text text = new Text("Hrací pole");
        text.setX (450);
        text.setY (50);
        VBox v = new VBox (text);
        Scene scene = new Scene (v);
        stage.setWidth (800);
        stage.setHeight (500);
        stage.setScene(scene);
        stage.show ();
    }

    public static void main(String[] args) {
        launch ();
    }
}