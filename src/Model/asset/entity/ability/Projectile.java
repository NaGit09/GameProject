package Model.asset.entity.ability;

import Controller.GameController;
import Model.asset.entity.Entity;

public abstract class Projectile extends Entity {

    private Entity user;

    public Projectile(GameController gameController) {
        super(gameController);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.setWorldX(worldX);
        this.setWorldY(worldY);
        this.setDirection(direction);
        this.setAlive(alive);
        this.user = user;
        this.setCurrentLife(this.getMaxLife());
    }

    @Override
    public void update() {

        if (user == getgameController().getPlayer()) {

            int monsterIndex = getgameController().getCollisionChecker().checkEntity(this, getgameController().update.getMonsters());

            if (monsterIndex != 999) {
                getgameController().getPlayer().damageMonster(monsterIndex, getAttackPower());
                generateParticle(user.getProjectile(), getgameController().update.getMonsters()[getgameController().update.getCurrentMap()][monsterIndex]);
                setAlive(false);
            }

        } else {

            boolean contactPlayer = getgameController().getCollisionChecker().checkPlayer(this);

            if (!getgameController().getPlayer().isInvincible() && contactPlayer) {
                damagePlayer(getAttackPower());
                generateParticle(user.getProjectile(), getgameController().getPlayer());
                setAlive(false);
            }
        }

        switch (getDirection()) {
            case "up" -> setWorldY(getWorldY() - getSpeed());
            case "down" -> setWorldY(getWorldY() + getSpeed());
            case "left" -> setWorldX(getWorldX() - getSpeed());
            case "right" -> setWorldX(getWorldX() + getSpeed());
        }

        setCurrentLife(getCurrentLife() - 1);
        if (getCurrentLife() <= 0) {
            setAlive(false);
        }

        checkAndChangeSpriteAnimationImage();
    }

    public abstract boolean haveEnoughResource(Entity user);

    public abstract void subtractResource(Entity user);
}
