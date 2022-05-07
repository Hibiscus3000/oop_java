package ru.nsu.fit.oop.game.model.field;

import ru.nsu.fit.oop.game.model.subject.unit.Hero;

public class GameField {

    public GameField(int x, int y, Hero hero) {
        this.sizeX = x;
        this.sizeY = y;
        this.radix = new Radix(sizeX,sizeY,hero);
    }

    private int sizeX;
    private int sizeY;
    private int leftUpX;
    private int leftDownX;
    private int rightUpX;
    private int rightDownX;
    private Radix radix;
}
