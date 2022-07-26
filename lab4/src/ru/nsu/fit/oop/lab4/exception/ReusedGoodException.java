package ru.nsu.fit.oop.lab4.exception;

import ru.nsu.fit.oop.lab4.goods.Good;

public class ReusedGoodException extends Exception{

    public ReusedGoodException(Good good) {
        super("There was an attempt to reuse " + good.getName() + " #" + good.getId());
    }
}
