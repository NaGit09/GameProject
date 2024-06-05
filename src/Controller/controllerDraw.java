package Controller;

import Model.asset.Asset;
import Model.asset.entity.player.Player;

import java.awt.*;
/*
class thực hiện gọi lệnh draw đến class ui thông quá việc cập nhật dữ liệu các model
 */
public class controllerDraw {
    public GameController controller;

    public controllerDraw(GameController controller) {
        this.controller = controller;
    }
    public void drawToTempScreen() {

        // DEBUG
        long drawStart = 0;

        Graphics2D graphics2D = controller.graphics2D;
        if (controller.keyHandler.isShowDebugText()) {
            drawStart = System.nanoTime();
        }

        if (controller.gameState == GameController.typeGame.titleState) {
            controller.ui.draw(graphics2D);
        } else {

            // TILES
            controller.tileManager.draw(graphics2D);

            drawInteractiveTiles(graphics2D);

            // ASSETS
            controller.update.addAssets();
            controller.update.sortAssets();
            drawAssets(graphics2D);
            controller.update.assets.clear();

            // UI
            controller.ui.draw(graphics2D);
        }

        // DEBUG
        if (controller.keyHandler.isShowDebugText()) {
            drawDebugInfo(graphics2D, drawStart);
        }
    }
    public void drawInteractiveTiles(Graphics2D graphics2D) {
        for (int i = 0; i < controller.update.interactiveTiles[1].length; i++) {
            if (controller.update.interactiveTiles[controller.update.currentMap][i] != null) {
                controller.update.interactiveTiles[controller.update.currentMap][i].draw(graphics2D);
            }
        }
    }
    public void drawAssets(Graphics2D graphics2D) {
        for (Asset asset : controller.update.assets) {
            asset.draw(graphics2D);
        }
    }
    public void drawDebugInfo(Graphics2D graphics2D, long drawStart) {
        long drawEnd = System.nanoTime();
        long passedTime = drawEnd - drawStart;
        int x = 10;
        int y = 400;
        int lineHeight = 20;

        Player player = controller.getPlayer();

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
        Graphics graphics = controller.gp.getGraphics();
        graphics.drawImage(controller.tempScreen, 0, 0, controller.fullScreenWidth, controller.fullScreenHeight, null);
        graphics.dispose();
    }
}
