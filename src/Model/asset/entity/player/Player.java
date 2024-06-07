package Model.asset.entity.player;

import Controller.GameController;
import Model.asset.Asset;
import Model.asset.entity.Entity;
import Model.asset.entity.ability.OBJ_Rock;
import Model.asset.entity.monster.BOSS;
import Model.asset.object.equipment.*;
import Model.asset.object.interactive.OBJ_Chest;
import Model.asset.object.usable.inventory.OBJ_Key;
import Model.asset.object.usable.inventory.OBJ_Potion_Blue;
import Model.asset.object.usable.inventory.OBJ_Potion_Red;
import Model.asset.object.usable.pickuponly.OBJ_Boots;
import Model.asset.object.usable.pickuponly.PickUpOnlyObject;
import Model.asset.tile.interactive.InteractiveTile;
import Controller.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    private final KeyHandler keyHandler;
    private final int screenX;
    private final int screenY;
    private int resetTimer;
    public int key = 0;
    public Player(GameController gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
        this.keyHandler = keyHandler;
        this.screenX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        this.screenY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);
        setItems();
        setDefaultValues();
        setCollision();
        getAnimationImages();
    }
    public void setDefaultValues() {
        setDefaultPosition();
        setSpeed(4);
        setMaxLife(6);
        setCurrentLife(getMaxLife());
        setMaxMana(4);
        setCurrentMana(getMaxMana());
        setMaxAmmo(10);
        setCurrentAmmo(getMaxAmmo());
        setLevel(1);
        setStrength(1);
        setDexterity(1);
        setExp(0);
        setNextLevelExp(5);
        setCoins(0);
        setKey(0);
        setAttackPower(getAttack());
        setDefensePower(getDefense());

    }
    public void setItems() {
        getInventory().clear();
        setDefaultWeapon();
        setCurrentShield(new OBJ_Shield_Wood(getgameController()));
        setProjectile(new OBJ_Rock(getgameController()));
        getInventory().add(getCurrentWeapon());
        getInventory().add(new OBJ_Axe(getgameController()));
        getInventory().add(getCurrentShield());
    }
    public void setDefaultPosition() {
        setWorldX(getgameController().getTileSize() * 23);
        setWorldY(getgameController().getTileSize() * 21);
        setDirection("down");
    }
    private void setDefaultWeapon() {
        setCurrentWeapon(new OBJ_Sword_Normal(getgameController()));
        setPlayerAttackArea();
        getAttackImages();
    }
    public void restoreLifeAndMana() {
        setCurrentLife(getMaxLife());
        setCurrentMana(getMaxMana());
        setInvincible(false);
    }
    public int getAttack() {
        return getStrength() * getCurrentWeapon().getAttackValue();
    }
    public int getDefense() {
        return getDexterity() * getCurrentShield().getDefenseValue();
    }
    private void setCollision() {
        setCollisionArea(new Rectangle(8, 16, 32, 32));
        setCollisionDefaultX(getCollisionArea().x);
        setCollisionDefaultY(getCollisionArea().y);
    }
    private void setPlayerAttackArea() {
        setAttackArea(getCurrentWeapon().getAttackArea());
    }
    public void getAnimationImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();

        setUp1(setup("/resources/images/player/boy_up_1", width, height));
        setUp2(setup("/resources/images/player/boy_up_2", width, height));
        setDown1(setup("/resources/images/player/boy_down_1", width, height));
        setDown2(setup("/resources/images/player/boy_down_2", width, height));
        setLeft1(setup("/resources/images/player/boy_left_1", width, height));
        setLeft2(setup("/resources/images/player/boy_left_2", width, height));
        setRight1(setup("/resources/images/player/boy_right_1", width, height));
        setRight2(setup("/resources/images/player/boy_right_2", width, height));
    }
    public void getAttackImages() {
        int width = getgameController().getTileSize();
        int height = getgameController().getTileSize();

        if (getCurrentWeapon() instanceof OBJ_Sword_Normal) {
            setAttackUp1(setup("/resources/images/player/boy_sword_up_1", width, height * 2));
            setAttackUp2(setup("/resources/images/player/boy_sword_up_2", width, height * 2));
            setAttackDown1(setup("/resources/images/player/boy_sword_down_1", width, height * 2));
            setAttackDown2(setup("/resources/images/player/boy_sword_down_2", width, height * 2));
            setAttackLeft1(setup("/resources/images/player/boy_sword_left_1", width * 2, height));
            setAttackLeft2(setup("/resources/images/player/boy_sword_left_2", width * 2, height));
            setAttackRight1(setup("/resources/images/player/boy_sword_right_1", width * 2, height));
            setAttackRight2(setup("/resources/images/player/boy_sword_right_2", width * 2, height));
        }

        if (getCurrentWeapon() instanceof OBJ_Axe) {
            setAttackUp1(setup("/resources/images/player/boy_axe_up_1", width, height * 2));
            setAttackUp2(setup("/resources/images/player/boy_axe_up_2", width, height * 2));
            setAttackDown1(setup("/resources/images/player/boy_axe_down_1", width, height * 2));
            setAttackDown2(setup("/resources/images/player/boy_axe_down_2", width, height * 2));
            setAttackLeft1(setup("/resources/images/player/boy_axe_left_1", width * 2, height));
            setAttackLeft2(setup("/resources/images/player/boy_axe_left_2", width * 2, height));
            setAttackRight1(setup("/resources/images/player/boy_axe_right_1", width * 2, height));
            setAttackRight2(setup("/resources/images/player/boy_axe_right_2", width * 2, height));
        }
    }
    @Override
    public void update() {
        if (isAttacking()) {
            attacking();
        } else if (keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed() || keyHandler.isEnterPressed() || keyHandler.isSpacePressed()) {

            if (keyHandler.isUpPressed()) {
                setDirection("up");
            } else if (keyHandler.isDownPressed()) {
                setDirection("down");
            } else if (keyHandler.isLeftPressed()) {
                setDirection("left");
            } else if (keyHandler.isRightPressed()) {
                setDirection("right");
            }

            checkIfAttacking();
            checkCollision();
            checkEvent();
            moveIfCollisionNotDetected();
            resetEnterPressedValue();
            checkAndChangeSpriteAnimationImage();
        } else {
            resetSpriteToDefault();
        }

        fireProjectileIfKeyPressed();
        checkIfInvincible();
        updateLifeAndMana();
        checkIfAlive();
    }
    private void attacking() {
        setSpriteCounter(getSpriteCounter() + 1);

        if (getSpriteCounter() <= 5) {
            setSpriteNumber(1);
        }

        if (getSpriteCounter() > 5 && getSpriteCounter() <= 25) {
            setSpriteNumber(2);

            // Save current worldX, worldY and CollisionArea
            int currentWorldX = getWorldX();
            int currentWorldY = getWorldY();
            int collisionAreaWidth = getCollisionArea().width;
            int collisionAreaHeight = getCollisionArea().height;

            // Adjust player's worldX/Y to the attackArea
            switch (getDirection()) {
                case "up" -> setWorldY(currentWorldY - getAttackArea().height);
                case "down" -> setWorldY(currentWorldY + getAttackArea().height);
                case "left" -> setWorldX(currentWorldX - getAttackArea().width);
                case "right" -> setWorldX(currentWorldX + getAttackArea().width);
            }

            // Make collisionArea into attackArea
            getCollisionArea().width = getAttackArea().width;
            getCollisionArea().height = getAttackArea().height;

            // Check monster collision with updated collisionArea
            int monsterIndex = getgameController().getCollisionChecker().checkEntity(this, getgameController().update.getMonsters());
            damageMonster(monsterIndex, getAttackPower());

            // Check interactiveTile collision
            int interactiveTileIndex = getgameController().getCollisionChecker().checkEntity(this, getgameController().update.getInteractiveTiles());
            damageInteractiveTile(interactiveTileIndex);

            // Reset collisionArea to player
            setWorldX(currentWorldX);
            setWorldY(currentWorldY);
            getCollisionArea().width = collisionAreaWidth;
            getCollisionArea().height = collisionAreaHeight;
        }

        if (getSpriteCounter() > 25) {
            setSpriteNumber(1);
            setSpriteCounter(0);
            setAttacking(false);
        }
    }
    public void damageMonster(int index, int attackPower) {
        if (index != 999) {
            Asset monster = getgameController().update.getMonsters()[getgameController().update.getCurrentMap()][index];
            if (!monster.isInvincible()) {

                getgameController().playSoundEffect(5);

                int damage = attackPower - monster.getDefensePower();
                if (damage < 0) {
                    damage = 0;
                }

                monster.setCurrentLife(monster.getCurrentLife() - damage);
                getgameController().getUi().addMessage(damage + " damage!");

                monster.setInvincible(true);
                monster.damageReaction();

                if (monster.getCurrentLife() <= 0) {
                    monster.setDying(true);
                    getgameController().getUi().addMessage("Killed the " + monster.getName() + "!");
                    setExp(getExp() + monster.getExp());
                    getgameController().getUi().addMessage("Exp + " + monster.getExp());

                    checkLevelUp();
                    // GAME CLEAR SAU KHI ĐÁNH BẠI BOSS GAME
                    if (monster instanceof BOSS) {
                        gameController.setGameState(gameController.getGameClear());
                    }

                }
            }
        }
    }
    private void damageInteractiveTile(int index) {

        if (index != 999) {
            InteractiveTile IT = getgameController().update.getInteractiveTiles()[getgameController().update.getCurrentMap()][index];
            if (IT.isDestructible() && IT.isCorrectWeapon(getCurrentWeapon()) && !IT.isInvincible()) {

                IT.playSoundEffect();
                IT.setCurrentLife(IT.getCurrentLife() - 1);
                IT.setInvincible(true);

                generateParticle(getgameController().update.getInteractiveTiles()[getgameController().update.getCurrentMap()][index], getgameController().update.getInteractiveTiles()[getgameController().update.getCurrentMap()][index]);

                if (getgameController().update.getInteractiveTiles()[getgameController().update.getCurrentMap()][index].getCurrentLife() == 0) {
                    getgameController().update.getInteractiveTiles()[getgameController().update.getCurrentMap()][index] = getgameController().update.getInteractiveTiles()[getgameController().update.getCurrentMap()][index].getDestroyedForm();
                }
            }
        }


    }
    public void checkLevelUp() {
        if (getExp() >= getNextLevelExp()) {
            setLevel(getLevel() + 1);
            setNextLevelExp(getNextLevelExp() * 3);
            setMaxLife(getMaxLife() + 2);
            setStrength(getStrength() + 1);
            setDexterity(getDexterity() + 1);
            setAttackPower(getAttack());
            setDefensePower(getDefense());

            getgameController().playSoundEffect(8);
            getgameController().setGameState(getgameController().getDialogueState());
            getgameController().getUi().setCurrentDialogue("You are level " + getLevel() + " now!\n" + "You feel stronger!");
        }
    }
    private void checkIfAttacking() {
        if (getgameController().getKeyHandler().isSpacePressed()) {
            getgameController().playSoundEffect(7);
            setAttacking(true);
        }
    }
    public void checkCollision() {
        setCollisionOn(false);

        checkTileCollision();
        checkInteractiveTileCollision();
        checkObjectCollision();
        checkNPCCollision();
        checkMonsterCollision();

    }
    private void checkTileCollision() {
        getgameController().getCollisionChecker().checkTile(this);
    }
    private void checkInteractiveTileCollision() {
        getgameController().getCollisionChecker().checkEntity(this, getgameController().update.getInteractiveTiles());
    }
    private void checkObjectCollision() {
        int objectIndex = getgameController().getCollisionChecker().checkObject(this, true);
        pickUpObject(objectIndex);
    }
    private void pickUpObject(int index) {
        if (index != 999) {
            int currentMap = getgameController().update.getCurrentMap();

            // PICK-UP ONLY ITEMS
            if (getgameController().update.getObjects()[currentMap][index] instanceof PickUpOnlyObject) {
                if (getgameController().update.getObjects()[currentMap][index] instanceof OBJ_Boots) {
                    setSpeed(6);
                }
                getgameController().update.getObjects()[currentMap][index].use();
            }

            // INVENTORY ITEMS
            else {

                String text;
                if (getInventory().size() != getMaxInventorySize()) {
                    if (getgameController().update.getObjects()[currentMap][index] instanceof OBJ_Key) {
                        setKey(getKey()+1);
                    }
                    getInventory().add(getgameController().update.getObjects()[currentMap][index]);
                    getgameController().playSoundEffect(1);
                    text = "Got a " + getgameController().update.getObjects()[currentMap][index].getName() + "!";
                } else {
                    text = "You cannot carry anymore!";
                }

                getgameController().getUi().addMessage(text);
            }

            getgameController().update.getObjects()[currentMap][index] = null;
        }
    }
    private void checkNPCCollision() {
        int npcIndex = getgameController().getCollisionChecker().checkEntity(this, getgameController().update.getNpcs());
        interactWithNPC(npcIndex);
    }
    private void interactWithNPC(int index) {
        if (index != 999) {
            if (getgameController().getKeyHandler().isEnterPressed()) {
                getgameController().setGameState(getgameController().getDialogueState());
                getgameController().update.getNpcs()[getgameController().update.getCurrentMap()][index].speak();
            }
        }
    }
    private void checkMonsterCollision() {
        int monsterIndex = getgameController().getCollisionChecker().checkEntity(this, getgameController().update.getMonsters());
        interactWithMonster(monsterIndex);
    }
    private void interactWithMonster(int index) {
        if (index != 999) {
            if (!isInvincible() && !getgameController().update.getMonsters()[getgameController().update.getCurrentMap()][index].isDying()) {
                getgameController().playSoundEffect(6);

                int damage = getgameController().update.getMonsters()[getgameController().update.getCurrentMap()][index].getAttackPower() - getDefensePower();
                if (damage < 0) {
                    damage = 0;
                }

                setCurrentLife(getCurrentLife() - damage);
                setInvincible(true);
            }
        }
    }
    private void checkEvent() {
        getgameController().getEventHandler().checkEvent();
    }
    private void resetEnterPressedValue() {
        keyHandler.setEnterPressed(false);
    }
    private void resetSpriteToDefault() {
        resetTimer++;
        if (resetTimer == 20) {
            setSpriteNumber(1);
            resetTimer = 0;
        }
    }
    private void fireProjectileIfKeyPressed() {
        if (getgameController().getKeyHandler().isProjectileKeyPressed() && !getProjectile().isAlive() && getProjectileAvailableCounter() == 30 && getProjectile().haveEnoughResource(this)) {

            // Set default coordinates, direction and user
            getProjectile().set(getWorldX(), getWorldY(), getDirection(), true, this);

            // Subtract use cost
            getProjectile().subtractResource(this);

            // Add it to the projectiles list
            getgameController().update.getProjectiles().add(getProjectile());

            setProjectileAvailableCounter(0);

            getgameController().playSoundEffect(10);
        }

        if (getProjectileAvailableCounter() < 30) {
            setProjectileAvailableCounter(getProjectileAvailableCounter() + 1);
        }
    }
    private void updateLifeAndMana() {
        if (getCurrentLife() > getMaxLife()) {
            setCurrentLife(getMaxLife());
        }
        if (getCurrentLife() < 0) {
            setCurrentLife(0);
        }
        if (getCurrentMana() > getMaxMana()) {
            setCurrentMana(getMaxMana());
        }
        if (getCurrentMana() < 0) {
            setCurrentMana(0);
        }
    }
    private void checkIfAlive() {
        if (getCurrentLife() <= 0) {
            getgameController().playSoundEffect(11);
            getgameController().setGameState(getgameController().getGameOverState());
            setInvincible(false);
        }
    }
    public void selectItem() {
        int itemIndex = getgameController().getUi().getItemIndexFromSlot(getgameController().getUi().getPlayerSlotCol(), getgameController().getUi().getPlayerSlotRow());

        if (itemIndex < getInventory().size()) {
            Asset selectedItem = getInventory().get(itemIndex);

            if (selectedItem instanceof Weapon) {
                setCurrentWeapon((Weapon) selectedItem);
                setAttackPower(getAttack());
                setPlayerAttackArea();
                getAttackImages();
            }

            if (selectedItem instanceof Shield) {
                setCurrentShield((Shield) selectedItem);
                setDefensePower(getDefense());
            }

            if (selectedItem instanceof OBJ_Potion_Red) {
                selectedItem.use();
                getInventory().remove(itemIndex);
            }
            if (selectedItem instanceof OBJ_Potion_Blue) {
                selectedItem.use();
                getInventory().remove(itemIndex);
            }
            if (selectedItem instanceof OBJ_Boots) {
                selectedItem.use();
            }
            if (selectedItem instanceof OBJ_Chest) {
                if (getKey() == 0) {
                    System.out.println(key);
                    gameController.getUi().drawNotifyNotKey();

                }
                else {
                    selectedItem.use();
                    getInventory().remove(itemIndex);
                    gameController.player.getInventory().removeIf(a -> a instanceof OBJ_Key);

                }
            }

        }
    }
    @Override
    public void draw(Graphics2D graphics2D) {
        int rightOffset = getgameController().getScreenWidth() - screenX;
        int x = checkIfAtEdgeOfXAxis(rightOffset);

        int bottomOffset = getgameController().getScreenHeight() - screenY;
        int y = checkIfAtEdgeOfYAxis(bottomOffset);

        if (isInvincible()) {
            graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4F));
        }

        if (isAttacking()) {
            int tileSize = getgameController().getTileSize();
            switch (getDirection()) {
                case "up" -> graphics2D.drawImage(getDirectionalAnimationImage(), x, y - tileSize, null);
                case "left" -> graphics2D.drawImage(getDirectionalAnimationImage(), x - tileSize, y, null);
                default -> graphics2D.drawImage(getDirectionalAnimationImage(), x, y, null);
            }
        } else {
            graphics2D.drawImage(getDirectionalAnimationImage(), x, y, null);
        }

        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
    private int checkIfAtEdgeOfXAxis(int rightOffset) {
        if (screenX > getWorldX()) {
            return getWorldX();
        }

        if (rightOffset > getgameController().getWorldWidth() - getWorldX()) {
            return getgameController().getScreenWidth() - (getgameController().getWorldWidth() - getWorldX());
        }

        return screenX;
    }
    private int checkIfAtEdgeOfYAxis(int bottomOffset) {
        if (screenY > getWorldY()) {
            return getWorldY();
        }

        if (bottomOffset > getgameController().getWorldHeight() - getWorldY()) {
            return getgameController().getScreenHeight() - (getgameController().getWorldHeight() - getWorldY());
        }

        return screenY;
    }
    public int getScreenX() {
        return screenX;
    }
    public int getScreenY() {
        return screenY;
    }
    @Override
    public BufferedImage getImage1() {
        return getDown1();
    }
    @Override
    public void damageReaction() {
        // Not used yet
    }
    public int getKey() {
        return key;
    }
    public void setKey(int key) {
        this.key = key;
    }
}
