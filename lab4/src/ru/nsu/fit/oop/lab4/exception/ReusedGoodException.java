package ru.nsu.fit.oop.lab4.exception;

import ru.nsu.fit.oop.lab4.good.Good;

public class ReusedGoodException extends Exception{

    public ReusedGoodException(Good good) {
        super("There was an attempt to reuse " + good.getName() + " #" + good.getId());
    }
}
