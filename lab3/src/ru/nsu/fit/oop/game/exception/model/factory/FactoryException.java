package ru.nsu.fit.oop.game.exception.model.factory;

import ru.nsu.fit.oop.game.exception.model.ModelException;

public class FactoryException extends ModelException {

        public FactoryException() {
            super("factory exception");
        }

        public FactoryException(String msg) {
            super(msg);
        }

        public FactoryException(String msg,Throwable cause) {
            super(msg,cause);
        }
}
