package ru.nsu.fit.oop.game.view;

import ru.nsu.fit.oop.game.model.GameObjectsInfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.lang.System.exit;

public class GameInfo extends JComponent {

    private Font font = new Font("SansSerif", Font.BOLD, 14);
    private Font statisticsFont = new Font("SansSerif", Font.BOLD, 18);
    private BufferedImage heroPortrait;
    private BasicStroke borderStroke = new BasicStroke(6.0f);
    private BasicStroke basicStroke = new BasicStroke(2.0f);
    private GameObjectsInfo gameObjectsInfo;
    private int windowSizeY;

    public GameInfo(int windowSizeY) {
        this.windowSizeY = windowSizeY;
        this.gameObjectsInfo = gameObjectsInfo;
        setPreferredSize(new Dimension(300, windowSizeY));
        try {
            heroPortrait = ImageIO.read(getClass().getResourceAsStream("hero.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(borderStroke);
        g2d.setColor(new Color(0, 0, 130));
        g2d.drawLine(7, 7, 7, 163);
        g2d.drawLine(163, 163, 7, 163);
        g2d.drawLine(163, 7, 7, 7);
        g2d.drawLine(163, 163, 163, 7);
        g2d.drawImage(heroPortrait, 10, 10, 150, 150, this);
        drawCharacteristics(g2d);
    }

    public void drawCharacteristics(Graphics2D g2d) {
        g2d.setFont(statisticsFont);
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = statisticsFont.getStringBounds(gameObjectsInfo.getHero().getName(), context);
        g2d.setColor(Color.BLACK);
        g2d.drawString(gameObjectsInfo.getHero().getName(), 7.0f, 185.0f);
        g2d.setColor(Color.RED);
        String lives = "Lives: " + gameObjectsInfo.getHero().getNumberOfLives();
        float height = (float) bounds.getHeight();
        g2d.drawString(lives, 7.0f, 185.0f + height);
        bounds = statisticsFont.getStringBounds(lives, context);
        height += bounds.getHeight();
        String health = "Health: " + gameObjectsInfo.getHero().getHealth() + '/' +
                gameObjectsInfo.getHero().getMaxHealth();
        g2d.drawString(health, 7.0f, 185.0f + height);
        bounds = statisticsFont.getStringBounds(health, context);
        height += bounds.getHeight();
        g2d.setColor(Color.YELLOW);
        String armor = "Armor: " + gameObjectsInfo.getHero().getArmor() + '/' +
                gameObjectsInfo.getHero().getMaxArmor();
        bounds = statisticsFont.getStringBounds(armor, context);
        g2d.drawString(armor, 7.0f, 185.0f + height);
        height += bounds.getHeight();
        g2d.setColor(Color.BLUE);
        String shield = "Shield: " + gameObjectsInfo.getHero().getShield() + '/' +
                gameObjectsInfo.getHero().getMaxShield();
        bounds = statisticsFont.getStringBounds(shield, context);
        g2d.drawString(shield, 7.0f, 185.0f + height);
        height += bounds.getHeight();
        g2d.setColor(Color.BLACK);
        String weapon = "Weapon: " + gameObjectsInfo.getHero().getWeaponName();
        bounds = statisticsFont.getStringBounds(weapon, context);
        g2d.drawString(weapon, 7.0f, 185.0f + height);
        height += bounds.getHeight();
        g2d.setColor(new Color(170,0,0));
        String wave = "Wave " + gameObjectsInfo.getWaveNumber();
        bounds = statisticsFont.getStringBounds(wave, context);
        g2d.drawString(wave, 7.0f, 200.0f + height);
        height += bounds.getHeight();
        String enemies = "Enemies still breathing: " + gameObjectsInfo.getNumberOfEnemies();
        bounds = statisticsFont.getStringBounds(enemies, context);
        g2d.drawString(enemies, 7.0f, 200.0f + height);
        height += bounds.getHeight();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, windowSizeY);
    }

    public void setGameObjectsInfo(GameObjectsInfo gameObjectsInfo) {
        this.gameObjectsInfo = gameObjectsInfo;
    }

}
