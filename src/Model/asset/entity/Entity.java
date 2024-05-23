package Model.asset.entity;

import Controller.GameController;
import Model.asset.Asset;
import Model.asset.entity.ability.Projectile;
import Model.asset.entity.monster.Monster;
import Model.asset.entity.particle.Particle;
import Model.asset.entity.player.Player;
import Model.asset.object.equipment.Shield;
import Model.asset.object.equipment.Weapon;
import Model.asset.tile.interactive.InteractiveTile;
import Controller.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public abstract class Entity implements Asset {

    public final GameController gameController;

    // CHARACTER INFO
    public int index;
    public String name;
    public int worldX, worldY;

    public int speed;
    public int maxLife;
    public int currentLife;
    public int maxMana;
    public int currentMana;
    public int maxAmmo;
    public int currentAmmo;
    public int level;
    public int strength;
    public int dexterity;
    public int attackPower;
    public int defensePower;
    public int exp;
    public int nextLevelExp;

    // OBJECTS & ABILITIES
    public final int maxInventorySize = 20;
    public List<Asset> inventory = new ArrayList<>();
    public int coins;
    public Weapon currentWeapon;
    public Shield currentShield;
    public Projectile projectile;
    public int useCost;

    // ANIMATION
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int actionLockCounter = 0;

    // COLLISION
    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
    public int collisionDefaultX, collisionDefaultY;
    public boolean collisionOn = false;

    // COMBAT
    public boolean invincible = false;
    public int invincibleCounter = 0;
    public boolean attacking = false;
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean hpBarOn = false;
    public int hpBarCounter;
    public int projectileAvailableCounter;
    public  boolean onPath = false;
    // ENTITY STATUS
    public boolean alive = true;
    public boolean dying = false;
    public int dyingCounter;

    public Entity(GameController gameController) {
        this.gameController = gameController;
        setDirection("down");
    }

    public void setupAI() {
        actionLockCounter++;

        if (actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                setDirection("up");
            }

            if (i > 25 && i <= 50) {
                setDirection("down");
            }

            if (i > 50 && i <= 75) {
                setDirection("left");
            }

            if (i > 75) {
                setDirection("right");
            }

            setActionLockCounter(0);
        }
    }

    @Override
    public void speak() {
    }
    public void checkCollision() {

        collisionOn = false;
        gameController.getCollisionChecker().checkTile(this);
        gameController.getCollisionChecker().checkObject(this, false);
        gameController.getCollisionChecker().checkEntity(this, gameController.getNpcs());
        gameController.getCollisionChecker().checkEntity(this, gameController.getMonsters());
        gameController.getCollisionChecker().checkEntity(this, gameController.getInteractiveTiles());
        boolean contactPlayer = gameController.getCollisionChecker().checkPlayer(this);

        if (this instanceof Monster && contactPlayer) {
            damagePlayer(getAttackPower());
        }

    }
    @Override
    public void update() {
        setupAI();
        checkCollision();
        checkIfInvincible();
        moveIfCollisionNotDetected();
        checkAndChangeSpriteAnimationImage();

        if (getProjectileAvailableCounter() < 30) {
            setProjectileAvailableCounter(getProjectileAvailableCounter() + 1);
        }
    }

    public void damagePlayer(int attackPower) {
        if (!gameController.getPlayer().isInvincible()) {
            gameController.playSoundEffect(6);

            int damage = attackPower - getgameController().getPlayer().getDefensePower();
            if (damage < 0) {
                damage = 0;
            }

            gameController.getPlayer().setCurrentLife(gameController.getPlayer().getCurrentLife() - damage);
            gameController.getPlayer().setInvincible(true);
        }
    }

    public void moveIfCollisionNotDetected() {
        if (!isCollisionOn() && !gameController.getKeyHandler().isEnterPressed() && !gameController.getKeyHandler().isSpacePressed()) {
            switch (getDirection()) {
                case "up" -> setWorldY(getWorldY() - getSpeed());
                case "down" -> setWorldY(getWorldY() + getSpeed());
                case "left" -> setWorldX(getWorldX() - getSpeed());
                case "right" -> setWorldX(getWorldX() + getSpeed());
            }
        }
    }

    public void checkAndChangeSpriteAnimationImage() {
        spriteCounter++;
        if (spriteCounter > 24) {
            if (spriteNumber == 1) {
                setSpriteNumber(2);
            } else if (spriteNumber == 2) {
                setSpriteNumber(1);
            }
            setSpriteCounter(0);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gameController.getPlayer().getWorldX() + gameController.getPlayer().getScreenX();
        int screenY = worldY - gameController.getPlayer().getWorldY() + gameController.getPlayer().getScreenY();

        if (UtilityTool.isInsidePlayerView(worldX, worldY, gameController)) {

            drawLifeBar(graphics2D, screenX, screenY);
            drawInvincible(graphics2D);
            drawDying(graphics2D);

            graphics2D.drawImage(getDirectionalAnimationImage(), screenX, screenY, null);

            resetAlphaTo100(graphics2D);
        }

    }

    public void drawLifeBar(Graphics2D graphics2D, int screenX, int screenY) {
        if (this instanceof Monster && hpBarOn) {
            double oneCurrentLifeWidth = (double) gameController.getTileSize() / maxLife;
            double lifeBarValue = oneCurrentLifeWidth * currentLife;

            if (lifeBarValue <= 0) {
                lifeBarValue = 0;
            }

            graphics2D.setColor(new Color(35, 35, 35));
            graphics2D.fillRect(screenX - 1, screenY - 16, gameController.getTileSize() + 2, 12);

            graphics2D.setColor(new Color(255, 0, 30));
            graphics2D.fillRect(screenX, screenY - 15, (int) lifeBarValue, 10);

            hpBarCounter++;

            if (hpBarCounter > 600) {
                setHpBarCounter(0);
                setHpBarOn(false);
            }
        }
    }

    public void drawInvincible(Graphics2D graphics2D) {
        if (isInvincible()) {
            setHpBarOn(true);
            setHpBarCounter(0);

            if (!(this instanceof InteractiveTile)) {
                UtilityTool.changeAlpha(graphics2D, 0.4F);
            }
        }
    }

    public void drawDying(Graphics2D graphics2D) {
        if (isDying()) {
            dyingAnimation(graphics2D);
        }
    }

    public void dyingAnimation(Graphics2D graphics2D) {
        int interval = 5;

        dyingCounter++;

        blinkingAnimation(graphics2D, interval);

        if (dyingCounter > interval * 8) {
            setAlive(false);
        }
    }

    public void blinkingAnimation(Graphics2D graphics2D, int interval) {
        if (dyingCounter <= interval) {
            UtilityTool.changeAlpha(graphics2D, 0);
        }

        if (dyingCounter > interval && dyingCounter <= interval * 2) {
            UtilityTool.changeAlpha(graphics2D, 1);
        }

        if (dyingCounter > interval * 2 && dyingCounter <= interval * 3) {
            UtilityTool.changeAlpha(graphics2D, 0);
        }

        if (dyingCounter > interval * 3 && dyingCounter <= interval * 4) {
            UtilityTool.changeAlpha(graphics2D, 1);
        }

        if (dyingCounter > interval * 4 && dyingCounter <= interval * 5) {
            UtilityTool.changeAlpha(graphics2D, 0);
        }

        if (dyingCounter > interval * 5 && dyingCounter <= interval * 6) {
            UtilityTool.changeAlpha(graphics2D, 1);
        }

        if (dyingCounter > interval * 6 && dyingCounter <= interval * 7) {
            UtilityTool.changeAlpha(graphics2D, 0);
        }

        if (dyingCounter > interval * 7 && dyingCounter <= interval * 8) {
            UtilityTool.changeAlpha(graphics2D, 1);
        }
    }

    public BufferedImage getDirectionalAnimationImage() {
        BufferedImage image = null;

        switch (getDirection()) {
            case "up" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getUp1();
                    if (getSpriteNumber() == 2)
                        image = getUp2();
                } else if (isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getAttackUp1();
                    if (getSpriteNumber() == 2)
                        image = getAttackUp2();
                }
            }
            case "down" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getDown1();
                    if (getSpriteNumber() == 2)
                        image = getDown2();
                } else if (isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getAttackDown1();
                    if (getSpriteNumber() == 2)
                        image = getAttackDown2();
                }

            }
            case "left" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getLeft1();
                    if (getSpriteNumber() == 2)
                        image = getLeft2();
                } else if (isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getAttackLeft1();
                    if (getSpriteNumber() == 2)
                        image = getAttackLeft2();
                }

            }
            case "right" -> {
                if (!isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getRight1();
                    if (getSpriteNumber() == 2)
                        image = getRight2();
                } else if (isAttacking()) {
                    if (getSpriteNumber() == 1)
                        image = getAttackRight1();
                    if (getSpriteNumber() == 2)
                        image = getAttackRight2();
                }

            }
        }
        return image;
    }

    public void checkIfInvincible() {
        if (isInvincible()) {
            setInvincibleCounter(getInvincibleCounter() + 1);

            if (getInvincibleCounter() > ((this instanceof Player) ? 60 : 40)
                    || getInvincibleCounter() > ((this instanceof InteractiveTile) ? 20 : 40)) {
                setInvincible(false);
                setInvincibleCounter(0);
            }
        }
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return UtilityTool.scaleImage(image, width, height);
    }

    public void resetAlphaTo100(Graphics2D graphics2D) {
        UtilityTool.changeAlpha(graphics2D, 1);
    }

    @Override
    public void dropObject(Asset droppedObject) {
        for (int i = 0; i < gameController.getObjects().length; i++) {
            if (gameController.getObjects()[gameController.getCurrentMap()][i] == null) {
                gameController.getObjects()[gameController.getCurrentMap()][i] = droppedObject;
                gameController.getObjects()[gameController.getCurrentMap()][i].setWorldX(worldX);
                gameController.getObjects()[gameController.getCurrentMap()][i].setWorldY(worldY);
                gameController.getObjects()[gameController.getCurrentMap()][i].setIndex(i);
                break;
            }
        }
    }

    public void generateParticle(Entity generator, Asset target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int maxLife = generator.getParticleMaxLife();

        Particle p1 = new Particle(gameController, target, color, size, speed, maxLife, -2, -1);
        Particle p2 = new Particle(gameController, target, color, size, speed, maxLife, 2, -1);
        Particle p3 = new Particle(gameController, target, color, size, speed, maxLife, -2, 1);
        Particle p4 = new Particle(gameController, target, color, size, speed, maxLife, 2, 1);

        gameController.getParticles().add(p1);
        gameController.getParticles().add(p2);
        gameController.getParticles().add(p3);
        gameController.getParticles().add(p4);
    }


    public GameController getgameController() {
        return gameController;
    }

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
        return this;
    }

    public int getWorldX() {
        return worldX;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public Entity setSpeed(int speed) {
        this.speed = speed;
        return this;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public Entity setUp1(BufferedImage up1) {
        this.up1 = up1;
        return this;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public Entity setUp2(BufferedImage up2) {
        this.up2 = up2;
        return this;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public Entity setDown1(BufferedImage down1) {
        this.down1 = down1;
        return this;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public Entity setDown2(BufferedImage down2) {
        this.down2 = down2;
        return this;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public Entity setLeft1(BufferedImage left1) {
        this.left1 = left1;
        return this;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public Entity setLeft2(BufferedImage left2) {
        this.left2 = left2;
        return this;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public Entity setRight1(BufferedImage right1) {
        this.right1 = right1;
        return this;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public Entity setRight2(BufferedImage right2) {
        this.right2 = right2;
        return this;
    }

    public BufferedImage getAttackUp1() {
        return attackUp1;
    }

    public Entity setAttackUp1(BufferedImage attackUp1) {
        this.attackUp1 = attackUp1;
        return this;
    }

    public BufferedImage getAttackUp2() {
        return attackUp2;
    }

    public Entity setAttackUp2(BufferedImage attackUp2) {
        this.attackUp2 = attackUp2;
        return this;
    }

    public BufferedImage getAttackDown1() {
        return attackDown1;
    }

    public Entity setAttackDown1(BufferedImage attackDown1) {
        this.attackDown1 = attackDown1;
        return this;
    }

    public BufferedImage getAttackDown2() {
        return attackDown2;
    }

    public Entity setAttackDown2(BufferedImage attackDown2) {
        this.attackDown2 = attackDown2;
        return this;
    }

    public BufferedImage getAttackLeft1() {
        return attackLeft1;
    }

    public Entity setAttackLeft1(BufferedImage attackLeft1) {
        this.attackLeft1 = attackLeft1;
        return this;
    }

    public BufferedImage getAttackLeft2() {
        return attackLeft2;
    }

    public Entity setAttackLeft2(BufferedImage attackLeft2) {
        this.attackLeft2 = attackLeft2;
        return this;
    }

    public BufferedImage getAttackRight1() {
        return attackRight1;
    }

    public Entity setAttackRight1(BufferedImage attackRight1) {
        this.attackRight1 = attackRight1;
        return this;
    }

    public BufferedImage getAttackRight2() {
        return attackRight2;
    }

    public Entity setAttackRight2(BufferedImage attackRight2) {
        this.attackRight2 = attackRight2;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public Entity setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public int getSpriteCounter() {
        return spriteCounter;
    }

    public Entity setSpriteCounter(int spriteCounter) {
        this.spriteCounter = spriteCounter;
        return this;
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public Entity setSpriteNumber(int spriteNumber) {
        this.spriteNumber = spriteNumber;
        return this;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public Entity setCollisionArea(Rectangle collisionArea) {
        this.collisionArea = collisionArea;
        return this;
    }

    public int getCollisionDefaultX() {
        return collisionDefaultX;
    }

    public Entity setCollisionDefaultX(int collisionDefaultX) {
        this.collisionDefaultX = collisionDefaultX;
        return this;
    }

    public int getCollisionDefaultY() {
        return collisionDefaultY;
    }

    public Entity setCollisionDefaultY(int collisionDefaultY) {
        this.collisionDefaultY = collisionDefaultY;
        return this;
    }

    public boolean isCollisionOn() {
        return collisionOn;
    }

    public Entity setCollisionOn(boolean collisionOn) {
        this.collisionOn = collisionOn;
        return this;
    }

    public int getActionLockCounter() {
        return actionLockCounter;
    }

    public Entity setActionLockCounter(int actionLockCounter) {
        this.actionLockCounter = actionLockCounter;
        return this;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getInvincibleCounter() {
        return invincibleCounter;
    }

    public Entity setInvincibleCounter(int invincibleCounter) {
        this.invincibleCounter = invincibleCounter;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public Entity setMaxLife(int maxLife) {
        this.maxLife = maxLife;
        return this;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public Entity setAttacking(boolean attacking) {
        this.attacking = attacking;
        return this;
    }

    @Override
    public Rectangle getAttackArea() {
        return attackArea;
    }

    @Override
    public void setAttackArea(Rectangle attackArea) {
        this.attackArea = attackArea;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isDying() {
        return dying;
    }

    public void setDying(boolean dying) {
        this.dying = dying;
    }

    public int getDyingCounter() {
        return dyingCounter;
    }

    public void setDyingCounter(int dyingCounter) {
        this.dyingCounter = dyingCounter;
    }

    public boolean isHpBarOn() {
        return hpBarOn;
    }

    public Entity setHpBarOn(boolean hpBarOn) {
        this.hpBarOn = hpBarOn;
        return this;
    }

    public int getHpBarCounter() {
        return hpBarCounter;
    }

    public Entity setHpBarCounter(int hpBarCounter) {
        this.hpBarCounter = hpBarCounter;
        return this;
    }

    public int getLevel() {
        return level;
    }

    public Entity setLevel(int level) {
        this.level = level;
        return this;
    }

    public int getStrength() {
        return strength;
    }

    public Entity setStrength(int strength) {
        this.strength = strength;
        return this;
    }

    public int getDexterity() {
        return dexterity;
    }

    public Entity setDexterity(int dexterity) {
        this.dexterity = dexterity;
        return this;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public Entity setAttackPower(int attackPower) {
        this.attackPower = attackPower;
        return this;
    }

    public int getDefensePower() {
        return defensePower;
    }

    public Entity setDefensePower(int defensePower) {
        this.defensePower = defensePower;
        return this;
    }

    public int getExp() {
        return exp;
    }

    public Entity setExp(int exp) {
        this.exp = exp;
        return this;
    }

    public int getNextLevelExp() {
        return nextLevelExp;
    }

    public Entity setNextLevelExp(int nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
        return this;
    }

    public int getCoins() {
        return coins;
    }

    public Entity setCoins(int coins) {
        this.coins = coins;
        return this;
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public Entity setCurrentWeapon(Weapon currentWeapon) {
        this.currentWeapon = currentWeapon;
        return this;
    }

    public Shield getCurrentShield() {
        return currentShield;
    }

    public Entity setCurrentShield(Shield currentShield) {
        this.currentShield = currentShield;
        return this;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public Entity setMaxMana(int maxMana) {
        this.maxMana = maxMana;
        return this;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public Entity setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
        return this;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public Entity setProjectile(Projectile projectile) {
        this.projectile = projectile;
        return this;
    }

    public int getUseCost() {
        return useCost;
    }

    public Entity setUseCost(int useCost) {
        this.useCost = useCost;
        return this;
    }

    public int getProjectileAvailableCounter() {
        return projectileAvailableCounter;
    }

    public Entity setProjectileAvailableCounter(int projectileAvailableCounter) {
        this.projectileAvailableCounter = projectileAvailableCounter;
        return this;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public Entity setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
        return this;
    }

    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public Entity setCurrentAmmo(int currentAmmo) {
        this.currentAmmo = currentAmmo;
        return this;
    }

    public List<Asset> getInventory() {
        return inventory;
    }

    public void setInventory(List<Asset> inventory) {
        this.inventory = inventory;
    }

    public int getMaxInventorySize() {
        return maxInventorySize;
    }

    // NOT USED

    @Override
    public BufferedImage getImage1() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isCollision() {
        return false;
    }

    @Override
    public void use() {
        // Not used
    }

    @Override
    public void damageReaction() {
    }

    @Override
    public void checkDrop() {
    }

    @Override
    public Color getParticleColor() {
        return null;
    }

    @Override
    public int getParticleSize() {
        return 0;
    }

    @Override
    public int getParticleSpeed() {
        return 0;
    }

    @Override
    public int getParticleMaxLife() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 0;
    }
    public void searchPath (int col , int row) {
    int startCol = (worldX +  collisionArea.x)/ gameController.getTileSize();
        int startRow  = (worldY +  collisionArea.y)/ gameController.getTileSize();
        gameController.pFinder.setNode(startCol,startRow,col,row,this);

        if (gameController.pFinder.search()) {
            int nextX = gameController.pFinder.pathList.get(0).col + gameController.getTileSize();
            int nextY = gameController.pFinder.pathList.get(0).row + gameController.getTileSize();

            int enLeftX = worldX + collisionArea.x;
            int enRightX = worldX + collisionArea.x + collisionArea.width;

            int enTopY = worldY + collisionArea.y;
            int enBottomY = worldY + collisionArea.y + collisionArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gameController.getTileSize()){
                direction = "up";

            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gameController.getTileSize()){
                direction = "down";

            }
            else if (enTopY >= nextY && enBottomY < nextY + gameController.getTileSize()){
                if (enLeftX > nextX) {
                    direction = "left";

                }
                if (enRightX < nextX) {
                    direction="Right";

                }

            }
            else if (enTopY > nextY && enLeftX > nextX) {
                direction ="up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";

                }
            }
            else if (enTopY > nextY && enLeftX < nextX) {
                direction ="up";
                checkCollision();
                if (collisionOn) {
                    direction = "right";

                }
            }
            else if (enTopY < nextY && enLeftX > nextX) {
                direction ="down";
                checkCollision();
                if (collisionOn) {
                    direction = "right";

                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                direction ="dowb";
                checkCollision();
                if (collisionOn) {
                    direction = "right";

                }
            }
            int nextCol = gameController.pFinder.pathList.get(0).col;
            int nextRow = gameController.pFinder.pathList.get(0).row;

            if (nextCol == col && nextRow == row) {
                onPath = false;
            }

        }

    }
}
