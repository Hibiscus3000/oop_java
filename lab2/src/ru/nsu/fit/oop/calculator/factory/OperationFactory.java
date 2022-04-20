package ru.nsu.fit.oop.calculator.factory;

import ru.nsu.fit.oop.calculator.operations.Operation;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    private static OperationFactory getInstance() throws IOException {
        if (null == instance)
            synchronized (OperationFactory.class) {
                if (null == instance) {
                    instance = new OperationFactory();
                }
            }
        return instance;
    }

    public Operation getOperation (String operationName) throws ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        var opClass = Class.forName(config.getProperty(operationName));
        var constructor = opClass.getDeclaredConstructor();
        return (Operation)constructor.newInstance();
    }
}
