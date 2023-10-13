package com.example.synthesizeraudio;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


public class VolumeAdjusterWidget extends AudioComponentWidget {
    private final Slider volumeSlider;
    private final Label volumeLabel;

    VolumeAdjusterWidget(AudioComponent ac, AnchorPane parent) {
        //Calls for the constructor of the base class
        super(ac, parent);

        //removes unnecessary boxes for this widget but keeping right side for connection and x button
        baseLayout.getChildren().removeAll(leftSide);
//        baseLayout.getChildren().add(slider_);
        //Creates new left side of the widget
        VBox newLeftSide = new VBox();

//        customLayout.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");
        //Creates specific label for this widget
//        + getFormattedVolume()
        volumeLabel = new Label("Volume");
        //Sets style of the label
//        volumeLabel.setStyle("fx-font-size: 14px; -fx-text-fill: #333");//Color and Size
//        volumeLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");//Background and padding
        volumeLabel.setStyle("-fx-effect: dropshadow(gaussian, #333, 5, 0, 0, 1)");//Text Shadow
        //Adding the volume label into the leftSide VBox of this widget
//        baseLayout.getChildren().add(volumeLabel);
        //Creates a slider for volume
        volumeSlider = new Slider(0.0f, 10f, 1);
        newLeftSide.getChildren().add(volumeLabel);
        newLeftSide.getChildren().add(volumeSlider);

        //Move the widget as mouse is pressed
        newLeftSide.setOnMousePressed(e ->moveWidget(e));
        newLeftSide.setOnMouseDragged(e-> getPosInf(e));

        volumeSlider.setOnMouseDragged(e -> setVolume(e, volumeSlider, volumeLabel));
        baseLayout.getChildren().add(newLeftSide);
        // not the right side because right side is already done by parent

//        //Sets where the initial position is
        this.setLayoutX(50.0);
        this.setLayoutY(50.0);
    }
    private void setVolume(MouseEvent e, Slider volumeSlider, Label volumeLabel) {
        AudioComponent ac_ = getAudioComponent();
        //Gets the value of the volume slider
        float sliderValue = (float) volumeSlider.getValue();
        ((VolumeAdjuster) ac_).setVolume((int) sliderValue);
    }
    //    private void setFrequency(MouseEvent e, Slider freqSlider, Label freqLabel) {
    //        ((SineWave)ac_).setFrequency(freqSlider.getValue());
    //        int val = (int) freqSlider.getValue();
    //        freqLabel.setText("SineWave " + val + " Hz");
    //    }
    private String getFormattedVolume() {
        float sliderValue = (float) volumeSlider.getValue();
        //Returns the volume value to 2 decimal points as a string
        return String.format("%.2f", sliderValue);
    }
}
