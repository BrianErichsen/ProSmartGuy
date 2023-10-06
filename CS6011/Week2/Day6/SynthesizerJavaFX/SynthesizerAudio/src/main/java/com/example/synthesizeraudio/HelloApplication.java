package com.example.synthesizeraudio;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.IOException;

//public class HelloApplication extends Application {
//    @Override
//    AnchorPane mainCenter;

//    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        BorderPane mainLayout = new BorderPane();
//        VBox rightpanel = new VBox();
//        rightpanel.setStyle("-fx-background-color: lightblue");
//        rightpanel.setPadding(new Insets(4);
//        Button sincewaveBtn = new Button("SineWave");
//        sincewaveBtn.setOnAction(e->createComponent(e));
//        rihtpanel.getChildren().add(sinewaveBtn);
//        //Center Panel
//        mainCenter.setStyle("-fx-background-color: #f1e6be");
//        Circle speaker = new Circle(400, 200, 15);
//        speaker.setFill(Color.DARKBLUE);
//        mainCenter.getChildren().add(speaker);
//
//        //Bottom Panel
//        HBox bottomPanel = new HBox();
//        bottomPanel.setStyle("-fx-background-color: #00c4");
//
//        mainLayout.setCenter(mainCenter);
//        mainLayout.setRight(rightpanel);
//
//        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();
//    }

//    private void playAudio(ActionEvent e) {
//        try {
//            Clip c = AudioSystem.getClip();
//            AudioFormat format16 = new AudioFormat(44100, 16, ...);
//            byte[] data = widgets_.get(0).ac_.getClip().getData();
//            c.open(format16, data, 0, data.length);
//            c.start();
//            AudioListener listener = new AudioListener(c);
//            c.addLineListener(listener);
//        } catch (LineUnavailableException) {
//            System.out.println("Done");
//        }
//    }
//    private void createComponent(ActionEvent e) {
//        AudioComponent sinewave = new SineWave((200));
//        AudioComponent acw = AudioComponentWidget(sinewave, mainCenter);
//        mainCenter.getChildren().add(acw);
//        widgets_.add(acw);
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }
//}