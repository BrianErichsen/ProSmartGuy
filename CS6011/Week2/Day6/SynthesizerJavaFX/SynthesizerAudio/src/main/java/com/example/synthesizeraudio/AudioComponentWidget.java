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
    AnchorPane parent_;
    double mouseXpos, mouseYpos, widgetXpos, widgetYPos;
    Line line_;
    //Constructor
    AudioComponentWidget (AudioComponent ac, AnchorPane parent) {
        ac_ = ac;
        parent_ = parent;

        HBox baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5");

        VBox rightSide = new VBox();
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

        //left side
        VBox leftside = new VBox();
        Label freqLabel = new Label("SineWave");
        Slider freqSlider = new Slider(200, 1200, 400);
        leftside.getChildren().add(freqLabel);
        leftside.getChildren().add(freqSlider);
        leftside.setOnMousePressed(e->moveWidget(e));
        leftside.setOnMouseDragged(e->getPosInf(e));

        freqSlider.setOnMouseDragged(e->setFrequency(e, freqSlider, freqLabel));
        baseLayout.getChildren().add(leftside);
        baseLayout.getChildren().add(rightSide);
        this.getChildren().add(baseLayout);

        this.setLayoutX(50.0);
        this.setLayoutY(50.0);
    }

    private void endConn(MouseEvent e, Circle output) {

        Circle speaker = SynthesizeApplication.speaker;
        Bounds speakerBounds = speaker.localToScene(speaker.getBoundsInLocal());

        double distance = Math.sqrt(Math.pow(speakerBounds.getCenterX() - e.getSceneX(), 2.0) +
                Math.pow(speakerBounds.getCenterY() - e.getSceneY(), 2.0));

        if (distance < 10) {
            SynthesizeApplication.Connected_widgets_.add(this);//adds to others opened widgets
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
        if (line_ != null) {
            parent_.getChildren().remove(line_);
        }
        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds outputBounds = output.localToScene(output.getBoundsInLocal());

//        double startX = outputBounds.getCenterX() - parentBounds.getMinX();
//        double startY = outputBounds.getCenterY() - parentBounds.getMinY();
//
//        line_ = new Line(startX, startY, startX, startY);
        line_ = new Line();
        line_.setStrokeWidth(5);

        line_.setStartX(outputBounds.getCenterX() - parentBounds.getMinX());
        line_.setStartY(outputBounds.getCenterX() - parentBounds.getMinY());

        line_.setEndX(e.getSceneX());
        line_.setStartY(e.getSceneY());
//        output.setOnMouseDragged(event -> updateConnectingLine(event));

        parent_.getChildren().add(line_);
    }

    private void moveWidget(MouseEvent e) {
        double delX = e.getSceneX() - mouseXpos;
        double delY = e.getSceneY() - mouseYpos;

        this.relocate(delX + widgetXpos, delY + widgetYPos);
    }
    private void getPosInf(MouseEvent e) {
        mouseXpos = e.getSceneX();
        mouseYpos = e.getSceneY();
        widgetXpos = this.getLayoutX();
        widgetYPos = this.getLayoutY();
    }
    private void setFrequency(MouseEvent e, Slider freqSlider, Label freqLabel) {
        ((SineWave)ac_).setFrequency(freqSlider.getValue());
        int val = (int) freqSlider.getValue();
        freqLabel.setText("SineWave " + val + " Hz");
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