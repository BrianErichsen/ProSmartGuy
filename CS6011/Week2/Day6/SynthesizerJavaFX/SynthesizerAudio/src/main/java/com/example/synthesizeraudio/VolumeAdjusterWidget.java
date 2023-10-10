//package com.example.synthesizeraudio;
//
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.layout.AnchorPane;
//
//import java.awt.event.MouseEvent;
//
//public class VolumeAdjusterWidget extends AudioComponentWidget {
//    private final Label volumeLabel_;
//    VolumeAdjusterWidget(AudioComponent ac, AnchorPane parent) {
//        //Calls for the constructor of the base class
//        super(ac, parent);
//
//        //removes unnecessary boxes for this widget
//        baseLayout.getChildren().remove(rightSide);
//        baseLayout.getChildren().remove(leftSide);
//
//        //Sets the initial position of this widget
//        this.setLayoutX(400);
//        this.setLayoutY(500);
//
//        //Creates specific label for this widget
//        volumeLabel_ = new Label("Volume: " + getFormattedVolume());
//        //Sets style of the label
//        volumeLabel_.setStyle("fx-font-size: 14px; -fx-text-fill: #333");//Color and Size
//        volumeLabel_.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");//Background and padding
//        volumeLabel_.setStyle("-fx-effect: dropshadow(gaussian, #333, 5, 0, 0, 1)");//Text Shadow
//        //Adding the volume label into the leftSide VBox of this widget
//        baseLayout.getChildren().add(volumeLabel_);
//    }
//    private void setVolume(MouseEvent e, Slider volumeSlider, Label volumeLabel_) {
//        AudioComponent ac_ = getAudioComponent();
//        //Gets the value of the volume slider
//        float sliderValue = (float) volumeSlider.getValue();
//        volumeLabel_.setText("Volume: " + getFormattedVolume());
//        ((VolumeAdjuster) ac_).getVolume((int) sliderValue);
//    }
//    //    private void setFrequency(MouseEvent e, Slider freqSlider, Label freqLabel) {
//    //        ((SineWave)ac_).setFrequency(freqSlider.getValue());
//    //        int val = (int) freqSlider.getValue();
//    //        freqLabel.setText("SineWave " + val + " Hz");
//    //    }
//    private String getFormattedVolume() {
//        float sliderValue = (float) slider_.getValue();
//        //Returns the volume value to 2 decimal points as a string
//        return String.format("%.2f", sliderValue);
//    }
//}
