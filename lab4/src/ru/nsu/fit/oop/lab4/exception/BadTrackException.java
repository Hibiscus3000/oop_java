package ru.nsu.fit.oop.lab4.exception;

public class BadTrackException extends Exception{

    public BadTrackException(Throwable cause) {
        super("Unable to cast track.",cause);
    }
}
