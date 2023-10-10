package com.example.synthesizeraudio;
import javafx.scene.input.KeyCode;
public class MusicalNote {
    private String name;
    private double frequency;
    private KeyCode key;

    public MusicalNote(String name, double frequency, KeyCode key) {
        this.name = name;
        this.frequency = frequency;
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public double getFrequency() {
        return frequency;
    }
    public KeyCode getKeyCode() {
        return key;
    }
}
