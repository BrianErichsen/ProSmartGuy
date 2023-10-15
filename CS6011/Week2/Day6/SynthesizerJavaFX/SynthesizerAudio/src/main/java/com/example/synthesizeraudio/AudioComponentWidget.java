package com.example.synthesizeraudio;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class AudioComponentWidget extends Pane {
    AudioComponent ac_;
    AudioComponent volume_;
    AnchorPane parent_;
    double mouseXpos, mouseYpos, widgetXpos, widgetYPos;
    Line line_;
    Slider slider_;

    HBox baseLayout;
    VBox rightSide;
    VBox leftSide;
    public final Label freqLabel;
    public final Slider freqSlider;
    private final Slider volumeSlider;
    private final Label volumeLabel;

    //Constructor
    AudioComponentWidget (AudioComponent ac, VolumeAdjuster volume, AnchorPane parent) {
        ac_ = ac;
        volume_ = volume;
        parent_ = parent;
        line_ = null;
        //Sets the HBox baseLayout
        baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5");

        //Sets the VBox rightSide
        rightSide = new VBox();
        Button closeBtn = new Button("x");
        closeBtn.setOnAction(e->closeWidget(e));
        Circle output = new Circle(10);
        output.setFill(Color.AQUA);

        //handle drawing the line
        output.setOnMousePressed(e->startConn(e, output));
        output.setOnMouseDragged(e->moveConn(e, output));
        output.setOnMouseReleased(e->endConn(e, output));

        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(output);

        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);

        //left side; and adding the frequency Label and frequency Slider
        leftSide = new VBox();
        freqLabel = new Label("SineWave");
        freqLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");
        freqSlider = new Slider(200, 700, 350);
        leftSide.getChildren().add(freqLabel);
        leftSide.getChildren().add(freqSlider);

        //Adding the volume slider and volume label
        volumeLabel = new Label("Volume");
        volumeLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5px");
        //Constructs the volumeSlider
        slider_ = new Slider(0.0, 2.0, 0.2);
        volumeSlider = slider_;
        leftSide.getChildren().add(volumeLabel);
        leftSide.getChildren().add(volumeSlider);

        //Move widget as mouse is pressed
        leftSide.setOnMousePressed(e->moveWidget(e));
        leftSide.setOnMouseDragged(e->getPosInf(e));

        //Adds the slider that controls the fequency
        freqSlider.setOnMouseDragged(e->setFrequency(e, freqSlider, freqLabel));
        //Adds the setVolume method for the volume Slider
        volumeSlider.setOnMouseDragged(e -> setVolume(e, volumeSlider, volumeLabel));
        // not the right side because right side is already done by parent
//        volumeSlider.setOnMouseDragged(this::setVolume);
        baseLayout.getChildren().add(leftSide);
        baseLayout.getChildren().add(rightSide);
        this.getChildren().add(baseLayout);
        //Sets where the initial position is
        this.setLayoutX(50.0);
        this.setLayoutY(50.0);
    }

    private void endConn(MouseEvent e, Circle output) {

        Circle speaker = SynthesizeApplication.speaker;
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());

        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0) +
                Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0));

        if (distance < 10) {
//            SynthesizeApplication.Connected_widgets_.add(volume_);
            SynthesizeApplication.Connected_widgets_.add(this);//adds to others opened widgets
            System.out.println("Connected at this point");
            //Connected_widgets_ array
        } else {
            parent_.getChildren().remove(line_);
            line_ = null;
        }
    }
    private void moveConn(MouseEvent e, Circle output) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinY());
    }
    private void startConn(MouseEvent e, Circle output) {
//        if (line_ != null) {
//            parent_.getChildren().remove(line_);
//        }
        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds outputBounds = output.localToScene(output.getBoundsInLocal());

        line_ = new Line();
        line_.setStrokeWidth(5);

        line_.setStartX(outputBounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(outputBounds.getCenterY() - parentBounds.getMinY());

        line_.setEndX(e.getSceneX());
        line_.setStartY(e.getSceneY());

        parent_.getChildren().add(line_);
    }

    public void moveWidget(MouseEvent e) {
        double delX = e.getSceneX() - mouseXpos;
        double delY = e.getSceneY() - mouseYpos;

        this.relocate(delX + widgetXpos, delY + widgetYPos);
    }
    public void getPosInf(MouseEvent e) {
        mouseXpos = e.getSceneX();
        mouseYpos = e.getSceneY();
        widgetXpos = this.getLayoutX();
        widgetYPos = this.getLayoutY();
    }
    private void setFrequency(MouseEvent e, Slider freqSlider, Label freqLabel) {
        if (ac_ instanceof SineWave) {
            ((SineWave) ac_).setFrequency(freqSlider.getValue());
            int val = (int) freqSlider.getValue();
            freqLabel.setText("SineWave " + val + " Hz");
            //for the VolumeAdjusterWidget constructor
        }
    }
        private void setVolume(MouseEvent e, Slider volumeSlider, Label volumeLabel) {
        if (volume_ instanceof VolumeAdjuster) {
            double sliderValue = (double) volumeSlider.getValue();
            ((VolumeAdjuster) volume_).volumeProperty().set((int) sliderValue);
            volumeLabel.setText("volume: " + sliderValue);
        }
    }
    private void closeWidget(ActionEvent e) {
        parent_.getChildren().remove(this);
        SynthesizeApplication.widgets_.remove(this);
        SynthesizeApplication.Connected_widgets_.remove(this);
        parent_.getChildren().remove(line_);
    }
    public AudioComponent getAudioComponent() {
        return ac_;
    }
}