package Controller;

import Model.asset.Asset;

import java.awt.*;

public class controllerDraw {
    public GameController controller;

    public controllerDraw(GameController controller) {
        this.controller = controller;
    }
    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;
        if (controller.keyHandler.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        if (controller.gameState == GameController.typeGame.titleState) {
            controller.ui.draw(controller.graphics2D);
        } else {

            // TILES
            controller.tileManager.draw(controller.graphics2D);

            drawInteractiveTiles(controller.graphics2D);

            // ASSETS
            controller.addAssets();
            controller.sortAssets();
            drawAssets(controller.graphics2D);
            controller.assets.clear();

            // UI
            controller.ui.draw(controller.graphics2D);
        }

        // DEBUG
        if (controller.keyHandler.isShowDebugText()) {
            drawDebugInfo(controller.graphics2D, drawStart);
        }
    }

    public void drawInteractiveTiles(Graphics2D graphics2D) {
        for (int i = 0; i < controller.interactiveTiles[1].length; i++) {
            if (controller.interactiveTiles[controller.currentMap][i] != null) {
                controller.interactiveTiles[controller.currentMap][i].draw(graphics2D);
            }
        }
    }

    public void drawAssets(Graphics2D graphics2D) {
        for (Asset asset : controller.assets) {
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

        graphics2D.drawString("WorldX: " + controller.player.getWorldX(), x, y);
        y += lineHeight;
        graphics2D.drawString("WorldY: " +  controller.player.getWorldY(), x, y);
        y += lineHeight;
        graphics2D.drawString("Col: " + ( controller.player.getWorldX() +  controller.player.getCollisionArea().x) / Constants.tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Row: " + ( controller.player.getWorldY() +  controller.player.getCollisionArea().y) / Constants.tileSize, x, y);
        y += lineHeight;
        graphics2D.drawString("Draw Time: " + passedTime, x, y);
    }

    public void drawToScreen() {
        Graphics graphics = controller.gp.getGraphics();
        graphics.drawImage(controller.tempScreen, 0, 0, controller.fullScreenWidth, controller.fullScreenHeight, null);
        graphics.dispose();
    }

}
