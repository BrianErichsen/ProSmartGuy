package com.example.synthesizeraudio;

public class VariableFrequencySineWave implements AudioComponent {
    private double phase = 0;
    private AudioComponent input;
    @Override
    public AudioClip getClip() {
        int maxValue = Short.MAX_VALUE;
        //Creates a new AudioClip to store the result (wave)
        AudioClip result = new AudioClip();
        //Gets the AudioClip from the connected input
        AudioClip inputClip = input.getClip();

        //Iterates through each sample of the result clip
        for (int i = 0; i < AudioClip.TOTAL_SAMPLES; i++) {
            phase += 2 * Math.PI * inputClip.getSample(i) / result.intSAMPLE_RATE;
            int sampleValue = (int) (maxValue * Math.sin(phase));
            //Sets the sampleValue into the result
            result.setSample(i, sampleValue);
        }
        return result;
    }
    @Override
    public boolean hasInput() {
        return true;
    }

    @Override
    public void connectInput(AudioComponent input) {
    //VariableFrequencySine should only have one input at time
        this.input = input;
    }
}
