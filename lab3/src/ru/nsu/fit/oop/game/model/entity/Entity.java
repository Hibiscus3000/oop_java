package ru.nsu.fit.oop.game.model.entity;

import ru.nsu.fit.oop.game.model.Radix;

public abstract class Entity {
    Radix radix;

    void setRadix(Radix radix) {
        this.radix = radix;
    }
}
