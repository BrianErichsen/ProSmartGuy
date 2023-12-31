package com.example.synthesizeraudio;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//Have input components connected; creates a clip, clip from input and modi-
    //fy it
public class VolumeAdjuster implements AudioComponent {
    private final DoubleProperty volumeProperty = new SimpleDoubleProperty();
    private AudioComponent input;
    private double volumeScale;

    public VolumeAdjuster(AudioComponent input, double volumeScale) {
        this.input = input;
        this.volumeScale = volumeScale;
        volumeProperty.set( volumeScale);
//        volumeProperty.addListener(((observable, oldValue, newValue) ->  {
//            volumeScale = newValue.doubleValue();
//            refreshAudio();
//        }));
    }
    @Override
    public AudioClip getClip() {
        //Gets the original audio
        AudioClip original = input.getClip();
        //Creates new audio clip to store adjusted audio
        AudioClip result = new AudioClip();
        //Gets the byte from original clip // Array of bytes
        byte[] originalData = original.getData();
        //Sets what is volumeScale in the new audio
        volumeScale = volumeProperty.get() / 2.0;
        
        //Adjust volume of each sample and store it in result clip
        for (int i = 0; i < originalData.length / 2; i++) {
            int sample = original.getSample(i);
            //Adjust the sample with volume scale
            double adjustedSample = (sample * volumeScale);

            //Caps the adjusted sample to valid range of 16 bits
            if (adjustedSample > Short.MAX_VALUE) {
                adjustedSample = Short.MAX_VALUE;
            } else if (adjustedSample < Short.MIN_VALUE) {
                adjustedSample = Short.MIN_VALUE;
            }
            //Sets adjustedSample in result clip
            result.setSample(i, (int) adjustedSample);
        }
        //Assigns the old original AudioClip to null to prevent memory leaks
//        original = null;
//        originalData = null;
        return result;
    }
    public DoubleProperty volumeProperty() {
        return volumeProperty;
    }
    @Override
    public boolean hasInput() {
        //VolumeAdjuster accepts input
        return true;
    }
    @Override
    public void connectInput(AudioComponent input) {
        this.input = input;
    }
    public void setVolume (double volume) {
        volumeScale = volume;
    }
}