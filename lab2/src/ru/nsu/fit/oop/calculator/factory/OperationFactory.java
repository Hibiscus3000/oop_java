package ru.nsu.fit.oop.calculator.factory;

import ru.nsu.fit.oop.calculator.exception.OperationNotSupported;
import ru.nsu.fit.oop.calculator.operations.Operation;

import java.io.IOException;
import java.util.Properties;

public class OperationFactory {
    private static Properties config = new Properties();
    private static volatile OperationFactory instance;

    public OperationFactory() throws IOException {
        var stream = OperationFactory.class.getResourceAsStream("config.properties");
        if (null == stream)
            throw new NullPointerException("Wasn't able to open config file");
        config.load(stream);
    }

    public static OperationFactory getInstance() throws IOException {
        if (null == instance)
            synchronized (OperationFactory.class) {
                if (null == instance) {
                    instance = new OperationFactory();
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
            return (Operation)constructor.newInstance();
        }
        catch (Throwable cause) {
            throw new OperationNotSupported(operationName, cause);
        }
    }
}
