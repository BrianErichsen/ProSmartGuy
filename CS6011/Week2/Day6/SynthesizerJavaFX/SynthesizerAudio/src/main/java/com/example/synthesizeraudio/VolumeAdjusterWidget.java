//package com.example.synthesizeraudio;
//
//import javafx.scene.control.Label;
//import javafx.scene.control.Slider;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//
//
//public class VolumeAdjusterWidget extends AudioComponentWidget {
//    private final Slider volumeSlider;
//    private final Label volumeLabel;
//
//    VolumeAdjusterWidget(AudioComponent ac, AnchorPane parent) {
//        //Calls for the constructor of the base class
//        super(ac, parent);
//
//        //Removes the freqLabel and freqSlider from the leftSide VBox
//        leftSide.getChildren().remove(freqLabel);
//        leftSide.getChildren().remove(freqSlider);
//
//        //Constructs the volumeLabel
//        volumeLabel = new Label("Volume");
//        volumeLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");
//
//        //Constructs the volumeSlider
//        slider_ = new Slider(0.0f, 10f, 1);
//        volumeSlider = slider_;
//        leftSide.getChildren().add(volumeLabel);
//        leftSide.getChildren().add(freqSlider);
//        //Move widget as mouse is pressed is already handled by parent
//
//        //Adds the slider that controls the volume
//        //Move the widget as mouse is pressed
//        leftSide.setOnMousePressed(e ->moveWidget(e));
//        leftSide.setOnMouseDragged(e-> getPosInf(e));
//
//        //Adds the slider that constrols the volume
//        volumeSlider.setOnMouseDragged(e -> setVolume(e));
//        // not the right side because right side is already done by parent
//        volumeSlider.setOnMouseDragged(this::setVolume);
//
////        //Sets where the initial position is
//        this.setLayoutX(50.0);
//        this.setLayoutY(50.0);
//    }
//    private void setVolume(MouseEvent e) {
//        AudioComponent ac = getAudioComponent();
//        float sliderValue = (float) volumeSlider.getValue();
//        ((VolumeAdjuster) ac).volumeProperty().set((int) sliderValue);
//    }
//    private String getFormattedVolume() {
//        double sliderValue = (double) volumeSlider.getValue();
//        //Returns the volume value to 2 decimal points as a string
//        return String.format("%.2f", sliderValue);
//    }
//}
