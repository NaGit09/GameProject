package Model.event;

import Controller.GameController;
import Model.asset.Asset;

public class EventHandler {

    private final GameController gameController;
    private final EventRectangle[][][] eventRect;
    private int previousEventX, previousEventY;
    private boolean canTouchEvent = true;
    private int tempMap, tempCol, tempRow;

    public EventHandler(GameController gameController) {
        this.gameController = gameController;

        this.eventRect = new EventRectangle[gameController.getMaxMaps()][gameController.getMaxWorldColumns()][gameController.getMaxWorldRows()];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gameController.getMaxMaps() && col < gameController.getMaxWorldColumns() && row < gameController.getMaxWorldRows()) {
            eventRect[map][col][row] = new EventRectangle();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].setEventRectDefaultX(eventRect[map][col][row].x);
            eventRect[map][col][row].setEventRectDefaultY(eventRect[map][col][row].y);

            col++;

            if (col == gameController.getMaxWorldColumns()) {
                col = 0;
                row++;

                if (row == gameController.getMaxWorldRows()) {
                    row = 0;
                    map++;
                }
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gameController.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gameController.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gameController.getTileSize()) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 27, 16, "right")) {
                damagePit();
            }
             if (hit(0, 23, 19, "any")) {
                damagePit();
            }
             if (hit(0, 23, 12, "up")) {
                healingPool();
            }
             if (hit(0, 10, 39, "any")) {
                teleport(1, 16, 13);
            }
             if (hit(1, 16, 13, "any")) {
                 teleport(0, 10, 39);
             }
             if (hit(1, 12, 9, "up")) {
                speak(gameController.getNpcs()[1][0]);
            }
        }
    }

    public boolean hit(int map, int col, int row, String requiredDirection) {
        boolean hit = false;

        if (map == gameController.getCurrentMap()) {

            gameController.getPlayer().getCollisionArea().x = gameController.getPlayer().getWorldX() + gameController.getPlayer().getCollisionArea().x;
            gameController.getPlayer().getCollisionArea().y = gameController.getPlayer().getWorldY() + gameController.getPlayer().getCollisionArea().y;

            eventRect[map][col][row].x = col * gameController.getTileSize() + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gameController.getTileSize() + eventRect[map][col][row].y;

            if (gameController.getPlayer().getCollisionArea().intersects(eventRect[map][col][row]) && !eventRect[map][col][row].isEventDone()) {
                if (gameController.getPlayer().getDirection().contentEquals(requiredDirection) || requiredDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gameController.getPlayer().getWorldX();
                    previousEventY = gameController.getPlayer().getWorldY();
                }
            }

            gameController.getPlayer().getCollisionArea().x = gameController.getPlayer().getCollisionDefaultX();
            gameController.getPlayer().getCollisionArea().y = gameController.getPlayer().getCollisionDefaultY();

            eventRect[map][col][row].x = eventRect[map][col][row].getEventRectDefaultX();
            eventRect[map][col][row].y = eventRect[map][col][row].getEventRectDefaultY();
        }

        return hit;
    }

    private void speak(Asset asset) {
        if (gameController.getKeyHandler().isEnterPressed()) {
            gameController.setGameState(gameController.getDialogueState());
            asset.speak();
        }
    }

    private void damagePit() {
        gameController.setGameState(gameController.getDialogueState());
        gameController.playSoundEffect(6);
        gameController.getUi().setCurrentDialogue("You fell into a pit!");
        gameController.getPlayer().setCurrentLife(gameController.getPlayer().getCurrentLife() - 1);
        canTouchEvent = false;
    }

    private void healingPool() {
        if (gameController.getKeyHandler().isEnterPressed()) {
            gameController.setGameState(gameController.getDialogueState());
            gameController.playSoundEffect(2);
            gameController.getUi().setCurrentDialogue("You drink the water. \nYour life and mana has been restored.");
            gameController.getPlayer().setCurrentLife(gameController.getPlayer().getMaxLife());
            gameController.getPlayer().setCurrentMana(gameController.getPlayer().getMaxMana());
            gameController.getAssetManager().setMonsters();
        }
    }

    private void teleport(int map, int col, int row) {
        gameController.setGameState(gameController.getTransitionState());
        tempMap = map;
        tempCol = col;
        tempRow = row;
        canTouchEvent = false;
        gameController.playSoundEffect(12);
    }

    public int getPreviousEventX() {
        return previousEventX;
    }

    public EventHandler setPreviousEventX(int previousEventX) {
        this.previousEventX = previousEventX;
        return this;
    }

    public int getPreviousEventY() {
        return previousEventY;
    }

    public EventHandler setPreviousEventY(int previousEventY) {
        this.previousEventY = previousEventY;
        return this;
    }

    public int getTempMap() {
        return tempMap;
    }

    public EventHandler setTempMap(int tempMap) {
        this.tempMap = tempMap;
        return this;
    }

    public int getTempCol() {
        return tempCol;
    }

    public EventHandler setTempCol(int tempCol) {
        this.tempCol = tempCol;
        return this;
    }

    public int getTempRow() {
        return tempRow;
    }

    public EventHandler setTempRow(int tempRow) {
        this.tempRow = tempRow;
        return this;
    }
}
