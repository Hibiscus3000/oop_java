package ru.nsu.fit.oop.lab4;

public class Main {

    public static void main(String[] args) {
        try {
            Complex complex = new Complex();
            complex.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
