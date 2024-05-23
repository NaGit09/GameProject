package Model.asset.entity.monster;

import Controller.GameController;
import Model.asset.object.ability.OBJ_Fireball;
import Model.asset.object.ability.OBJ_Rock;
import Model.asset.object.ability.OBJ_SlimeBall;
import Model.asset.object.usable.pickuponly.OBJ_Coin_Bronze;
import Model.asset.object.usable.pickuponly.OBJ_Heart;
import Model.asset.object.usable.pickuponly.OBJ_ManaCrystal;

import java.awt.*;
import java.util.Random;

public class MON_Slime extends Monster {

    public MON_Slime(GameController gameController) {
        super(gameController);

        setName("Green Slime");
        setDirection("down");
        setSpeed(1);
        setMaxLife(4);
        setCurrentLife(getMaxLife());
        setAttackPower(5);
        setDefensePower(0);
        setExp(2);
        setMaxMana(4);
        setCurrentMana(getMaxMana());
        setProjectile(new OBJ_SlimeBall(gameController));
        setMaxAmmo(5);
        setCurrentAmmo(getMaxAmmo());

        setCollisionArea(new Rectangle(3, 18, 42, 30));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);

        getAnimationImages();
    }

    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();

        setUp1(setup("/resources/images/monster/slime_down_1", width,height));
        setUp2(setup("/resources/images/monster/slime_down_2", width,height));
        setDown1(setup("/resources/images/monster/slime_down_1", width,height));
        setDown2(setup("/resources/images/monster/slime_down_2",width,height));
        setLeft1(setup("/resources/images/monster/slime_down_1", width,height));
        setLeft2(setup("/resources/images/monster/slime_down_2", width,height));
        setRight1(setup("/resources/images/monster/slime_down_1", width,height));
        setRight2(setup("/resources/images/monster/slime_down_2", width,height));
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
        int i = new Random().nextInt(100) + 1;

        if (i < 50) {
            dropObject(new OBJ_Coin_Bronze(getgameController()));
        }

        if (i >= 50 && i < 75) {
            dropObject(new OBJ_Heart(getgameController()));
        }

        if (i >= 75 && i < 100) {
            dropObject(new OBJ_ManaCrystal(getgameController()));
        }
    }

}
