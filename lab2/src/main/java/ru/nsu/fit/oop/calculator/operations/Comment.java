package main.java.ru.nsu.fit.oop.calculator.operations;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationException;

import java.util.List;

public class Comment extends Operation {

    public Comment() {
        super();
    }

    @Override
    public void execute(Context context, List<String> args) throws OperationException {
        logger.entering(this.getClass().getName(),"execute");
        logger.exiting(this.getClass().getName(),"execute");
    }
}
