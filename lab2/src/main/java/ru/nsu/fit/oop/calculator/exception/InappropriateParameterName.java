package main.java.ru.nsu.fit.oop.calculator.exception;

public class InappropriateParameterName extends OperationException{

    private String paramName;

    public InappropriateParameterName(String paramName) {
        super("Define");
        this.paramName = paramName;
    }

    @Override
    public String getMessage() {
        return "Inappropriate parameter name: \"" + paramName + "\"\n Parameter name shouldn't start" +
                " with a digit.";
    }
}
