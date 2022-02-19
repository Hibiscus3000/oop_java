package ru.nsu.fit.oop.calculator.operations;

public abstract class Operation {
     public void numberOfArgsCheck(int expectedNumberOfArgs)
     {

     }
     abstract void execute(Context context, String[] args);
}
