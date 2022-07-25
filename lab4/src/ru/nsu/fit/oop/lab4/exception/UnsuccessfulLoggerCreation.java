package ru.nsu.fit.oop.lab4.exception;

public class UnsuccessfulLoggerCreation extends Exception{

    public UnsuccessfulLoggerCreation(String entityName, Throwable t) {
        super("Wasn't able to create logger for " + entityName,t);
    }
}
