package ru.nsu.fit.oop.lab4.exception;

public class UnknownGoodName extends Exception{

    public UnknownGoodName(String goodName) {
        super("Tried to find goods list with name " + goodName + " in goods train map.");
    }
}
