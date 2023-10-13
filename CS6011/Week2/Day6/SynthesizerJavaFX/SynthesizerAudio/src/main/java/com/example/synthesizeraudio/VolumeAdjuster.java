package com.example.synthesizeraudio;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//Have input components connected; creates a clip, clip from input and modi-
    //fy it
public class VolumeAdjuster implements AudioComponent {
    private final IntegerProperty volumeProperty = new SimpleIntegerProperty();
    private AudioComponent input;
    private double volumeScale;

    public VolumeAdjuster(AudioComponent input, double volumeScale) {
        this.input = input;
        this.volumeScale = volumeScale;
        volumeProperty.set((int) volumeScale);
        volumeProperty.addListener(((observable, oldValue, newValue) ->  {
            refreshAudio();
        }));
    }
    @Override
    public AudioClip getClip() {
        //Gets the original audio
        AudioClip original = input.getClip();
        //Creates new audio clip to store adjusted audio
        AudioClip result = new AudioClip();
        //Gets the byte from original clip // Array of bytes
        byte[] originalData = original.getData();
        
        //Adjust volume of each sample and store it in result clip
        for (int i = 0; i < originalData.length / 2; i++) {
            int sample = original.getSample(i);
            //Adjust the sample with volume scale
            int adjustedSample = (int) (sample * volumeScale);

            //Caps the adjusted sample to valid range of 16 bits
            if (adjustedSample > Short.MAX_VALUE) {
                adjustedSample = Short.MAX_VALUE;
            } else if (adjustedSample < Short.MIN_VALUE) {
                adjustedSample = Short.MIN_VALUE;
            }

            //Sets adjustedSample in result clip
            result.setSample(i, adjustedSample);
        }
        return result;
    }
    private void refreshAudio() {
        AudioClip inputAudio = input.getClip();
        double volumeScale = (double) volumeProperty.get();
        AudioClip adjustedAudio = adjustVolume(inputAudio, volumeScale);
    }

    private AudioClip adjustVolume(AudioClip inputAudio, double volumeScale) {
        //Makes sure that input audio is not null
        if (inputAudio == null) {
            return null;
        }
        //Gets the original audio
        AudioClip original = input.getClip();
        //Creates new audio clip to store adjusted audio
        AudioClip result = new AudioClip();
        //Gets the byte from original clip // Array of bytes
        byte[] originalData = original.getData();

        //Adjust volume of each sample and store it in result clip
        for (int i = 0; i < originalData.length / 2; i++) {
            int sample = original.getSample(i);
            //Adjust the sample with volume scale
            int adjustedSample = (int) (sample * volumeScale);

            //Caps the adjusted sample to valid range of 16 bits
            if (adjustedSample > Short.MAX_VALUE) {
                adjustedSample = Short.MAX_VALUE;
            } else if (adjustedSample < Short.MIN_VALUE) {
                adjustedSample = Short.MIN_VALUE;
            }

            //Sets adjustedSample in result clip
            result.setSample(i, adjustedSample);
        }
        return result;
    }

    public IntegerProperty volumeProperty() {
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