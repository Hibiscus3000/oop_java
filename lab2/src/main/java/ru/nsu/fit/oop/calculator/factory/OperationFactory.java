package main.java.ru.nsu.fit.oop.calculator.factory;

import main.java.ru.nsu.fit.oop.calculator.exception.OperationNotSupported;
import main.java.ru.nsu.fit.oop.calculator.operations.Operation;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OperationFactory {

    private static final Logger logger = Logger.getLogger(OperationFactory.class.getName());

    private static Properties config = new Properties();
    private static volatile OperationFactory instance;

    public OperationFactory() throws IOException {
        logger.setLevel(Level.FINEST);
        var stream = OperationFactory.class.getResourceAsStream("config.properties");
        if (null == stream) {
            NullPointerException exception =  new NullPointerException("Wasn't able to open config file");
            logger.throwing(OperationFactory.class.getName(),"OperationFactory",exception);
            throw exception;
        }
        config.load(stream);
    }

    public static OperationFactory getInstance() throws IOException {
        if (null == instance)
            synchronized (OperationFactory.class) {
                if (null == instance) {
                    instance = new OperationFactory();
                    logger.log(Level.INFO,"Operation factory successfully initialized");
                }
            }
        return instance;
    }

    public Operation getOperation (String operationName) throws OperationNotSupported {
        if (!config.containsKey(operationName))
            throw new OperationNotSupported(operationName);
        try {
            var opClass = Class.forName(config.getProperty(operationName));
            var constructor = opClass.getDeclaredConstructor();
            if (!constructor.isAccessible())
                constructor.setAccessible(true);
            Operation operation = (Operation)constructor.newInstance();
            logger.log(Level.INFO,"Factory successfully made operation: \"" + operationName + "\"");
            return operation;
        }
        catch (Throwable cause) {
            OperationNotSupported exception =  new OperationNotSupported(operationName, cause);
            logger.throwing(OperationFactory.class.getName(),"getOperation",exception);
            throw exception;
        }
    }
}
