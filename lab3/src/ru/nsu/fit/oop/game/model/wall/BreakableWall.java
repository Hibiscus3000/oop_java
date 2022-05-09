package ru.nsu.fit.oop.game.model.wall;

public class BreakableWall extends Wall{

    private boolean inGame;
    private int armor;

    public BreakableWall(int startX, int startY, int endX, int endY, double thickness,int armor) {
        super(startX, startY, endX, endY, thickness);
        this.armor = armor;
    }

    public void takeDamage(int damage) {
        armor -= damage;
        if (armor <= 0)
            inGame = false;
    }

    public boolean getInGame() {
        return inGame;
    }
}
