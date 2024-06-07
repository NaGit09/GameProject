package Model.asset.entity.monster;

import Controller.GameController;
import Model.asset.entity.Entity;
import Model.asset.entity.ability.OBJ_Fireball;
import Model.asset.entity.ability.OBJ_Rock;
import Model.asset.entity.ability.OBJ_SlimeBall;

import java.util.Random;

public class Monster extends Entity {

    public Monster(GameController gameController) {
        super(gameController);
    }

    @Override
    public void setupAI() {
        super.setupAI();
        setupProjectileAI();
    }

    public void setupProjectileAI() {
        int i = new Random().nextInt(100) + 1;
        if (i > 99
                && !getProjectile().isAlive()
                && getProjectileAvailableCounter() == 30
                && getProjectile().haveEnoughResource(this)) {

            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);
            getProjectile().subtractResource(this);
            getgameController().update.getProjectiles().add(getProjectile());
            setProjectileAvailableCounter(0);
        }
    }
}
