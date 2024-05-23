package Model.asset.entity.particle;

import Controller.GameController;
import Model.asset.Asset;
import Model.asset.entity.Entity;
import Model.asset.entity.player.Player;

import javax.swing.text.PlainDocument;
import java.awt.*;

public class Particle extends Entity {

    private Asset generator;
    private Color color;
    private int size;
    private int xd, yd;

    public Particle(GameController gameController, Asset generator, Color color, int size, int speed, int maxLife, int xd, int yd) {
        super(gameController);

        this.generator = generator;
        this.color = color;
        this.size = size;
        this.setSpeed(speed);
        this.setMaxLife(maxLife);
        this.xd = xd;
        this.yd = yd;

        setCurrentLife(maxLife);
        int offSet = (gameController.getTileSize() / 2) - (size / 2);
        setWorldX(generator.getWorldX() + offSet);
        setWorldY(generator.getWorldY() + offSet);
    }

    @Override
    public void update() {

        setCurrentLife(getCurrentLife() - 1);

        if (getCurrentLife() < getMaxLife() / 3) {
            yd++;
        }

        setWorldX(getWorldX() + (xd * getSpeed()));
        setWorldY(getWorldY() + (yd * getSpeed()));

        if (getCurrentLife() == 0) {
            setAlive(false);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Player player = getgameController().getPlayer();
        int screenX = getWorldX() - player.getWorldX() + player.getScreenX();
        int screenY = getWorldY() - player.getWorldY() + player.getScreenY();

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }
}
