package Model.asset;

import Model.asset.object.Object;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Asset {
    void update();
    void draw(Graphics2D graphics2D);
    void speak();
    void damageReaction();
    void setIndex(int i);
    void setWorldX(int i);
    void setWorldY(int i);
    void setAttackArea(Rectangle attackArea);
    void setInvincible(boolean invincible);
    void setCurrentLife(int currentLife);
    void setAlive(boolean alive);
    void use();
    void setDying(boolean dying);
    void checkDrop();
    void dropObject(Asset droppedObject);


    int getIndex();

    int getCollisionDefaultX();

    int getCollisionDefaultY();

    int getWorldX();

    int getWorldY();

    int getCurrentLife();

    int getMaxLife();

    int getParticleSize();

    int getParticleSpeed();

    int getParticleMaxLife();

    int getPrice();

    int getLevel();

    int getStrength();

    int getDexterity();

    int getAttackPower();

    int getDefensePower();

    int getExp();

    int getNextLevelExp();

    int getCoins();


    boolean isAlive();
    boolean isDying();
    boolean isInvincible();
    boolean isCollision();



    Object getCurrentWeapon();
    Object getCurrentShield();


    String getName();

    String getDescription();

    Rectangle getAttackArea();
    Rectangle getCollisionArea();

    Color getParticleColor();
    BufferedImage getImage1();


}
