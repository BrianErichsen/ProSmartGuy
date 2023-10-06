package com.example.synthesizeraudio;


//import com.example.SynthesizerAudio.AudioListener;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import java.io.IOException;
import java.util.ArrayList;


public class SynthesizeApplication extends Application {
    AnchorPane mainCenter;
    public static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane mainLayout = new BorderPane();

        //Right Panel
        VBox rightpanel = new VBox();
        rightpanel.setStyle("-fx-background-color: lightblue");
        rightpanel.setPadding(new Insets(4));
        Button sinewaveBtn = new Button("SineWave");
        sinewaveBtn.setOnAction(e->createComponent(e));
        rightpanel.getChildren().add(sinewaveBtn);

        //Center Panel
        mainCenter = new AnchorPane();
        mainCenter.setStyle("-fx-background-color: #f1e6be");
        Circle speaker = new Circle(400,200,15);
        speaker.setFill(Color.DARKBLUE);
        mainCenter.getChildren().add(speaker);

        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightpanel);

//        Bottom Panel
        HBox bottomPanel = new HBox();
        bottomPanel.setStyle("-fx-background-color: #00c4ff");
        Button playBtn = new Button("Play");
        playBtn.setOnAction(e->playAudio(e));
        bottomPanel.getChildren().add(playBtn);
        mainLayout.setBottom(bottomPanel);


        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Sound Maker");
        stage.setScene(scene);
        stage.show();
    }
    private void playAudio(ActionEvent e) {
        try {
            Clip c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            byte[] data = widgets_.get(0).ac_.getClip().getData();
            c.open(format16, data, 0, data.length);
            //Starts playing the sound
            c.start();
            AudioListener listener = new AudioListener(c);
            //replaces the while loop for playing
            c.addLineListener(listener);
        } catch (LineUnavailableException k){
            System.out.println(k.getMessage());
        }
    }
    private void createComponent(ActionEvent e) {
        AudioComponent sinewave = new SineWave(200);
        AudioComponentWidget acw = new AudioComponentWidget(sinewave, mainCenter );
        mainCenter.getChildren().add(acw);
        widgets_.add(acw);
        // create new object AudioComponentWidget and show it!
    }
    public static void main(String[] args) {
        launch();
    }
}