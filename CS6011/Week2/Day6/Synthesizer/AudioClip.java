//Created by Brian Erichsen Fagundes - on - 10/01/2023
import java.util.Arrays;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

class AudioClip {
    //const duration in seconds
    private static final double DURATION = 2.0;
    //Sample rate in samples per second
    private static final int SAMPLE_RATE = 44100;
    //Public Sample Rate to be used in different classes
    public int intSAMPLE_RATE = 44100;
    //Byte array
    byte[] data;
        public static void main(String[] args) {
            //Creates an audioFormat matching the desired audio format
            AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

            //Create a SineWave instance with desired frequency
            AudioComponent gen = new SineWave(200);
            AudioClip clip = gen.getClip();

            try {
                //Create a clip for audio playback
                Clip c = AudioSystem.getClip();
                //Open the Clip with specified format and audio data
                c.open(format16, clip.getData(), 0, clip.getData().length);

                //Start playing the clip
                System.out.println("About to play ...");
                c.start();
                c.loop(2);

                //Wait for the sound to finish
                while (c.isRunning()) {
                    //Do nothing while we wait for the note to play
                }
                //Close the clip when done
                System.out.println("Done");
                c.close();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
    }// End of Main bracket
    //--------------------------------------------------------------------------
    //Constructor
    public AudioClip() {
    //NumSamples holds total number of audio samples that audio clip can hold
    //(int) because it casts the value to a int
        int numSamples = (int) (DURATION * SAMPLE_RATE);
         //Initialize the data array with appropriate length
        //creates data array called data to store audio samples
        //Since audio sample is 2 bytes, then array size is 2 * numSamples
        data = new byte[2 * numSamples];
    }
    public int getSample(int index) {
        //Ensures that index is within bounds
        //Since each sample takes 2 bytes, then we divide data by 2 to get number
        //of valid samples
        if (index < 0 || index >= data.length / 2) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        //lower 8 bits represent the fine details of the amplitude
        //Upper 8 bits represent the coarse details of the amplitude
        /*When audio samples are stored in memory, they are split in two conse-
         * cutive bytes; to get the original 16-bit sample, we need to combine
         * the two bytes in correct order*/
        //Extracts the lower and upper 8 bits and combine them into an int
        byte lowerByte = data[2 * index];
        byte upperByte = data[2 * index + 1];
        //upperByte >> 8 ?
        //upperByte << 8 makes room for the lowerbyte; or operator with
        //masked lower byte returning combinied upper and lower bytes;
        return ((upperByte << 8) | (lowerByte & 0xFF));
    }
    public void setSample(int index, int value) {
        //Ensures that index is within bounds
        if (index < 0 || index >= data.length / 2) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        //Split the int into lower and upper bytes
        //sets lower bits to value and upper bits to 0
        byte lowerByte = (byte) (value & 0xFF);
        //Extracts upper 8 bits of value by 00000000 value & 11111111 retaining
        //only the upper 8 bits
        byte upperByte = (byte) ((value >> 8) & 0xFF);
        //Stores the bytes in little-endian order at specific index
        data[2 * index] = lowerByte;
        data[2 * index + 1] = upperByte;
    }
    public byte[] getData() {
        //Returns a copy of the data
        return Arrays.copyOf(data, data.length);
    }
}// AudioClip class end bracket