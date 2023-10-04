import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) {
        //Creates an audioFormat matching the desired audio format
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

        //Create a SineWave instance with desired frequency
        AudioComponent gen = new SineWave(440);
        AudioComponent volumeAdjuster = new VolumeAdjuster(gen, 0.1);
        AudioClip clip = gen.getClip();

        try {
            //Create a clip for audio playback
            Clip c = AudioSystem.getClip();
            //Open the Clip with specified format and audio data
            c.open(format16, volumeAdjuster.getClip().getData(), 0, volumeAdjuster.getClip().getData().length);

            //Start playing the clip
            System.out.println("About to play ...");
            c.start();
            c.loop(2);

            //Wait for the sound to finish Makes sure you don't quit before the sound plays
        while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
            //Do nothing while we wait for the note to play
            }
            //Close the clip when done
            System.out.println("Done");
            c.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
    }// End of main braket
}//End of class bracket
