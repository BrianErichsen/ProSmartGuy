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
        //Creates a new empty clip to store the result
        AudioClip result = new AudioClip();
        //Iterates through all connected inputs and add their clips together
        for (AudioComponent input : inputs) {
            AudioClip inputClip = input.getClip();
            result = addClips(result, inputClip);
        }
        return result;
    }
    @Override
    public boolean hasInput() {
        //Mixer accepts input
        return true;
    }
    //Helper method to add the two audio clips together
    private AudioClip addClips(AudioClip clip1, AudioClip clip2) {
        //Finds the minimal length of the two clips
        int length = Math.min(clip1.getData().length, clip2.getData().length);
        //Creates new object result which will hold the audio samples
        AudioClip result = new AudioClip();
        for (int i = 0; i < length; i++) {
            //retrieves sample value at position i and stores in sample
            int sample1 = clip1.getSample(i);
            int sample2 = clip2.getSample(i);
            //Sums the two audio samples at the same position in time
            int sum = sample1 + sample2;

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
    public void connectInput(AudioComponent input, int index) {
        inputs.add(index, input);
    }
}
