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
    //Declaring specific musical notes to be used on the GUI
    private MusicalNote noteA, noteB, noteC, noteD, noteE, noteF, noteG;
    AnchorPane mainCenter;
    public static Circle speaker;
    public static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidget> Connected_widgets_ = new ArrayList<>();
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane mainLayout = new BorderPane();
        noteA = new MusicalNote("A", 440.0);
        noteB = new MusicalNote("B", 493.88);
        noteC = new MusicalNote("C", 523.25);
        noteD = new MusicalNote("D", 587.33);
        noteE = new MusicalNote("E", 659.25);
        noteF = new MusicalNote("F", 698.46);
        noteG = new MusicalNote("G", 783.99);

        //Right Panel
        VBox rightpanel = new VBox();

        //Style of the panel
        rightpanel.setStyle("-fx-background-color: lightblue");
        rightpanel.setPadding(new Insets(4));

        //SineWave Buttom
        Button sinewaveBtn = new Button("SineWave");
        //Style
        sinewaveBtn.setStyle("fx-base: coral");
        //Does the intented action when buttom is clicked
        sinewaveBtn.setOnAction(e->createComponent(e));
        rightpanel.getChildren().add(sinewaveBtn);

        //Center Panel
        mainCenter = new AnchorPane();
        //Sets style
        mainCenter.setStyle("-fx-background-color: #f1e6be");
        //Creates a circle to represent the speaker
        Circle speaker = new Circle(400,200,15);
        speaker.setFill(Color.DARKBLUE);
        mainCenter.getChildren().add(speaker);

        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightpanel);

//        Bottom Panel
        HBox bottomPanel = new HBox();
        //Sets the style of the bottom panel
        bottomPanel.setStyle("-fx-background-color: #00c4ff");
        //Creates the play button
        Button playBtn = new Button("Play");
        //Calls for the playAudio Method when button is clicked
        playBtn.setOnAction(e->playAudio(e));
        mainLayout.setBottom(bottomPanel);
        //Adding specific notes to be displayed
        Button noteAButton = createNoteButton("A", noteA);
        Button noteBButton = createNoteButton("B",noteB);
        Button noteCButton = createNoteButton("C",noteC);
        Button noteDButton = createNoteButton("D",noteD);
        Button noteEButton = createNoteButton("E",noteE);
        Button noteFButton = createNoteButton("F",noteF);
        Button noteGButton = createNoteButton("G",noteG);
        bottomPanel.getChildren().addAll(playBtn, noteAButton, noteBButton,
                noteCButton, noteDButton, noteEButton, noteFButton);

        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Sound Maker");
        stage.setScene(scene);
        stage.show();
    }
    private void playAudio(ActionEvent e) {
        try {
            Clip c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
            byte[] data = Connected_widgets_.get(0).ac_.getClip().getData();

            Mixer mixer = new Mixer();
            for (AudioComponentWidget w : Connected_widgets_) {
                AudioComponent ac = w.ac_;
                mixer.connectInput(ac);
            }
            AudioClip clip = mixer.getClip();
            c.open(format16, clip.getData(), 0, clip.getData().length);
            //Starts playing the sound
            c.start();
            AudioListener listener = new AudioListener(c);
            //replaces the while loop for playing
            c.addLineListener(listener);
        } catch (LineUnavailableException k){
            System.out.println(k.getMessage());
        }
    }
    private Button createNoteButton(String label, MusicalNote note) {
        Button button = new Button(label);
        button.setOnAction(e -> playNote(note));
        return button;
    }
    private void playNote (MusicalNote note) {
        AudioComponent noteComponnet = new SineWave(note.getFrequency());
        AudioComponentWidget a = new AudioComponentWidget(noteComponnet, mainCenter);
        Connected_widgets_.add(a);
    }
    private void createComponent(ActionEvent e) {
        AudioComponent sinewave = new SineWave(200);
        AudioComponentWidget acw = new AudioComponentWidget(sinewave, mainCenter );
        mainCenter.getChildren().add(acw);
        Connected_widgets_.add(acw);
        widgets_.add(acw);
        // create new object AudioComponentWidget and show it!
    }
    public static void main(String[] args) {
        launch();
    }
}