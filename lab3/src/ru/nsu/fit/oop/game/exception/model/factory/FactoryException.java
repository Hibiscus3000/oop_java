package ru.nsu.fit.oop.game.exception.model.factory;

import ru.nsu.fit.oop.game.exception.model.ModelException;

public class FactoryException extends ModelException {

        public FactoryException() {
            super("Factory exception.");
        }

        public FactoryException(String msg) {
            super(msg);
        }

        public FactoryException(String name,Throwable cause) {
            super("error in \"" + name + "\".",cause);
        }
}
