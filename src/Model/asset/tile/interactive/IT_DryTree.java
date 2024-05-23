package Model.asset.tile.interactive;

import Controller.GameController;
import Model.asset.object.equipment.OBJ_Axe;
import Model.asset.object.equipment.Weapon;

import java.awt.*;

public class IT_DryTree extends InteractiveTile {

    private final GameController gameController;

    public IT_DryTree(GameController gameController) {
        super(gameController);
        this.gameController = gameController;

        setDown1(setup("/resources/images/tiles/interactive/drytree", getgameController().getTileSize(), getgameController().getTileSize()));

        setDestructible(true);
        setCollisionDefaultX(-2);
        setCollisionDefaultY(-2);
        setCurrentLife(3);
    }

    @Override
    public boolean isCorrectWeapon(Weapon weapon) {
        return weapon instanceof OBJ_Axe;
    }

    @Override
    public void playSoundEffect() {
        gameController.playSoundEffect(7);
    }

    @Override
    public InteractiveTile getDestroyedForm() {
        InteractiveTile tile = new IT_Trunk(gameController);
        tile.setWorldX(getWorldX());
        tile.setWorldY(getWorldY());
        tile.setIndex(getIndex());
        return tile;
    }

    @Override
    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }

    @Override
    public int getParticleSize() {
        return 6; // 6 pixels
    }

    @Override
    public int getParticleSpeed() {
        return 1;
    }

    @Override
    public int getParticleMaxLife() {
        return 20; // How long it will last
    }
}
