package com.example.synthesizeraudio;

import java.util.ArrayList;
import java.util.List;

public class Mixer implements AudioComponent {
    //The mixer contains a list of connected inputs
    private List<AudioComponent> inputs;

    public Mixer() {
        inputs = new ArrayList<>();
    }

    @Override
    public AudioClip getClip() {
        //Creates an array to hold the input clips
        AudioClip[] inputClips = new AudioClip[inputs.size()];

        //Iterates through all connected inputs and get their clips
        int index = 0;
        for (AudioComponent input : inputs) {
            inputClips[index++] = input.getClip();
        }
        //Uses the helper method addClips to combine all input clips
        AudioClip result = addClips(inputClips);
        
        return result;
    }
    @Override
    public boolean hasInput() {
        //Mixer accepts input //hack to set it to false actually
        return true;
    }
    //Helper method to add all clips together
    private AudioClip addClips(AudioClip... clips) {
        //Length represents total samples that i will be iterated over
        int length = 44100 * 2;
        //Creates new object result which will hold the audio samples
        //Length should be total samples instead of size
        AudioClip result = new AudioClip();
        for (int i = 0; i < length; i++) {
            int sum = 0;
            //Iterates through all it's data and sums sample
            for (AudioClip clip : clips) {
                int sample = clip.getSample(i);
                sum += sample;
            }

        //Clamps the result for a valid range of 16 bit audio sample
            if (sum > Short.MAX_VALUE) {
            sum = Short.MAX_VALUE;
            } else if (sum < Short.MIN_VALUE) {
            sum = Short.MIN_VALUE;
            }

            result.setSample(i, sum);
    }
    return result;
    }
    @Override
    //ConnectInput specifies an index for the input to add
    public void connectInput(AudioComponent input) {
        inputs.add(input);
    }
}
