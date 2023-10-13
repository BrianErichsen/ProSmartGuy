package com.example.synthesizeraudio;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


public class VolumeAdjusterWidget extends AudioComponentWidget {
    private final Slider volumeSlider;
    private final Label volumeLabel;

    VolumeAdjusterWidget(AudioComponent ac, AnchorPane parent) {
        //Calls for the constructor of the base class
        super(ac, parent);

        //Removes the freqLabel and freqSlider from the leftSide VBox
        leftSide.getChildren().remove(freqLabel);
        leftSide.getChildren().remove(freqSlider);

        //Constructs the volumeLabel
        volumeLabel = new Label("Volume");
        volumeLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");

        //Constructs the volumeSlider
        volumeSlider = new Slider(0.0f, 10f, 1);
        leftSide.getChildren().add(volumeLabel);
        leftSide.getChildren().add(freqSlider);
        //Move widget as mouse is pressed is already handled by parent

        //Adds the slider that controls the volume
        //Move the widget as mouse is pressed
        leftSide.setOnMousePressed(e ->moveWidget(e));
        leftSide.setOnMouseDragged(e-> getPosInf(e));

        //Adds the slider that constrols the volume
        volumeSlider.setOnMouseDragged(e -> setVolume(e, volumeSlider, volumeLabel));
        // not the right side because right side is already done by parent

//        //Sets where the initial position is
        this.setLayoutX(50.0);
        this.setLayoutY(50.0);
    }
    private void setVolume(MouseEvent e, Slider volumeSlider, Label volumeLabel) {
        int val = (int) volumeSlider.getValue();
        volumeLabel.setText("Volume" + String.valueOf(val));
        ((VolumeAdjuster) ac_).setVolume(freqSlider.getValue());
    }

        /// This is what I think as well
//        AudioComponent ac_ = getAudioComponent();
//        //Gets the value of the volume slider
//        float sliderValue = (float) volumeSlider.getValue();
//        ((VolumeAdjuster) ac_).setVolume((int) sliderValue);

    //    private void setFrequency(MouseEvent e, Slider freqSlider, Label freqLabel) {
    //        ((SineWave)ac_).setFrequency(freqSlider.getValue());
    //        int val = (int) freqSlider.getValue();
    //        freqLabel.setText("SineWave " + val + " Hz");
    //    }
    private String getFormattedVolume() {
        double sliderValue = (double) volumeSlider.getValue();
        //Returns the volume value to 2 decimal points as a string
        return String.format("%.2f", sliderValue);
    }
}
