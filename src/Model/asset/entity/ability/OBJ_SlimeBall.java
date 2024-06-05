package Model.asset.entity.ability;

import Controller.GameController;
import Model.asset.entity.Entity;
import java.awt.*;
    public class OBJ_SlimeBall extends Projectile {

        public OBJ_SlimeBall(GameController gameController) {
            super(gameController);

            setName("water ball");
            setSpeed(6);
            setMaxLife(80);
            setCurrentLife(getMaxLife());
            setAttackPower(2);
            setUseCost(1);
            setAlive(false);

            getAnimationImages();
        }
        public void getAnimationImages() {
            int width = getgameController().getTileSize();
            int height = getgameController().getTileSize();
            setUp1(setup("/resources/images/ability/waterball_up_1", width, height));
            setUp2(setup("/resources/images/ability/waterball_up_2", width, height));
            setDown1(setup("/resources/images/ability/waterball_down_1", width, height));
            setDown2(setup("/resources/images/ability/waterball_down_2", width, height));
            setLeft1(setup("/resources/images/ability/waterball_left_1", width, height));
            setLeft2(setup("/resources/images/ability/waterball_left_2", width, height));
            setRight1(setup("/resources/images/ability/waterball_right_1", width, height));
            setRight2(setup("/resources/images/ability/waterball_right_2", width, height));
        }
        @Override
        public boolean haveEnoughResource(Entity user) {
            return user.getCurrentMana() >= getUseCost();
        }
        @Override
        public void subtractResource(Entity user) {
            user.setCurrentMana(user.getCurrentMana() - getUseCost());
        }
        @Override
        public Color getParticleColor() {
            return new Color(10, 113, 239);
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
