//Class that takes desired frequency(pitch) as constructor

public class SineWave implements AudioComponent {
    //Frequency of the sine wave in Hz
    private double frequency;
    //Constructor to set desired frequency
    public SineWave(double frequency) {
        this.frequency = frequency;
    }
    //Creates and fill an AudioClip with sample values
    public AudioClip geClip() {
        
        int numSamples = (int) (2.0 * AudioClip.intSAMPLE_RATE);
        AudioClip audioClip = new AudioClip();
        for (int i = 0; i < numSamples; i++) {
            double value = Math.sin(2 * Math.PI * frequency * i /
            intSAMPLE_RATE);
            int sampleValue = (int) (Short.MAX_VALUE * value);
            audioClip.setSample(i, sampleValue);
            }
            return audioClip;
    }
    public boolean hasInput () {
        return true;
    }
    public void connectInput(AudioComponent input) {

    }
}
