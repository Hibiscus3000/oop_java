package ru.nsu.fit.oop.game.model.field;

public class GameField {

    public GameField(int x, int y) {
        this.sizeX = x;
        this.sizeY = y;
    }

    private int sizeX;
    private int sizeY;
    private int leftUpX;
    private int leftDownX;
    private int rightUpX;
    private int rightDownX;
    private Radix radix = new Radix();
}
