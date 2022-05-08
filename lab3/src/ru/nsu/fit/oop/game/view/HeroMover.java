package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class HeroMover extends JComponent {

    private GameObjectsInfo gameObjectsInfo;
    private InputMap imap;
    private ActionMap amap;

    public HeroMover(GameObjectsInfo gameObjectsInfo) {
        this.gameObjectsInfo = gameObjectsInfo;
        imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        amap = getActionMap();
        mapMoveKey("W", "move forward", 270);
        mapMoveKey("A", "move left", 180);
        mapMoveKey("S", "move backward", 90);
        mapMoveKey("D", "move right", 0);
    }

    private void mapMoveKey(String keyName, String actionName, double angle) {
        imap.put(KeyStroke.getKeyStroke(keyName), actionName);
        amap.put(actionName, new MoveAction(angle));
    }

    private class MoveAction extends AbstractAction {

        double angle;

        public MoveAction(double angle) {
            this.angle = angle;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            gameObjectsInfo.getHeroParams().move(Math.toRadians(angle));
        }
    }

}
