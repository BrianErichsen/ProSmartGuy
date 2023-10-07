package com.example.synthesizeraudio;

public class LinearRamp implements AudioComponent {
    private double start;
    private double stop;

    //Constructor
    public LinearRamp (double start, double stop) {
        this.start = start;
        this.stop = stop;
    }
    @Override
    public AudioClip getClip() {
        int numSamples = AudioClip.TOTAL_SAMPLES;
        //Created Double numSamples to make sure that I am dividing by a double for precision
        double DoubleNumSamples = numSamples;
        //Creates a new AudioClip where result will be stored
        AudioClip result = new AudioClip();
        //Iterates through each sample and calculates linear ramp value for each
        for (int i = 0; i < numSamples; i++) {
            double sample = (start * (numSamples - i) + stop * i) / DoubleNumSamples;
//            //Converts the sample value to a 16-bit int
//            int intSample = (int) (sample * Short.MAX_VALUE);
            //Sets sample value in the result AudioClip
            result.setSample(i, (int) sample);
        }
        return result;
    }
    @Override
    //Linear ramp does not have input but rather it is the input
    public boolean hasInput() {
        return false;
    }
    @Override
    public void connectInput(AudioComponent input) {
    assert false;//LinearRamp does accept inputs
    }
}
