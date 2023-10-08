package com.example.synthesizeraudio;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Main {
    public static void main(String[] args) {
        // Creates an audioFormat matching the desired audio format
        AudioFormat format16 = new AudioFormat(44100, 16, 1, true, false);

        // //Create a SineWave instance with desired frequency
        AudioComponent sineWave1 = new SineWave(440); //for note A
        AudioComponent sineWave2 = new SineWave(659.26); //for note E : root and fifth
        AudioComponent sineWave3 = new SineWave(523.25); //for C so we have a Am
        
        //Apply volume adjustment to each sine wave
        AudioComponent adjustedSineWave1 = new VolumeAdjuster(sineWave1, 0.5);
        AudioComponent adjustedSineWave2 = new VolumeAdjuster(sineWave2, 0.5);
        AudioComponent adjustedSineWave3 = new VolumeAdjuster(sineWave2, 0.5);
        
        //Creates a mixer to add the two adjusted sine waves
        Mixer mixer = new Mixer();
        mixer.connectInput(adjustedSineWave1);
        mixer.connectInput(adjustedSineWave2);
        mixer.connectInput(adjustedSineWave3);

        LinearRamp ramp = new LinearRamp(50, 2000);
        VariableFrequencySineWave vfsineWave = new VariableFrequencySineWave();
        vfsineWave.connectInput(ramp);

        try {
            //Create a clip for audio playback
            Clip c = AudioSystem.getClip();
            //Open the Clip with specified format and audio data
            c.open(format16, vfsineWave.getClip().getData(), 0, vfsineWave.getClip().getData().length);

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

//        try {
//            //Create a clip for audio playback
//            Clip c = AudioSystem.getClip();
//            //Open the Clip with specified format and audio data
//            c.open(format16, mixer.getClip().getData(), 0, mixer.getClip().getData().length);
//
//            //Start playing the clip
//            System.out.println("About to play ...");
//            c.start();
//            c.loop(2);
//
//            //Wait for the sound to finish Makes sure you don't quit before the sound plays
//        while (c.getFramePosition() < AudioClip.TOTAL_SAMPLES || c.isActive() || c.isRunning()) {
//            //Do nothing while we wait for the note to play
//            }
//            //Close the clip when done
//            System.out.println("Done");
//            c.close();
//            } catch (LineUnavailableException e) {
//                e.printStackTrace();
//            }
    }// End of main braket
}//End of class bracket
