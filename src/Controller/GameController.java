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
import View.UI;
import View.GamePanel;
import Controller.util.CollisionChecker;
import Controller.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameController implements Runnable {
    public controllerDraw draw = new controllerDraw(this) ;
    public controllerUpdate update = new controllerUpdate(this);
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
    // GAME STATE
    public enum typeGame {
        titleState,playState,pauseState,dialogueState,characterState,optionState,
        transitionState,gameOverState,tradeState,gameClear
    }
    public typeGame gameState;

    // ENTITIES & OBJECTS
    public boolean fullScreenOn;
    // GAME THREAD
    public Thread gameThread;
    public PathFinder pFinder = new PathFinder(this);
    public final Player player = new Player(this, keyHandler);
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
        update.setCurrentMap(0);
    }
    public void restart() {
        player.setItems();
        player.setDefaultValues();
        assetManager.setObjects();
        assetManager.setNPCs();
        assetManager.setMonsters();
        assetManager.setInteractiveTiles();
        gameState = typeGame.titleState;
        update.setCurrentMap(0);
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
                draw.drawToTempScreen();
                draw.drawToScreen();
                delta--;
            }
        }
    }
    public void update() {
        if (gameState == typeGame.playState) {
            player.update();
            update.updateNPCs();
            update.updateMonsters();
            update.updateProjectiles();
            update.updateParticles();
            update.updateInteractiveTiles();
        }
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
    // GETTER SETTER METOd
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
    public SoundManager getMusic() {
        return music;
    }
    public SoundManager getSoundEffect() {
        return soundEffect;
    }
    public int getMaxMaps() {
        return Constants.maxMaps;
    }
}
