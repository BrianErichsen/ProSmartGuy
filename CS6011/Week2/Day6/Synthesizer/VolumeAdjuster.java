    //Have input components connected; creates a clip, clip from input and modi-
    //fy it
public class VolumeAdjuster implements AudioComponent {
    private AudioComponent input;
    private double volumeScale;

    public VolumeAdjuster(AudioComponent input, double volumeScale) {
        this.input = input;
        this.volumeScale = volumeScale;
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
    @Override
    public boolean hasInput() {
        //VolumeAdjuster accepts input
        return true;
    }
    @Override
    public void connectInput(AudioComponent input, int index) {
        this.input = input;
    }
}