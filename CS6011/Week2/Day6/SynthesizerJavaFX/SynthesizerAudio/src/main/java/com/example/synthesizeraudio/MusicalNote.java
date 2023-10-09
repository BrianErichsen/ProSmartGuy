package com.example.synthesizeraudio;

public class MusicalNote {
    private String name;
    private double frequency;

    public MusicalNote(String name, double frequency) {
        this.name = name;
        this.frequency = frequency;
    }
    public String getName() {
        return name;
    }
    public double getFrequency() {
        return frequency;
    }
}
