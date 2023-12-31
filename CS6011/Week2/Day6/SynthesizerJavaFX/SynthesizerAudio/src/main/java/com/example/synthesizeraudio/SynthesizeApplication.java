package com.example.synthesizeraudio;

//Created by Brian Erichsen Fagundes - Synthesizer App - October 2023;
//import com.example.SynthesizerAudio.AudioListener;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SynthesizeApplication extends Application {
//    private static VolumeAdjusterWidget acw;
    //Using array list to hold all the specific notes together
    private List<MusicalNote> notes = new ArrayList<>();
    //Declaring specific musical notes to be used on the GUI
    private MusicalNote noteA, noteB, noteC, noteD, noteE, noteF, noteG;
    AnchorPane mainCenter;
    private HBox bottomPanel = new HBox();
    //Declares what is the speaker to be connected
    public static Circle speaker;
    //Array list that takes all widgets
    public static ArrayList<AudioComponentWidget> widgets_ = new ArrayList<>();
    //Array list that takes all the connected widgets; the ones that are making sound
    public static ArrayList<AudioComponentWidget> Connected_widgets_ = new ArrayList<>();
    public static ArrayList<AudioComponentWidgetBase> mixers_widgets = new ArrayList<>();
    private SineWave sineWave;
    public  Mixer allInput;
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane mainLayout = new BorderPane();
        //Creates each individual note based on their sineWave frequency
        noteA = new MusicalNote("A", 440.0, KeyCode.A);
        noteB = new MusicalNote("B", 493.88, KeyCode.S);
        noteC = new MusicalNote("C", 523.25, KeyCode.D);
        noteD = new MusicalNote("D", 587.33, KeyCode.F);
        noteE = new MusicalNote("E", 659.25, KeyCode.G);
        noteF = new MusicalNote("F", 698.46, KeyCode.H);
        noteG = new MusicalNote("G", 783.99, KeyCode.J);
        //Adds all the notes into the specified array list for all notes
        notes.add(noteA);
        notes.add(noteB);
        notes.add(noteC);
        notes.add(noteD);
        notes.add(noteE);
        notes.add(noteF);
        notes.add(noteG);

        //Right Panel
        VBox rightPanel = new VBox();

        //Style of the panel
        rightPanel.setStyle("-fx-background-color: lightblue");
        rightPanel.setPadding(new Insets(4));

        //SineWave Buttom
        Button sinewaveBtn = new Button("SineWave");
        //Style
        sinewaveBtn.setStyle("fx-base: coral");
        //Does the intented action when buttom is clicked
        sinewaveBtn.setOnAction(e->createComponent(e));
        rightPanel.getChildren().add(sinewaveBtn);
        //Mixer widget button
        Button mixerButton = new Button("Mixer");
        mixerButton.setStyle("-fx-base: coral ");
        mixerButton.setOnAction(e -> createMixer(e));
        rightPanel.getChildren().add(mixerButton);

        //Center Panel which contains the specified Speaker
        mainCenter = new AnchorPane();
        //Sets style
        mainCenter.setStyle("-fx-background-color: #f1e6be");
        //Creates a circle to represent the speaker and declares speaker as well
        speaker = new Circle(400, 200, 15);
        Circle speaker = new Circle(400,200,15);
        speaker.setFill(Color.DARKBLUE);
        mainCenter.getChildren().add(speaker);
        mainLayout.setCenter(mainCenter);
        mainLayout.setRight(rightPanel);

//        Bottom Panel
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
                noteCButton, noteDButton, noteEButton, noteFButton, noteGButton);


        /*Specifies Border Pane */
        Scene scene = new Scene(mainLayout, 600, 400);
        stage.setTitle("Sound Maker");
        stage.setScene(scene);
        //Uses specific keys and triggers the events to occur when the keys are being hit
        scene.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            //Plays specific note that is attached to that specific note
            switch (key) {
                case A:
                    playNote(noteA);
                    break;
                case S:
                    playNote(noteB); break;
                case D:
                    playNote(noteC); break;
                case F:
                    playNote(noteD); break;
                case G:
                    playNote(noteE); break;
                case H:
                    playNote(noteF); break;
                case J:
                    playNote(noteG); break;
            }
            //Triggers the even to actually play the audio
        playAudio(event);
        });
        stage.show();
    }

    /*-------------------------------------------------------------------
    Beginning of Methods                ------------------               -*/


    private void playAudio(ActionEvent e) {
        Clip c = null;
        try {
            //Using JavaFX library get clip to get Clip c to actually play intended audio
            c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

            for (AudioComponentWidget w : Connected_widgets_) {
                AudioComponent ac = w.ac_;
                VolumeAdjuster instanceVolume = (VolumeAdjuster) w.volume_;
                if (instanceVolume instanceof VolumeAdjuster) {
                    //sliderValue sees the slider value from the SineWaves values
                    double sliderValue =  w.slider_.getValue();
                    (instanceVolume).volumeProperty().set(sliderValue);
                    System.out.println(sliderValue);
                    //mixerVolumeSlider sees the slider value from the mix component
                }
                if (AudioComponentWidgetBase.isConnectedToSpeaker) {
                    for (AudioComponentWidgetBase k : mixers_widgets){
                        allInput.connectInput(w.volume_);
                        VolumeAdjuster mixerInstanceVolume = (VolumeAdjuster) k.volume_;
                        double mixerVolumeSliderValue = k.slider_.getValue();
                        (mixerInstanceVolume).volumeProperty().set(mixerVolumeSliderValue);
                        System.out.println(mixerVolumeSliderValue);
                    }
                }
            }
            //AudioClip clip gets the AudioClip from the mixer while c holds AudioClip from the library
            if (allInput != null) {
                AudioClip clip = allInput.getClip();
                c.open(format16, clip.getData(), 0, clip.getData().length);
                //Starts playing the sound
                c.start();
                AudioListener listener = new AudioListener(c);
                //replaces the while loop for playing
                c.addLineListener(listener);
            }
        } catch (LineUnavailableException k) {
            System.out.println(k.getMessage());
        }
    }
    //Using the method overload so that we may also hear the notes with only hitting the keys
    private void playAudio(KeyEvent e) {
        try {
            Clip c = AudioSystem.getClip();
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);
//            byte[] data = Connected_widgets_.get(0).ac_.getClip().getData();

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
    //Helper method that helps create any note as a button
    private Button createNoteButton(String label, MusicalNote note) {
        Button button = new Button(label);
        //Calls for the playNote method any time the button is clicked
        //which can be played with the Play button is clicked
        button.setOnAction(e -> playNote(note));
        return button;
    }
    private void playNote (MusicalNote note) {
        //creates new SineWave that has the frequency of the specified note
        AudioComponent noteComponnet = new SineWave(note.getFrequency());
        //Creates the new AudioComponentWidget and adds it to Connected_widgets
        AudioComponentWidget a = new AudioComponentWidget(noteComponnet, null, mainCenter);
        Connected_widgets_.add(a);
        //Sets a time limit for the note to be connected into Connected_widgets
        Duration duration = Duration.seconds(1);// Up to 1 seconds
        Timeline timeline = new Timeline(new KeyFrame(duration, e -> {
            Connected_widgets_.remove(a);//removes note input after 1 seconds
        }));
                timeline.setCycleCount(1);//1 so doesn't loop more times
                timeline.play();
    }
    //Creates new Widget
    private void createComponent(ActionEvent e) {
        sineWave = new SineWave(200);
        VolumeAdjuster adjustedSineWave = new VolumeAdjuster(sineWave, 1.0); //Here if you change it
        // it does alter the ouput volume
        AudioComponentWidget acw = new AudioComponentWidget(sineWave, adjustedSineWave, mainCenter);
        //Creates as well a VolumeAdjuster component where it's initial volume is 1.0
        //Sets the initial position for (x and y) for the widget
        acw.setLayoutX(widgets_.size() * 50);
        acw.setLayoutY(widgets_.size() * 50);
        //Adds it GUI
        mainCenter.getChildren().add(acw);
        //Connects new created Widget into the array list of all current widgets
        widgets_.add(acw);
    }
    //Creates new instace of mixer where the other widgets can be connected into
    private void createMixer(ActionEvent e) {
        allInput = new Mixer();
        VolumeAdjuster adjustedMix = new VolumeAdjuster(allInput, 1.0);
        AudioComponentWidgetBase acwb = new AudioComponentWidgetBase(allInput, adjustedMix, mainCenter );
        acwb.setLayoutX(widgets_.size() * 50);
        acwb.setLayoutY(widgets_.size() * 50);
        mainCenter.getChildren().add(acwb);
        widgets_.add(acwb);
    }
    public static void main(String[] args) {
        launch();
    }
}