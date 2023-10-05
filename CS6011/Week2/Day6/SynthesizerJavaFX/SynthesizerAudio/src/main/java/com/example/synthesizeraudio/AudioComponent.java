package com.example.synthesizeraudio;

public interface AudioComponent {
    //Get current sound produced by this component
    AudioClip getClip();
    //Checks if can connect something to this as an input
    boolean hasInput();
    //Connect to another device to this input
    void connectInput(AudioComponent input, int index);
}
