package Model.asset.tile.interactive;

import Controller.GameController;
import Model.asset.entity.Entity;
import Model.asset.object.equipment.Weapon;

public class InteractiveTile extends Entity {

    private boolean destructible;

    public InteractiveTile(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        checkIfInvincible();
    }

    public void playSoundEffect() {

    }

    public InteractiveTile getDestroyedForm() {
        return null;
    }

    public boolean isDestructible() {
        return destructible;
    }

    public InteractiveTile setDestructible(boolean destructible) {
        this.destructible = destructible;
        return this;
    }

    public boolean isCorrectWeapon(Weapon weapon) {
        return false;
    }
}
