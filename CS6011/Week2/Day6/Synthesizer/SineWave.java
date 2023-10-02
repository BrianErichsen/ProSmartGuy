//Class that takes desired frequency(pitch) as constructor

public class SineWave implements AudioComponent {
    //Frequency of the sine wave in Hz
    private double frequency;
    //Constructor to set desired frequency
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
            double value = Math.sin(2 * Math.PI * frequency * i /
            sampleRate);
            //Scale the range of shorts
            int sampleValue = (int) (Short.MAX_VALUE * value);
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
}
