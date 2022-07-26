package ru.nsu.fit.oop.lab4.exception;

public class BadNumberOfTracks extends Exception{

    public BadNumberOfTracks(String trackName, int numberOfTracks) {
        super("Bad number of " + trackName + ".\n Should be at least 1 track, " +
                "number supplied by config equals to " + numberOfTracks);
    }
}
