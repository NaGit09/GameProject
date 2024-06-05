package Model.asset.entity.ability;

import Controller.GameController;
import Model.asset.entity.Entity;
import Model.asset.entity.ability.Projectile;

import java.awt.*;

public class OBJ_Rock extends Projectile {

    public OBJ_Rock(GameController gameController) {
        super(gameController);

        setName("Rock Projectile");
        setSpeed(9);
        setMaxLife(80);
        setCurrentLife(getMaxLife());
        setAttackPower(5);
        setUseCost(1);
        setAlive(false);

        getAnimationImages();
    }

    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();

        setUp1(setup("/resources/images/ability/rock_down_1", width, height));
        setUp2(setup("/resources/images/ability/rock_down_1", width, height));
        setDown1(setup("/resources/images/ability/rock_down_1", width, height));
        setDown2(setup("/resources/images/ability/rock_down_1", width, height));
        setLeft1(setup("/resources/images/ability/rock_down_1", width, height));
        setLeft2(setup("/resources/images/ability/rock_down_1", width, height));
        setRight1(setup("/resources/images/ability/rock_down_1", width, height));
        setRight2(setup("/resources/images/ability/rock_down_1", width, height));
    }

    @Override
    public boolean haveEnoughResource(Entity user) {
        return user.getCurrentAmmo() >= getUseCost();
    }

    @Override
    public void subtractResource(Entity user) {
        user.setCurrentMana(user.getCurrentMana() - getUseCost());
    }

    @Override
    public Color getParticleColor() {
        return new Color(40, 50, 0);
    }

    @Override
    public int getParticleSize() {
        return 10; // pixels
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
