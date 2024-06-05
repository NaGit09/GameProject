package Model.asset.entity.monster;

import Controller.GameController;
import Model.asset.entity.ability.OBJ_Fireball;
import Model.asset.entity.ability.OBJ_SlimeBall;
import Model.asset.object.usable.inventory.OBJ_Key;


import java.awt.*;
import java.util.Random;

public class BOSS_Samurai extends Monster {
    public BOSS_Samurai(GameController gameController) {
        super(gameController);

        setName("Boss Samurai");
        setDirection("down");
        setSpeed(1);
        setMaxLife(99);
        setMaxMana(99);
        setCurrentMana(getMaxMana());
        setCurrentLife(getMaxLife());
        setAttackPower(20);
        setDefensePower(0);
        setExp(100);
        setProjectile( new OBJ_Fireball(gameController));
        setMaxAmmo(10);
        setCurrentAmmo(getMaxAmmo());

        setCollisionArea(new Rectangle(3, 18, 42, 30));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);

        getAnimationImages();
    }

    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();
        setUp1(setup("/resources/images/boss/boss_up_1", width, height));
        setUp2(setup("/resources/images/boss/boss_up_2", width, height));
        setDown1(setup("/resources/images/boss/boss_down_1", width, height));
        setDown2(setup("/resources/images/boss/boss_down_2", width, height));
        setLeft1(setup("/resources/images/boss/boss_left_1",width, height));
        setLeft2(setup("/resources/images/boss/boss_left_2", width, height));
        setRight1(setup("/resources/images/boss/boss_right_1", width, height));
        setRight2(setup("/resources/images/boss/boss_right_2", width, height));
    }

    @Override
    public void damageReaction() {
        setActionLockCounter(0);
        setDirection(getgameController().getPlayer().getDirection());
    }

    @Override
    public void setupAI() {
        super.setupAI();
    }

    @Override
    public void checkDrop() {
        dropObject(new OBJ_Key(getgameController()));
    }

}
