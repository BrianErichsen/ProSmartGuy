package com.example.synthesizeraudio;//Class that takes desired frequency(pitch) as constructor

public class SineWave implements AudioComponent {
    //Frequency of the sine wave in Hz
    private double frequency;
    //Constructor to set desired frequency
    //Frequency is how many cycles per second the sine wave completes
    public SineWave(double frequency) {
        this.frequency = frequency;
    }
    @Override
    //Creates and fill an AudioClip with sample values
    public AudioClip getClip() {
        int sampleRate = 44100;
        //Calculates total number of samples per second
        int numSamples = (int) (2.0 * sampleRate);
        //Creates a new AudioClip to store sine wave
        AudioClip audioClip = new AudioClip();
        //Generate sine wave samples and store them in the AudioClip
        for (int i = 0; i < numSamples; i++) {
            /*Calculates sine wave at current index; 2 * Math.Pi represents com-
             * plete cycle, converts from cycles per second Hertz to radians per
             * second; Divided by samples per second : Same formula from instruc-*/
            double sineWave = Math.sin(2 * Math.PI * frequency * i /
            sampleRate);
            //Scale the range of shorts
            //Short.MAX_VALUE to get ther range of 16 bit audio sample
            //Value ; the sine wave at specific point in time
            //(int) casts the sineWave from Short.M... * sineWave to a int
            int sampleValue = (int) (Short.MAX_VALUE * sineWave);
            //Stores the amplitude of sine wave at each sample point
            audioClip.setSample(i, sampleValue);
            }
            return audioClip;
    }
    @Override
    public boolean hasInput () {
        //SineWave does not accept input
        return false;
    }
    @Override
    public void connectInput(AudioComponent input) {
        //Sinewave
        assert false; //Sinewave does not accept inputs
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getFrequency() {
        return frequency;
    }
}
