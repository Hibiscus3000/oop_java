package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.Model;
import ru.nsu.fit.oop.game.model.entity.game_object.unit.Hero;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

public class HeroController extends JComponent{

    Model model;
    InputMap imap;
    ActionMap amap;

    public HeroController(Model model) {
        this.model = model;
        imap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        amap = getActionMap();
        mapMoveKey("W","move forward",90);
        mapMoveKey("A","move left",180);
        mapMoveKey("S","move backward",270);
        mapMoveKey("D","move right",0);
    }

    private void mapMoveKey(String keyName, String actionName, double angle) {
        imap.put(KeyStroke.getKeyStroke(keyName),actionName);
        amap.put(actionName,new MoveAction(angle));
    }

    private class MoveAction extends AbstractAction {

        double angle;
        public MoveAction(double angle) {
            this.angle = angle;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            model.moveHero(Math.toRadians(angle));
        }
    }

    private class MouseHandler extends MouseAdapter {


    }

    private class MouseMotionHandler extends MouseMotionAdapter {

    }
}
