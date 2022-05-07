package ru.nsu.fit.oop.game.exception.model;

import ru.nsu.fit.oop.game.model.Model;

public class ModelException  extends Exception{
    public ModelException() {
        super("model exception");
    }

    public ModelException(String msg) {
        super(msg);
    }

    public ModelException(String msg,Throwable cause) {
        super(msg,cause);
    }
}
