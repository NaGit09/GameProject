package Controller.util;

import Controller.GameController;
import Model.asset.entity.player.Player;
import java.awt.*;
import java.awt.image.BufferedImage;
/*
CLASS CHỨA CÁC HÀM STATIC ĐỂ XỬ LÍ HÌNH ẢNH , VÀ KÍCH THƯỚC HIỂN THỊ HÌNH ẢNH
 */
public class UtilityTool {

    public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D graphics2D = scaledImage.createGraphics();
        graphics2D.drawImage(original, 0, 0, width, height, null);
        graphics2D.dispose();

        return scaledImage;
    }
    public static int getXForCenterOfText(String text, GameController gameController, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gameController.getScreenWidth() / 2 - length / 2;
    }

    public static int getXForAlightToRightOfText(String text, int tailX, GameController GameController, Graphics2D graphics2D) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return tailX - length;
    }

    public static boolean isInsidePlayerView(int worldX, int worldY, GameController GameController) {
        int tileSize = GameController.getTileSize();
        Player player = GameController.getPlayer();
        return worldX + tileSize > player.getWorldX() - player.getScreenX()
                && worldX - tileSize < player.getWorldX() + player.getScreenX()
                && worldY + tileSize > player.getWorldY() - player.getScreenY()
                && worldY - tileSize < player.getWorldY() + player.getScreenY();
    }

    public static void changeAlpha(Graphics2D graphics2D, float alphaValue) {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }
}
