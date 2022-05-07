package ru.nsu.fit.oop.game.exception.model;

import ru.nsu.fit.oop.game.model.Model;

public class ModelException  extends Exception{
    public ModelException() {
        super("Model exception.");
    }

    public ModelException(String msg) {
        super(msg);
    }

    public ModelException(Throwable cause) {super("Model exception.",cause);}

    public ModelException(String msg,Throwable cause) {
        super(msg,cause);
    }
}
