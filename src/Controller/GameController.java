package Controller;

import Model.ai.PathFinder;
import Model.asset.Asset;
import Model.asset.AssetManager;
import Model.asset.entity.Entity;
import Model.asset.entity.player.Player;
import Model.asset.object.Object;
import Model.asset.tile.TileManager;
import Model.asset.tile.interactive.InteractiveTile;
import Model.event.EventHandler;
import Model.sound.SoundManager;
import View.GamePanel;
import Model.util.CollisionChecker;
import Model.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameController implements Runnable {

    // Full screen mode
    public int fullScreenWidth = Constants.screenWidth;
    public int fullScreenHeight = Constants.screenHeight;
    public BufferedImage tempScreen;
    public Graphics2D graphics2D;


    // SYSTEM
    public final KeyHandler keyHandler = new KeyHandler(this);
    public final CollisionChecker collisionChecker = new CollisionChecker(this);
    public  final TileManager tileManager = new TileManager(this);
    public final AssetManager assetManager = new AssetManager(this);
    public final SoundManager music = new SoundManager();
    public final SoundManager soundEffect = new SoundManager();
    public final UI ui = new UI(this);
    public final EventHandler eventHandler = new EventHandler(this);
//    public final Config config = new Config(this);

    // GAME STATE
    public enum typeGame {
        titleState,playState,pauseState,dialogueState,characterState,optionState,transitionState,gameOverState,tradeState,gameClear
    }

    public typeGame gameState;

    // ENTITIES & OBJECTS
    public final List<Asset> assets = new ArrayList<>();
    public boolean fullScreenOn;

    // GAME THREAD
    public Thread gameThread;
    public PathFinder pFinder = new PathFinder(this);
    public int currentMap = 0;
    public final Asset[][] objects = new Object[Constants.maxMaps][20];
    public final Player player = new Player(this, keyHandler);
    public final Asset[][] npcs = new Entity[Constants.maxMaps][10];
    public final Asset[][] monsters = new Entity[Constants.maxMaps][20];
    public final InteractiveTile[][] interactiveTiles = new InteractiveTile[Constants.maxMaps][50];
    public final List<Asset> projectiles = new ArrayList<>();
    public final List<Asset> particles = new ArrayList<>();
    public final GamePanel gp;

    public GameController(GamePanel gp) {

        this.gp = gp;
    }

    public void setUpGame() {

        assetManager.setObjects();
        assetManager.setNPCs();
        assetManager.setMonsters();
        assetManager.setInteractiveTiles();
        gameState = typeGame.titleState;

        tempScreen = new BufferedImage(Constants.screenWidth, Constants.screenHeight, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) tempScreen.getGraphics();

        if (fullScreenOn) {
            setFullScreen();
        }
    }

    public void setFullScreen() {

        // GET LOCAL SCREEN DEVICE
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();

        graphicsDevice.setFullScreenWindow(Main.window);

        // GET FULLSCREEN WIDTH & HEIGHT
        fullScreenWidth = Main.window.getWidth();
        fullScreenHeight = Main.window.getHeight();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void retry() {
        player.setDefaultPosition();
        player.restoreLifeAndMana();
        assetManager.setNPCs();
        assetManager.setMonsters();
        gameState = typeGame.playState;
        setCurrentMap(0);
    }

    public void restart() {
        player.setItems();
        player.setDefaultValues();
        assetManager.setObjects();
        assetManager.setNPCs();
        assetManager.setMonsters();
        assetManager.setInteractiveTiles();
        gameState = typeGame.titleState;
        setCurrentMap(0);
        stopMusic();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1_000_000_000 / Constants.FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == typeGame.playState) {
            player.update();
            updateNPCs();
            updateMonsters();
            updateProjectiles();
            updateParticles();
            updateInteractiveTiles();
        }


    }

    public void updateNPCs() {
        for (int i = 0; i < npcs[1].length; i++) {
            if (npcs[currentMap][i] != null) {
                npcs[currentMap][i].update();
            }
        }
    }

    public void updateMonsters() {
        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                if (monsters[currentMap][i].isAlive() && !monsters[currentMap][i].isDying()) {
                    monsters[currentMap][i].update();
                }

                if (!monsters[currentMap][i].isAlive()) {
                    monsters[currentMap][i].checkDrop();
                    removeMonster(monsters[currentMap][i].getIndex());
                }
            }
        }
    }

    public void removeMonster(int index) {
        monsters[currentMap][index] = null;
    }

    public void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i) != null) {
                if (projectiles.get(i).isAlive()) {
                    projectiles.get(i).update();
                }

                if (!projectiles.get(i).isAlive()) {
                    projectiles.remove(projectiles.get(i));
                }
            }
        }
    }

    public void updateParticles() {
        for (int i = 0; i < particles.size(); i++) {
            if (particles.get(i) != null) {
                if (particles.get(i).isAlive()) {
                    particles.get(i).update();
                }

                if (!particles.get(i).isAlive()) {
                    particles.remove(particles.get(i));
                }
            }
        }
    }

    public void updateInteractiveTiles() {
        for (int i = 0; i < interactiveTiles[1].length; i++) {
            if (interactiveTiles[currentMap][i] != null) {
                interactiveTiles[currentMap][i].update();
            }
        }
    }

    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;
        if (keyHandler.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        if (gameState == typeGame.titleState) {
            ui.draw(graphics2D);
        } else {

            // TILES
            tileManager.draw(graphics2D);

            drawInteractiveTiles(graphics2D);

            // ASSETS
            addAssets();
            sortAssets();
            drawAssets(graphics2D);
            assets.clear();

            // UI
            ui.draw(graphics2D);
        }

        // DEBUG
        if (keyHandler.isShowDebugText()) {
            drawDebugInfo(graphics2D, drawStart);
        }
    }

    public void drawInteractiveTiles(Graphics2D graphics2D) {
        for (int i = 0; i < interactiveTiles[1].length; i++) {
            if (interactiveTiles[currentMap][i] != null) {
                interactiveTiles[currentMap][i].draw(graphics2D);
            }
        }
    }

    public void addAssets() {
        assets.add(player);

        for (int i = 0; i < npcs[1].length; i++) {
            if (npcs[currentMap][i] != null) {
                assets.add(npcs[currentMap][i]);
            }
        }

        for (int i = 0; i < objects[1].length; i++) {
            if (objects[currentMap][i] != null) {
                assets.add(objects[currentMap][i]);
            }
        }

        for (int i = 0; i < monsters[1].length; i++) {
            if (monsters[currentMap][i] != null) {
                assets.add(monsters[currentMap][i]);
            }
        }

        for (Asset projectile : projectiles) {
            if (projectile != null) {
                assets.add(projectile);
            }
        }

        for (Asset particle : particles) {
            if (particle != null) {
                assets.add(particle);
            }
        }
    }

    public void sortAssets() {
        assets.sort(Comparator.comparingInt(Asset::getWorldY));
    }

    public void drawAssets(Graphics2D graphics2D) {
        for (Asset asset : assets) {
            asset.draw(graphics2D);
        }
    }

    public void drawDebugInfo(Graphics2D graphics2D, long drawStart) {
        long drawEnd = System.nanoTime();
        long passedTime = drawEnd - drawStart;
        int x = 10;
        int y = 400;
        int lineHeight = 20;

        graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
        graphics2D.setColor(Color.WHITE);

        graphics2D.drawString("WorldX: " + player.getWorldX(), x, y);
        y += lineHeight;
        graphics2D.drawString("WorldY: " + player.getWorldY(), x, y);
        y += lineHeight;
        graphics2D.drawString("Col: " + (player.getWorldX() + player.getCollisionArea().x) / Constants.tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Row: " + (player.getWorldY() + player.getCollisionArea().y) / Constants.tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Draw Time: " + passedTime, x, y);
    }

    public void drawToScreen() {
        Graphics graphics = gp.getGraphics();
        graphics.drawImage(tempScreen, 0, 0, fullScreenWidth, fullScreenHeight, null);
        graphics.dispose();
    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }

    // GETTER SETTER METOD
    public int getTileSize() {
        return Constants.tileSize;
    }

    public int getMaxScreenColumns() {
        return Constants.maxScreenColumns;
    }

    public int getMaxScreenRows() {
        return Constants.maxScreenRows;
    }

    public int getWorldWidth() {
        return Constants.worldWidth;
    }

    public int getWorldHeight() {
        return Constants.worldHeight;
    }

    public int getScreenWidth() {
        return Constants.screenWidth;
    }

    public int getScreenHeight() {
        return Constants.screenHeight;
    }

    public int getMaxWorldColumns() {
        return Constants.maxWorldColumns;
    }

    public int getMaxWorldRows() {
        return Constants.maxWorldRows;
    }

    public boolean isFullScreenOn() {
        return fullScreenOn;
    }

    public void setFullScreenOn(boolean fullScreenOn) {
        this.fullScreenOn = fullScreenOn;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }

    public AssetManager getObjectManager() {
        return assetManager;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public void  setGameThread(Thread gameThread) {
        this.gameThread = gameThread;

    }
    public GameController getGameController () {
        return this;
    }

    public Player getPlayer() {
        return player;
    }

    public Asset[][] getObjects() {
        return objects;
    }

    public Asset[][] getNpcs() {
        return npcs;
    }

    public Asset[][] getMonsters() {
        return monsters;
    }

    public UI getUi() {
        return ui;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }


    public typeGame getGameState() {
        return gameState;
    }

    public void setGameState(typeGame gameState) {
        this.gameState = gameState;
    }

    public typeGame getTitleState() {
        return typeGame.titleState;
    }

    public typeGame getPlayState() {
        return typeGame.playState;
    }

    public typeGame getPauseState() {
        return typeGame.pauseState;
    }

    public typeGame getDialogueState() {
        return typeGame.dialogueState;
    }

    public typeGame getCharacterState() {
        return typeGame.characterState;
    }

    public typeGame getOptionState() {
        return typeGame.optionState;
    }

    public typeGame getGameOverState() {
        return typeGame.gameOverState;
    }

    public typeGame getGameClear() {
        return typeGame.gameClear;
    }
    public typeGame getTransitionState() {
        return typeGame.transitionState;
    }
    public typeGame getTradeState() {
        return typeGame.tradeState;
    }

    public List<Asset> getProjectiles() {
        return projectiles;
    }

    public InteractiveTile[][] getInteractiveTiles() {
        return interactiveTiles;
    }

    public List<Asset> getParticles() {
        return particles;
    }

    public SoundManager getMusic() {
        return music;
    }

    public SoundManager getSoundEffect() {
        return soundEffect;
    }

    public int getMaxMaps() {
        return Constants.maxMaps;
    }

    public int getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(int currentMap) {
        this.currentMap = currentMap;

    }


}
