package com.example.synthesizeraudio;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.awt.*;

import static java.awt.Color.*;

public class AudioComponentWidget extends Pane {
    AudioComponent ac_;
    AnchorPane parent_;
    //Constructor
    AudioComponentWidget (AudioComponent ac, AnchorPane parent) {
        ac_ = ac;
        parent_ = parent;

        HBox baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color: black; -fx-border-image-width: 5");

        VBox rightSide = new VBox();
        Button closeBtn = new Button("x");
        Circle output = new Circle(10);
//        output.setFill(Color.AQUA);

//        rightSide.getChildren().add(closeBtn);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        rightSide.setSpacing(5);


        Slider freqSlider = new Slider(200, 400, 300);
        baseLayout.getChildren().add(freqSlider);
        baseLayout.getChildren().add(rightSide);
        this.getChildren().add(baseLayout);

        this.setLayoutX(50.0);
        this.setLayoutY(50.0);
    }
    private void closeWidget(ActionEvent e) {
        parent_.getChildren().remove(this);
//        SynthesizeApplication.widgets_;
    }
}
